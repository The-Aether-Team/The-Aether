package com.gildedgames.aether.common.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class FallingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {
	public FallingRandomWalkingGoal(CreatureEntity creatureEntity, double speed) {
		super(creatureEntity, speed, 0.001F);
	}

	@Nullable
	protected Vector3d getPosition() {
		if (this.mob.isInWaterOrBubble()) {
			Vector3d vector3d = RandomPositionGenerator.getLandPos(this.mob, 15, 7);
			return vector3d == null ? super.getPosition() : vector3d;
		} else {
			Vector3d vector3d = RandomPositionGenerator.getLandPos(this.mob, 12, 12);

			return this.mob.getRandom().nextFloat() >= this.probability && vector3d != null ? vector3d : super.getPosition();
		}
	}
}
