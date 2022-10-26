package com.gildedgames.aether.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.pathfinder.Path;

/**
 * Attack goal to make sure the entity finds a path as quickly as possible.
 * DO NOT USE THIS IF IT'S NOT NECESSARY! Continuous checks every tick for navigation
 * are not good for performance, and you don't want them to add up.
 */
public class ContinuousMeleeAttackGoal extends MeleeAttackGoal {
    private Path path;
    public ContinuousMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    }

    /** [VANILLA COPY] MeleeAttackGoal.canUse()
     * Method override to make the mob recalculate the path right away if the path is null
     */
    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else {
            this.path = this.mob.getNavigation().createPath(livingentity, 0);
            if (this.path != null) {
                return true;
            } else {
                return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        super.start();
        this.mob.getNavigation().moveTo(this.path, 1.5);
    }
}
