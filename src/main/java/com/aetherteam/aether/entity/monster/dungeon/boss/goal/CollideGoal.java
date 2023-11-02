package com.aetherteam.aether.entity.monster.dungeon.boss.goal;

import com.aetherteam.aether.data.resources.registries.AetherDamageTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CollideGoal extends Goal {
    private final Slider slider;
    public CollideGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        if (!slider.isAwake() || slider.isDeadOrDying()) {
            return false;
        }
        return slider.attackCooldown() <= 0 || slider.getDeltaMovement().length() > 0.08;
    }

    @Override
    public void tick() {
        Vec3 min = new Vec3(slider.getBoundingBox().minX - 0.1, slider.getBoundingBox().minY - 0.1, slider.getBoundingBox().minZ - 0.1);
        Vec3 max = new Vec3(slider.getBoundingBox().maxX + 0.1, slider.getBoundingBox().maxY + 0.1, slider.getBoundingBox().maxZ + 0.1);
        AABB collisionBounds = new AABB(min, max);
        for (Entity entity : slider.level().getEntities(slider, collisionBounds)) {
            if (entity instanceof LivingEntity livingEntity && entity.hurt(AetherDamageTypes.entityDamageSource(slider.level(), AetherDamageTypes.CRUSH, slider), 6)) {
                if (livingEntity instanceof Player player && player.getUseItem().is(Items.SHIELD) && player.isBlocking()) { // Disables the player's Shield if one is being used.
                    player.getCooldowns().addCooldown(Items.SHIELD, 100);
                    player.stopUsingItem();
                    slider.level().broadcastEntityEvent(player, (byte) 30);
                }
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));

                slider.setMoveDelay(slider.calculateMoveDelay());
                slider.setAttackCooldown(20);
                slider.setMoveDirection(null);

                // Stop the Slider's movement.
                slider.playSound(slider.getCollideSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));
                slider.setDeltaMovement(Vec3.ZERO);
            } else if (!(entity instanceof Player player && player.isCreative()) && !(entity instanceof Slider)) {
                entity.setDeltaMovement(slider.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
