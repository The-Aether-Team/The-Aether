package com.aetherteam.aether.entity.monster.dungeon.boss.ai;

import com.aetherteam.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.Optional;

/**
 * Have the slider back off if the player is still nearby after an attack. This is used to prevent stun locks.
 */
public class BackOffAfterAttack extends Behavior<Slider> {
    public BackOffAfterAttack() {
        super(ImmutableMap.of(AetherMemoryModuleTypes.HAS_ATTACKED.get(), MemoryStatus.VALUE_PRESENT, AetherMemoryModuleTypes.MOVE_DELAY.get(), MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
        return slider.getBrain().getTimeUntilExpiry(AetherMemoryModuleTypes.MOVE_DELAY.get()) == 1;
    }

    @Override
    protected void start(ServerLevel level, Slider slider, long pGameTime) {
        Brain<?> brain = slider.getBrain();
        Optional<LivingEntity> optional = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
        if (optional.isPresent()) {
            LivingEntity target = optional.get();
            if (slider.getBoundingBox().inflate(1.5).contains(target.position())) {
                // Move one block in the opposite direction of the target
                Direction direction = SliderAi.calculateDirection(slider.getX() - target.getX(), 0, slider.getZ() - target.getZ());
                brain.setMemory(AetherMemoryModuleTypes.TARGET_POSITION.get(), slider.position().relative(direction, 2));
            }
        }
    }
}