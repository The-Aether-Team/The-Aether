package com.aetherteam.aether.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 * Attack goal to make sure the entity attacks right away after spawning.
 */
public class ContinuousMeleeAttackGoal extends MeleeAttackGoal {
    private final double speedModifier;
    public ContinuousMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.speedModifier = pSpeedModifier;
    }

    /**
     * Method override to make the mob walk toward its enemy if there is no path.
     */
    @Override
    public boolean canUse() {
        if (!super.canUse()) {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                this.mob.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), this.speedModifier);
            }
            return false;
        }
        return true;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return 2.0F + pAttackTarget.getBbWidth();
    }
}
