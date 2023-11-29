package com.aetherteam.aether.entity.monster.dungeon.boss.goal;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Have the slider back off if the player is still nearby after an attack. This is used to prevent stun locks.
 */
public class BackOffAfterAttackGoal extends Goal {
    private final Slider slider;

    public BackOffAfterAttackGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        return this.slider.getMoveDelay() == 1 && this.slider.attackCooldown() > 0;
    }

    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        LivingEntity target = this.slider.getTarget();
        if (target != null && this.slider.getBoundingBox().inflate(1.5).contains(target.position())) {
            // Move one block in the opposite direction of the target.
            Direction direction = Slider.calculateDirection(this.slider.getX() - target.getX(), 0, this.slider.getZ() - target.getZ());
            this.slider.setTargetPoint(slider.position().relative(direction, 2));
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}