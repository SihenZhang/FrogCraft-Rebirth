package frogcraftrebirth.client.gui;

import org.lwjgl.opengl.GL11;

import frogcraftrebirth.client.GuiUtil;
import frogcraftrebirth.common.gui.ContainerAirPump;
import frogcraftrebirth.common.tile.TileAirPump;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAirPump extends GuiContainer {

	TileAirPump tile;

	public GuiAirPump(InventoryPlayer playerInv, TileAirPump tile) {
		super(new ContainerAirPump(playerInv, tile));
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.fontRendererObj.drawString("Industrial Air Pump", 8, ySize - 155, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1Float, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiUtil.getGuiBackground("AirPump"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int chargeIcon = (int) (15 * tile.charge / tile.maxCharge);
		this.drawTexturedModalRect(this.guiLeft + 118, this.guiTop + 33 + 14 - chargeIcon, 188, 14 - chargeIcon, 10, chargeIcon);
		
		int airPercentage = (int) (40 * tile.airAmount() / 1000);
		this.drawTexturedModalRect(this.guiLeft + 145, this.guiTop + 63 - airPercentage, 176, 0, 12, airPercentage);
		this.drawTexturedModalRect(this.guiLeft + 145, this.guiTop + 60 - airPercentage, 176, 41, 12, 4);
	}

}
