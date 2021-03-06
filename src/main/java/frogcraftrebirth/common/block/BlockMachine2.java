/*
 * Copyright (c) 2015 - 2017 3TUSK, et al.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package frogcraftrebirth.common.block;

import frogcraftrebirth.FrogCraftRebirth;
import frogcraftrebirth.common.lib.block.BlockFrogWrenchable;
import frogcraftrebirth.common.lib.util.ItemUtil;
import frogcraftrebirth.common.tile.IHasWork;
import frogcraftrebirth.common.tile.TileAdvBlastFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMachine2 extends BlockFrogWrenchable {

	public static final PropertyEnum<Type> TYPE = PropertyEnum.create("variant", Type.class);

	public BlockMachine2() {
		super(Material.IRON, "machine_2", false, 0);
		setUnlocalizedName("machines");
		setDefaultState(getDefaultState().withProperty(WORKING, false));
		setHardness(5.0F);
		setResistance(10.0F);
	}
	@Override
	protected IProperty<?>[] getPropertyArray() {
		return new IProperty[] { TYPE, FACING_HORIZONTAL, WORKING };
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		switch (state.getValue(TYPE)) {
			case ADV_BLAST_FURNACE: {
				ItemUtil.dropInventoryItems(worldIn, pos, ((TileAdvBlastFurnace)worldIn.getTileEntity(pos)).input);
				ItemUtil.dropInventoryItems(worldIn, pos, ((TileAdvBlastFurnace)worldIn.getTileEntity(pos)).output);
				break;
			}
			default: {
				break;
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return state.getValue(TYPE) == Type.ADV_BLAST_FURNACE;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		switch (state.getValue(TYPE)) {
			case ADV_BLAST_FURNACE: return new TileAdvBlastFurnace();
			default: return null;
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof IHasWork) {
			return state.withProperty(WORKING, ((IHasWork)tile).isWorking());
		} else {
			return state.withProperty(WORKING, false);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;
		playerIn.openGui(FrogCraftRebirth.getInstance(), 5, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int facing;
		switch (state.getValue(FACING_HORIZONTAL)) {
			case SOUTH: {
				facing = 0;
				break;
			}
			case WEST: {
				facing = 1;
				break;
			}
			case NORTH: {
				facing = 2;
				break;
			}
			case EAST: {
				facing = 3;
				break;
			}
			default: {
				facing = 2;
				break;
			}
		}
		return (facing << 2) + state.getValue(TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int facing = meta >> 2, type = meta & 0b11;
		return this.getDefaultState().withProperty(FACING_HORIZONTAL, EnumFacing.getHorizontal(facing)).withProperty(TYPE, Type.values()[type]);
	}

	public enum Type implements IStringSerializableEnumImpl {
		ADV_BLAST_FURNACE
	}
}
