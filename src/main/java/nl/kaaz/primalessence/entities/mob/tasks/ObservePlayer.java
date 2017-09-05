package nl.kaaz.primalessence.entities.mob.tasks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class ObservePlayer extends EntityAIBase {
	private final EntityLiving idleEntity;
	private int idleTime;

	public ObservePlayer(EntityLiving entitylivingIn) {
		this.idleEntity = entitylivingIn;
		this.setMutexBits(2);
	}

	public boolean shouldExecute() {
		return this.idleEntity.getRNG().nextFloat() < 1;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return this.idleTime >= 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		double d0 = (Math.PI * 2D) * this.idleEntity.getRNG().nextDouble();
		this.idleTime = 200 + this.idleEntity.getRNG().nextInt(200);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask() {
		--this.idleTime;
		EntityPlayer entityPlayer = idleEntity.getEntityWorld().getClosestPlayerToEntity(idleEntity, 8);
		if (entityPlayer != null) {
			this.idleEntity.getLookHelper().setLookPosition(entityPlayer.posX, entityPlayer.posY + entityPlayer.eyeHeight, entityPlayer.posZ, (float) this.idleEntity.getHorizontalFaceSpeed(),
					(float) this.idleEntity.getVerticalFaceSpeed());
		}
	}
}
