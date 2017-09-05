package nl.kaaz.primalessence.entities;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PEEntities {
	public static void init() {
		int id = 1;

//		EntityRegistry.registerModEntity(new ResourceLocation(PrimalEssence.ID), BasicGolem.class, "basicGolem", id++, PrimalEssence.instance, 64, 3, true, 0x996600, 0x00ff00);
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
//		RenderingRegistry.registerEntityRenderingHandler(BasicGolem.class, RendererGolem.FACTORY);
	}
}
