package nl.kaaz.primalessence.entities.mob.tasks;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import nl.kaaz.primalessence.entities.mob.BasicGolem;

public class BegPlayer extends EntityAIBase {
	private final BasicGolem golem;
	private EntityPlayer player;
	private final World world;
	private final float minPlayerDistance;
	private int timeoutCounter;

	public BegPlayer(BasicGolem golem, float minDistance) {
		this.golem = golem;
		this.world = golem.world;
		this.minPlayerDistance = minDistance;
		this.setMutexBits(2);
	}

	public boolean shouldExecute() {
		this.player = this.world.getClosestPlayerToEntity(this.golem, (double) this.minPlayerDistance);
		return this.player != null && this.hasTemptationItemInHand(this.player);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		if (!this.player.isEntityAlive()) {
			return false;
		} else if (this.golem.getDistanceSqToEntity(this.player) > (double) (this.minPlayerDistance * this.minPlayerDistance)) {
			return false;
		} else {
			return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.player);
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.golem.setBegging(true);
		this.timeoutCounter = 40 + this.golem.getRNG().nextInt(40);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask() {
		this.golem.setBegging(false);
		this.player = null;
	}

	public void updateTask() {
		System.out.println("BEGGING");
		this.golem.getLookHelper().setLookPosition(this.player.posX, this.player.posY + (double) this.player.getEyeHeight(), this.player.posZ, 10.0F, (float) this.golem.getVerticalFaceSpeed());
		--this.timeoutCounter;
	}


	private boolean hasTemptationItemInHand(EntityPlayer player) {
		for (EnumHand enumhand : EnumHand.values()) {
			ItemStack itemstack = player.getHeldItem(enumhand);
			if (itemstack.getItem() == Items.MELON) {
				return true;
			}
		}

		return false;
	}
}
