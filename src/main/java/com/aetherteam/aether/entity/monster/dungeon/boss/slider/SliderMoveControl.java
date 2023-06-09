package com.aetherteam.aether.entity.monster.dungeon.boss.slider;

import com.aetherteam.aether.entity.monster.dungeon.boss.slider.Slider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

/**
 * A move control for the slider moving only along the three axes.
 */
public class SliderMoveControl extends MoveControl {
    private final Slider slider;

    private Direction direction = Direction.UP;
    private float velocity;
    private int moveDelay;

    public SliderMoveControl(Slider mob) {
        super(mob);
        this.slider = mob;
    }

    @Override
    public void tick() {
        if (this.moveDelay > 0) {
            this.moveDelay--;
        } else {
            double x = this.getWantedX() - this.mob.getX();
            double y = this.getWantedY() - this.mob.getY();
            double z = this.getWantedZ() - this.mob.getZ();

            if (this.direction == null) {
                if (this.manhattanDistance(x, y, z) < 0.1) {
                    return;
                }
                this.direction = Direction.getNearest(x, y, z);
            }

            if (this.axisDistance(x, y, z, this.direction) <= 0) {
                this.stop();
                return;
            }

            if (this.velocity < this.slider.getMaxVelocity()) {
                // The Slider increases its speed based on the speed it has saved
                this.velocity = Math.min(slider.getMaxVelocity(), this.velocity + this.slider.getVelocityIncrease());
            }

            Vec3 movement = new Vec3(this.direction.getStepX() * this.velocity,
                    this.direction.getStepY() * this.velocity,
                    this.direction.getStepZ() * this.velocity);

            this.slider.setDeltaMovement(movement);
        }
    }

    private void stop() {
        this.moveDelay = this.slider.calculateMoveDelay();
        this.direction = null;
        this.velocity = 0;
        this.mob.setDeltaMovement(Vec3.ZERO);
    }

    private double axisDistance(double x, double y, double z, Direction direction) {
        return x * direction.getStepX() + y * direction.getStepY() + z * direction.getStepZ();
    }

    private double manhattanDistance(double x, double y, double z) {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }
}
