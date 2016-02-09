package frogcraftrebirth.common.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import frogcraftrebirth.common.network.PacketFrog02GuiDataUpdate;
import frogcraftrebirth.common.tile.TileAirPump;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public class ContainerAirPump extends ContainerTileFrog<TileAirPump> {
	
	public int charge, air;
	
	public ContainerAirPump(InventoryPlayer playerInv, TileAirPump tile) {
		super(playerInv, tile);
		this.registerPlayerInventory(playerInv);
	}
	
	public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.tile.charge);
        crafting.sendProgressBarUpdate(this, 1, this.tile.airAmount());
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i=0;i<this.crafters.size();++i) {
			ICrafting crafter = (ICrafting)this.crafters.get(i);
			if (this.charge != this.tile.charge)
				sendDataToClientSide(new PacketFrog02GuiDataUpdate(this.windowId, 0, this.tile.charge), (EntityPlayerMP)crafter);
			if (this.air != this.tile.airAmount())
				sendDataToClientSide(new PacketFrog02GuiDataUpdate(this.windowId, 1, this.tile.airAmount()), (EntityPlayerMP)crafter);
		}
		this.charge = this.tile.charge;
		this.air = this.tile.airAmount();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int value) {
		if (id == 0) this.tile.charge = value;
		if (id == 1) this.tile.setAirAmount(value);
	}

}