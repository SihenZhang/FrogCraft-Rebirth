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

package frogcraftrebirth.common.lib;

import frogcraftrebirth.api.recipes.IPyrolyzerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PyrolyzerRecipe implements IPyrolyzerRecipe {

	private final ItemStack input, output;
	private final FluidStack outputFluid;
	private final int time, energyPerTick;
	
	public PyrolyzerRecipe(ItemStack input, ItemStack output, FluidStack outputFluid, int time, int energyPerTick){
		this.input = input;
		this.output = output;
		this.outputFluid = outputFluid;
		this.time = time;
		this.energyPerTick = energyPerTick;
	}
	
	public ItemStack getInput() {
		return input.copy();
	}
	
	public ItemStack getOutput() {
		return output.copy();
	}
	
	public FluidStack getOutputFluid() {
		return outputFluid != null ? outputFluid.copy() : null;
	}
	
	public int getTime() {
		return time;
	}
	
	public int getEnergyPerTick() {
		return energyPerTick;
	}
	
	@Override
	public boolean equals(Object r) {
		return r instanceof PyrolyzerRecipe && ((PyrolyzerRecipe)r).getInput().isItemEqual(this.input);
	}

}
