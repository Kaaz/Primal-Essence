package nl.kaaz.primalessence.entities;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.kaaz.primalessence.PrimalEssence;
import nl.kaaz.primalessence.entities.mob.BasicGolem;
import nl.kaaz.primalessence.entities.mob.RenderGolem;

public class PEEntities {
	public static void init() {
		int id = 1;

		EntityRegistry.registerModEntity(new ResourceLocation(PrimalEssence.ID), BasicGolem.class, "basicGolem", id++, PrimalEssence.instance, 64, 3, true, 0x996600, 0x00ff00);
	}

	public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(BasicGolem.class, RenderGolem.FACTORY);
	}
}
