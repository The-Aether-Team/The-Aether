package com.aetherteam.aether.entity.monster.dungeon.boss.goal;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SliderMoveGoal extends Goal {

    private final Slider slider;
    private float velocity;
    public SliderMoveGoal(Slider slider) {
        this.slider = slider;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return slider.isAwake() && !slider.isDeadOrDying() && slider.getMoveDelay() <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        if (!canUse()) {
            return false;
        }
        if (slider.getMoveDelay() > 0) {
            return false;
        }
        return !slider.horizontalCollision && !slider.verticalCollision;
    }

    @Override
    public void start() {
        slider.setMoveDirection(null);
        slider.playSound(slider.getMoveSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public void tick() {
        Vec3 targetPoint = slider.findTargetPoint();
        // Move along the calculated path.
        if (targetPoint == null) {
            this.stop();
            return;
        }
        Direction moveDir = getMoveDirection(slider, targetPoint);
        if (axisDistance(targetPoint, slider.position(), moveDir) <= 0) {
            this.stop();
            return;
        }
        if (this.velocity < slider.getMaxVelocity()) { // The Slider increases its speed based on the speed it has saved.
            this.velocity = Math.min(slider.getMaxVelocity(), this.velocity + slider.getVelocityIncrease());
        }
        slider.setDeltaMovement(new Vec3(moveDir.getStepX() * this.velocity, moveDir.getStepY() * this.velocity, moveDir.getStepZ() * this.velocity));
    }

    @Override
    public void stop() {
        slider.setMoveDelay(slider.calculateMoveDelay());
        slider.setTargetPoint(null);
        this.velocity = 0;
        slider.setDeltaMovement(Vec3.ZERO);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Get the move direction if it already exists, or calculate a new one.
     * @param slider The {@link Slider} that the brain belongs to.
     * @param targetPoint The target {@link Vec3} position.
     * @return The {@link Direction} to move in.
     */
    private static Direction getMoveDirection(Slider slider, Vec3 targetPoint) {
        Direction moveDir = slider.getMoveDirection();

        if (moveDir == null) { // Checks if the direction has changed.
            double x = targetPoint.x - slider.getX();
            double y = targetPoint.y - slider.getY();
            double z = targetPoint.z - slider.getZ();
            moveDir = Slider.calculateDirection(x, y, z);
            slider.setMoveDirection(moveDir);
        }
        return moveDir;
    }

    /**
     * Gets the calculated distance between two points on one axis.
     * @param direction The axis and direction to move along {@link Direction}.
     * @return The calculated distance.
     */
    private static double axisDistance(Vec3 target, Vec3 start, Direction direction) {
        double x = target.x() - start.x();
        double y = target.y() - start.y();
        double z = target.z() - start.z();
        return x * direction.getStepX() + y * direction.getStepY() + z * direction.getStepZ();
    }
}
