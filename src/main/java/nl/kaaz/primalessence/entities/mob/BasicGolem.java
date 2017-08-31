package nl.kaaz.primalessence.entities.mob;

import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.kaaz.primalessence.entities.mob.tasks.BegPlayer;
import nl.kaaz.primalessence.entities.mob.tasks.ObservePlayer;

public class BasicGolem extends EntityMob {
	private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(BasicGolem.class, DataSerializers.BOOLEAN);
	private float headRotationCourse;
	private float headRotationCourseOld;
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;

	public BasicGolem(World worldIn) {
		super(worldIn);
		this.timeWolfIsShaking = 0.0F;
		this.prevTimeWolfIsShaking = 0.0F;
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(4, new BegPlayer(this, 64));
		this.tasks.addTask(5, new ObservePlayer(this));
		this.tasks.addTask(10, new EntityAILookIdle(this));

	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(BEGGING, false);
	}

	@SideOnly(Side.CLIENT)
	public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
		float f = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;

		if (f < 0.0F) {
			f = 0.0F;
		} else if (f > 1.0F) {
			f = 1.0F;
		}

		return MathHelper.sin(f * (float) Math.PI) * MathHelper.sin(f * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
	}

	@SideOnly(Side.CLIENT)
	public float getInterestedAngle(float p_70917_1_) {
		return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * (float) Math.PI;
	}

	public void onUpdate() {
		super.onUpdate();
		this.headRotationCourseOld = this.headRotationCourse;

		if (this.isBegging()) {
			this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
		} else {
			this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
		}
	}

	public boolean isBegging() {
		return this.dataManager.get(BEGGING);
	}

	public void setBegging(boolean beg) {
		this.dataManager.set(BEGGING, beg);
	}
}
