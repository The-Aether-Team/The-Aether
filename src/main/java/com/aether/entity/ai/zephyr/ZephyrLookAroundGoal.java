package com.aether.entity.ai.zephyr;

import com.aether.entity.monster.ZephyrEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

/**
 * Vanilla copy - GhastEntity.LookAroundGoal - Changed from GhastEntity to Zephyr Entity
 */
public class ZephyrLookAroundGoal extends Goal {
    private final ZephyrEntity zephyr;

    public ZephyrLookAroundGoal(ZephyrEntity entity) {
        this.zephyr = entity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        return true;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.zephyr.getAttackTarget() == null) {
            Vec3d vec3d = this.zephyr.getMotion();
            this.zephyr.rotationYaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * (180F / (float)Math.PI);
            this.zephyr.renderYawOffset = this.zephyr.rotationYaw;
        } else {
            LivingEntity livingentity = this.zephyr.getAttackTarget();
            double d0 = 64.0D;
            if (livingentity.getDistanceSq(this.zephyr) < 4096.0D) {
                double d1 = livingentity.getPosX() - this.zephyr.getPosX();
                double d2 = livingentity.getPosZ() - this.zephyr.getPosZ();
                this.zephyr.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                this.zephyr.renderYawOffset = this.zephyr.rotationYaw;
            }
        }

    }
}
