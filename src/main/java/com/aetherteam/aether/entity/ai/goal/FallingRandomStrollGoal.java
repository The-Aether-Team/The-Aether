package com.aetherteam.aether.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

/**
 * A stroll goal that is capable of looking for land positions to target while falling.
 */
public class FallingRandomStrollGoal extends RandomStrollGoal {
    private final float probability;

    public FallingRandomStrollGoal(PathfinderMob mob, double speed) {
        this(mob, speed, 120, 0.001F);
    }

    public FallingRandomStrollGoal(PathfinderMob mob, double speed, int interval) {
        this(mob, speed, interval, 0.001F);
    }

    public FallingRandomStrollGoal(PathfinderMob mob, double speed, int interval, float probability) {
        super(mob, speed, interval);
        this.probability = probability;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (this.mob.isInWaterOrBubble()) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, this.mob.getMaxFallDistance());
            return vec3 == null ? super.getPosition() : vec3;
        } else if (!this.mob.onGround()) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 12, this.mob.getMaxFallDistance());
            return vec3 != null ? vec3 : super.getPosition();
        } else {
            return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, this.mob.getMaxFallDistance()) : super.getPosition();
        }
    }
}
