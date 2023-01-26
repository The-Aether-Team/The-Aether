package com.gildedgames.aether.entity.monster.dungeon.boss.slider;

import com.gildedgames.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Collide extends Behavior<Slider> {
    public Collide() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
        if (!slider.isAwake() || slider.isDeadOrDying()) {
            return false;
        }
        return !slider.getBrain().hasMemoryValue(AetherMemoryModuleTypes.HAS_ATTACKED.get()) || slider.getDeltaMovement().length() > 0.08;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Slider slider, long gameTime) {
        return this.checkExtraStartConditions(level, slider);
    }

    @Override
    protected void tick(ServerLevel level, Slider slider, long gameTime) {
        Brain<?> brain = slider.getBrain();
        AABB collisionBounds = new AABB(slider.getBoundingBox().minX - 0.1, slider.getBoundingBox().minY - 0.1, slider.getBoundingBox().minZ - 0.1,
                slider.getBoundingBox().maxX + 0.1, slider.getBoundingBox().maxY + 0.1, slider.getBoundingBox().maxZ + 0.1);
        for (Entity entity : level.getEntities(slider, collisionBounds)) {
            if (entity instanceof LivingEntity livingEntity && entity.hurt(new EntityDamageSource("aether.crush", slider), 6)) {
                if (livingEntity instanceof Player player && player.getUseItem().is(Items.SHIELD) && player.isBlocking()) {
                    player.getCooldowns().addCooldown(Items.SHIELD, 100);
                    player.stopUsingItem();
                    level.broadcastEntityEvent(player, (byte) 30);
                }
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));

                brain.setMemoryWithExpiry(AetherMemoryModuleTypes.HAS_ATTACKED.get(), Unit.INSTANCE, 20);
                brain.setMemoryWithExpiry(AetherMemoryModuleTypes.MOVE_DELAY.get(), Unit.INSTANCE, slider.calculateMoveDelay());
                brain.eraseMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get());

                // Stop the slider movement
                slider.playSound(slider.getCollideSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));
                slider.setDeltaMovement(Vec3.ZERO);
            } else if (!(entity instanceof Player player && player.isCreative()) && !(entity instanceof Slider)) {
                entity.setDeltaMovement(slider.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
            }
        }
    }
}
