package frogcraftrewrite.common.lib.tile;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileFrogGenerator extends TileFrogInventory implements ISidedInventory, IEnergySource {

	public int charge, sourceTier, output;
	protected boolean isInENet;
	
	public TileFrogGenerator (int invSize, String name, int sourceTier, int output) {
		super(invSize, name);
		this.sourceTier = sourceTier;
		this.output = output;
	}
	
	@Override
	public void invalidate() {
		if (!worldObj.isRemote && isInENet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			isInENet = false;
		}
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();	
		if (!worldObj.isRemote && !isInENet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			isInENet = true;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.charge = tag.getInteger("charge");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("charge", this.charge);
	}
	
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
		return true;
	}

	@Override
	public double getOfferedEnergy() {
		return Math.min(charge, output);
	}

	@Override
	public void drawEnergy(double amount) {
		this.charge -= amount;
		this.charge = charge < 0 ? 0 : charge;
	}

	@Override
	public int getSourceTier() {
		return sourceTier;
	}

	@Override
	public abstract int[] getAccessibleSlotsFromSide(int side);

	@Override
	public abstract boolean canInsertItem(int slot, ItemStack item, int side);

	@Override
	public abstract boolean canExtractItem(int slot, ItemStack item, int side);

}