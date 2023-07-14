package com.aetherteam.aether.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FallingRandomStrollGoal extends RandomStrollGoal {
    protected final float probability;

    public FallingRandomStrollGoal(PathfinderMob creatureEntity, double speed) {
        this(creatureEntity, speed, 120, 0.001F);
    }

    public FallingRandomStrollGoal(PathfinderMob creatureEntity, double speed, int interval) {
        this(creatureEntity, speed, interval, 0.001F);
    }

    public FallingRandomStrollGoal(PathfinderMob creatureEntity, double speed, int interval, float probability) {
        super(creatureEntity, speed, interval);
        this.probability = probability;
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob.isInWaterOrBubble()) {
            Vec3 vector3d = LandRandomPos.getPos(this.mob, 15, this.mob.getMaxFallDistance());
            return vector3d == null ? super.getPosition() : vector3d;
        } else if (!this.mob.isOnGround()) {
            Vec3 vector3d = LandRandomPos.getPos(this.mob, 12, this.mob.getMaxFallDistance());
            return vector3d != null ? vector3d : super.getPosition();
        } else {
            return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, this.mob.getMaxFallDistance()) : super.getPosition();
        }
    }
}
