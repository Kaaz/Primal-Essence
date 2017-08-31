package nl.kaaz.primalessence.entities.mob;

import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import nl.kaaz.primalessence.entities.mob.tasks.ObservePlayer;

public class BasicGolem extends EntityMob {
	public BasicGolem(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(2, new ObservePlayer(this));
		this.tasks.addTask(10, new EntityAILookIdle(this));
	}
}
