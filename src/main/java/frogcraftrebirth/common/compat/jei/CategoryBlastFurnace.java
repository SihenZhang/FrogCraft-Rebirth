package frogcraftrebirth.common.compat.jei;

import frogcraftrebirth.api.FrogAPI;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class CategoryBlastFurnace implements IRecipeCategory<RecipeBlastFurnace> {

	protected final IDrawable background;
	protected final IDrawableAnimated progressBar;
	protected final IDrawableAnimated heatBar;

	public CategoryBlastFurnace(IGuiHelper helper) {
		ResourceLocation backgroundTexture = new ResourceLocation("frogcraftrebirth", "textures/gui/gui_adv_blast_furnace.png");
		background = helper.createDrawable(backgroundTexture, 5, 5, 165, 70, 23, 88, 5, 10);
		IDrawableStatic progressBarBackground = helper.createDrawable(backgroundTexture, 176, 59, 24, 18);
		progressBar = helper.createAnimatedDrawable(progressBarBackground, 100, IDrawableAnimated.StartDirection.LEFT, false);
		IDrawableStatic chargeBarBackground = helper.createDrawable(backgroundTexture, 176, 53, 24, 6);
		heatBar = helper.createAnimatedDrawable(chargeBarBackground, 30, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public String getUid() {
		return "frogcraftrebirth.blastfurnace";
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.category.blastfurnace");
	}

	@Override
	public String getModName() {
		return FrogAPI.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		progressBar.draw(minecraft, 76, 45);
		heatBar.draw(minecraft, 76, 69);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RecipeBlastFurnace recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		List<List<ItemStack>> listsInputs = ingredients.getInputs(ItemStack.class);
		itemStackGroup.init(0, true, 32, 43);
		itemStackGroup.set(0, listsInputs.get(0));
		itemStackGroup.init(1, true, 50, 43);
		itemStackGroup.set(1, listsInputs.get(1));
		itemStackGroup.init(2, false, 108, 43);
		List<List<ItemStack>> listsOutputs = ingredients.getOutputs(ItemStack.class);
		itemStackGroup.set(2, listsOutputs.get(0));
		itemStackGroup.init(3, false, 126, 43);
		itemStackGroup.set(3, listsOutputs.get(1));
		List<List<FluidStack>> listsInputsFluid = ingredients.getInputs(FluidStack.class);
		IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
		fluidStackGroup.init(0, true, 8, 39, 16, 47, 8000, true, null);
		fluidStackGroup.set(0, listsInputsFluid.get(0).get(0));
		fluidStackGroup.init(1, true, 152, 39, 16, 47, 1000, true, null);
		fluidStackGroup.set(1, listsInputsFluid.get(1).get(0));
	}
}