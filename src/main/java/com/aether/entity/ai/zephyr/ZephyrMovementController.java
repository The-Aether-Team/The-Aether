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
    public void tick()
    {
        if (this.action == Action.MOVE_TO)
        {
            double d0 = this.posX - this.zephyr.getPosX();
            double d1 = this.posY - this.zephyr.getPosY();
            double d2 = this.posZ - this.zephyr.getPosZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;

            if (this.courseChangeCooldown-- <= 0)
            {
                this.courseChangeCooldown += this.zephyr.getRNG().nextInt(5) + 2;
                d3 = (double) MathHelper.sqrt(d3);

                if (this.isNotColliding(this.posX, this.posY, this.posZ, d3))
                {
                    this.zephyr.setMotion(this.zephyr.getMotion().add(d0 / d3 * 0.1D, d1 / d3 * 0.1D, d2 / d3 * 0.1D));
                }
                else
                {
                    this.action = Action.WAIT;
                }
            }
        }
    }

    /**
     * Checks if entity bounding box is not colliding with terrain
     */
    private boolean isNotColliding(double x, double y, double z, double distance)
    {
        double d0 = (x - this.zephyr.getPosX()) / distance;
        double d1 = (y - this.zephyr.getPosY()) / distance;
        double d2 = (z - this.zephyr.getPosZ()) / distance;
        AxisAlignedBB axisalignedbb = this.zephyr.getBoundingBox();

        for (int i = 1; (double)i < distance; ++i)
        {
            axisalignedbb = axisalignedbb.offset(d0, d1, d2);

            if (!this.zephyr.world.hasNoCollisions(this.zephyr, axisalignedbb))
            {
                return false;
            }
        }

        return true;
    }

}
