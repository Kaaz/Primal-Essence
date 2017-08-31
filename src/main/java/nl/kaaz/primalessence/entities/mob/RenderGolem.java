package nl.kaaz.primalessence.entities.mob;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import nl.kaaz.primalessence.PrimalEssence;

import javax.annotation.Nonnull;

public class RenderGolem extends RenderLiving<BasicGolem> {

	private ResourceLocation mobTexture = new ResourceLocation(PrimalEssence.ID+":textures/basicgolem.png");

	public static final Factory FACTORY = new Factory();

	public RenderGolem(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelZombie(), 0.5F);
	}

	@Override
	@Nonnull
	protected ResourceLocation getEntityTexture(@Nonnull BasicGolem entity) {
		return mobTexture;
	}

	public static class Factory implements IRenderFactory<BasicGolem> {
		@Override
		public Render<? super BasicGolem> createRenderFor(RenderManager manager) {
			return new RenderGolem(manager);
		}

	}

}
