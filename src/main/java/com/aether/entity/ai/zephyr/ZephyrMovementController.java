package com.aether.entity.ai.zephyr;

import com.aether.entity.monster.ZephyrEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ZephyrMovementController extends MovementController {
    private final ZephyrEntity zephyr;
    private int courseChangeCooldown;
    public ZephyrMovementController(ZephyrEntity mob) {
        super(mob);
        zephyr = mob;
    }
    
    @Override
    public void tick() {
        if (this.action == Action.MOVE_TO) {
            if (this.courseChangeCooldown-- <= 0) {
                Vec3d motion = new Vec3d(this.posX - this.zephyr.getPosX(), this.posY - this.zephyr.getPosY(), this.posZ - this.zephyr.getPosZ());
                double magnitude = motion.length();
                motion = motion.normalize();
                this.courseChangeCooldown += this.zephyr.getRNG().nextInt(5) + 2;
                if (this.isNotColliding(motion, Math.ceil(magnitude))) {
                    this.zephyr.setMotion(this.zephyr.getMotion().add(motion.scale(0.1D)));
                } else {
                    this.action = Action.WAIT;
                }
            }

        }
    }

    private boolean isNotColliding(Vec3d motion, double distance) {

        AxisAlignedBB axisalignedbb = this.zephyr.getBoundingBox();

        for(int i = 1; i < distance; ++i) {
            axisalignedbb = axisalignedbb.offset(motion);
            if (!this.zephyr.world.hasNoCollisions(this.zephyr, axisalignedbb)) {
                return false;
            }
        }

        return true;
    }
}
