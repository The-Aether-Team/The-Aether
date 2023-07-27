package com.aetherteam.aether.entity.monster.dungeon.boss.ai;

import com.aetherteam.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Move to the defined target position.
 */
public class Move extends Behavior<Slider> {
    private float velocity;

    public Move() {
        super(ImmutableMap.of(AetherMemoryModuleTypes.MOVE_DELAY.get(), MemoryStatus.VALUE_ABSENT, AetherMemoryModuleTypes.MOVE_DIRECTION.get(), MemoryStatus.REGISTERED, AetherMemoryModuleTypes.TARGET_POSITION.get(), MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
        return slider.isAwake() && !slider.isDeadOrDying();
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Slider slider, long gameTime) {
        if (!slider.isAwake() || slider.isDeadOrDying()) {
            return false;
        }

        if (slider.getBrain().hasMemoryValue(AetherMemoryModuleTypes.MOVE_DELAY.get())) {
            return false;
        }

        return !slider.horizontalCollision && !slider.verticalCollision;
    }

    @Override
    protected void start(ServerLevel level, Slider slider, long pGameTime) {
        slider.getBrain().eraseMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get());
        slider.playSound(slider.getMoveSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));
    }

    @Override
    protected void tick(ServerLevel level, Slider slider, long gameTime) {
        Brain<?> brain = slider.getBrain();
        Vec3 targetPoint = SliderAi.getTargetPoint(brain);

        // Move along the calculated path
        if (targetPoint == null) {
            this.doStop(level, slider, gameTime);
            return;
        }

        Direction moveDir = getMoveDirection(slider, targetPoint);

        if (axisDistance(targetPoint.x - slider.getX(), targetPoint.y - slider.getY(), targetPoint.z - slider.getZ(), moveDir) <= 0) {
            this.doStop(level, slider, gameTime);
            return;
        }

        if (this.velocity < slider.getMaxVelocity()) {
            // The Slider increases its speed based on the speed it has saved
            this.velocity = Math.min(slider.getMaxVelocity(), this.velocity + slider.getVelocityIncrease());
        }

        Vec3 movement = new Vec3(moveDir.getStepX() * this.velocity,
                moveDir.getStepY() * this.velocity,
                moveDir.getStepZ() * this.velocity);

        slider.setDeltaMovement(movement);
    }

    @Override
    protected void stop(ServerLevel level, Slider slider, long gameTime) {
        slider.getBrain().setMemoryWithExpiry(AetherMemoryModuleTypes.MOVE_DELAY.get(), Unit.INSTANCE, slider.calculateMoveDelay());
        slider.getBrain().eraseMemory(AetherMemoryModuleTypes.TARGET_POSITION.get());
        this.velocity = 0;
        slider.setDeltaMovement(Vec3.ZERO);
    }

    /**
     * Get the move direction if it already exists, or calculate a new one.
     */
    private static Direction getMoveDirection(Slider slider, Vec3 targetPoint) {
        Brain<?> brain = slider.getBrain();
        Optional<Direction> optionalDir = brain.getMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get());
        Direction moveDir;

        if (optionalDir.isEmpty()) { // If the direction has changed
            double x = targetPoint.x - slider.getX();
            double y = targetPoint.y - slider.getY();
            double z = targetPoint.z - slider.getZ();
            moveDir = SliderAi.calculateDirection(x, y, z);
            brain.setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), moveDir);
        } else {
            moveDir = optionalDir.get();
        }
        return moveDir;
    }

    private static double axisDistance(double x, double y, double z, Direction direction) {
        return x * direction.getStepX() + y * direction.getStepY() + z * direction.getStepZ();
    }
}