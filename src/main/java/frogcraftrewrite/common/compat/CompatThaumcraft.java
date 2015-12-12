package frogcraftrewrite.common.compat;

import static frogcraftrewrite.common.lib.config.ConfigMain.*;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

public class CompatThaumcraft implements ICompatModuleFrog {
	
	public static Aspect fertilizer;

	@Override
	public void preInit() {
		fertilizer = new Aspect("fertilitati", 0xFFFF00, new Aspect[] {Aspect.GREED, Aspect.CROP}, new ResourceLocation(""/*todo*/), 771);
	}

	@Override
	public void init() {
		//noop
	}

	@Override
	public void postInit() {
		if (enableTCAspect) {
			//prepare for... aspects registration
		}
	}

}
