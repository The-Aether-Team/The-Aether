package com.aetherteam.aether.entity.ai.navigator;

import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

/**
 * A path navigator that doesn't require the entity to be on the ground to update the path.
 */
public class FallPathNavigation extends GroundPathNavigation {
    public FallPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    /**
     * [CODE COPY] - {@link PathNavigation#followThePath()}.
     * Modified to prevent spinning.
     */
    @Override
    protected void followThePath() {
        Vec3 vec3 = this.getTempMobPos();
        this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75F ? this.mob.getBbWidth() / 2.0F : 0.75F - this.mob.getBbWidth() / 2.0F;
        Vec3i vec3i = this.path.getNextNodePos();
        double d0 = Math.abs(this.mob.getX() - ((double) vec3i.getX() + (this.mob.getBbWidth() + 1) / 2D)); //Forge: Fix MC-94054
        double d1 = Math.abs(this.mob.getY() - (double) vec3i.getY());
        double d2 = Math.abs(this.mob.getZ() - ((double) vec3i.getZ() + (this.mob.getBbWidth() + 1) / 2D)); //Forge: Fix MC-94054

        // Lessens rotation around a point when following a path.
        float fallDistance = this.mob.getMaxFallDistance();
        boolean flag = d0 <= (double) this.maxDistanceToWaypoint && d2 <= (double) this.maxDistanceToWaypoint && d1 < fallDistance; //Forge: Fix MC-94054
        if (flag || this.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(vec3)) {
            this.path.advance();
        }

        this.doStuckDetection(vec3);
    }

    private boolean canCutCorner(BlockPathTypes pPathType) {
        return pPathType != BlockPathTypes.DANGER_FIRE && pPathType != BlockPathTypes.DANGER_OTHER && pPathType != BlockPathTypes.WALKABLE_DOOR;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.ai.navigation.PathNavigation#shouldTargetNextNodeInDirection(Vec3)}.
     */
    private boolean shouldTargetNextNodeInDirection(Vec3 pVec) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
            return false;
        } else {
            Vec3 vec3 = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!pVec.closerThan(vec3, 2.0)) {
                return false;
            } else if (this.canMoveDirectly(pVec, this.path.getNextEntityPos(this.mob))) {
                return true;
            } else {
                Vec3 vec31 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vec32 = vec3.subtract(pVec);
                Vec3 vec33 = vec31.subtract(pVec);
                double d0 = vec32.lengthSqr();
                double d1 = vec33.lengthSqr();
                boolean flag = d1 < d0;
                boolean flag1 = d0 < 0.5;
                if (!flag && !flag1) {
                    return false;
                } else {
                    Vec3 vec34 = vec32.normalize();
                    Vec3 vec35 = vec33.normalize();
                    return vec35.dot(vec34) < 0.0;
                }
            }
        }
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }
}
