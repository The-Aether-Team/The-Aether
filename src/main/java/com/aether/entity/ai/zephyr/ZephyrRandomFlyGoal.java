package com.aether.entity.ai.zephyr;

import com.aether.entity.monster.ZephyrEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Random;

/**
 * Vanilla copy - GhastEntity.RandomFlyGoal - Changed from GhastEntity to Zephyr Entity
 */
public class ZephyrRandomFlyGoal extends Goal {
    private ZephyrEntity zephyr;
    public ZephyrRandomFlyGoal(ZephyrEntity entity) {
        zephyr = entity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        MovementController movementcontroller = this.zephyr.getMoveHelper();
        if (!movementcontroller.isUpdating()) {
            return true;
        } else {
            double d0 = movementcontroller.getX() - this.zephyr.getPosX();
            double d1 = movementcontroller.getY() - this.zephyr.getPosY();
            double d2 = movementcontroller.getZ() - this.zephyr.getPosZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        Random random = this.zephyr.getRNG();
        double d0 = this.zephyr.getPosX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d1 = this.zephyr.getPosY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = this.zephyr.getPosZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.zephyr.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    }
}
