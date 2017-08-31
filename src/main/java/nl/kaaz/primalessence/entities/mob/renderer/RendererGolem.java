package nl.kaaz.primalessence.entities.mob.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.kaaz.primalessence.entities.mob.BasicGolem;
import nl.kaaz.primalessence.entities.mob.ModelGolem;

@SideOnly(Side.CLIENT)
public class RendererGolem extends RenderLiving<BasicGolem> {
	public static final Factory FACTORY = new Factory();

	private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf.png");
	private static final ResourceLocation TAMED_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
	private static final ResourceLocation ANRGY_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

	public RendererGolem(RenderManager p_i47187_1_) {
		super(p_i47187_1_, new ModelGolem(), 0.5F);
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(BasicGolem entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(BasicGolem entity) {
		return WOLF_TEXTURES;
	}
	public static class Factory implements IRenderFactory<BasicGolem> {
		@Override
		public Render<? super BasicGolem> createRenderFor(RenderManager manager) {
			return new RendererGolem(manager);
		}

	}
}