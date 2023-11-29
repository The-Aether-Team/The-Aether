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
        if (!this.slider.isAwake() || this.slider.isDeadOrDying()) {
            return false;
        }
        return this.slider.attackCooldown() <= 0 || this.slider.getDeltaMovement().length() > 0.08;
    }

    @Override
    public void tick() {
        Vec3 min = new Vec3(this.slider.getBoundingBox().minX - 0.1, this.slider.getBoundingBox().minY - 0.1, this.slider.getBoundingBox().minZ - 0.1);
        Vec3 max = new Vec3(this.slider.getBoundingBox().maxX + 0.1, this.slider.getBoundingBox().maxY + 0.1, this.slider.getBoundingBox().maxZ + 0.1);
        AABB collisionBounds = new AABB(min, max);
        for (Entity entity : this.slider.level().getEntities(this.slider, collisionBounds)) {
            if (entity instanceof LivingEntity livingEntity && entity.hurt(AetherDamageTypes.entityDamageSource(this.slider.level(), AetherDamageTypes.CRUSH, this.slider), 6)) {
                if (livingEntity instanceof Player player && player.getUseItem().is(Items.SHIELD) && player.isBlocking()) { // Disables the player's Shield if one is being used.
                    player.getCooldowns().addCooldown(Items.SHIELD, 100);
                    player.stopUsingItem();
                    this.slider.level().broadcastEntityEvent(player, (byte) 30);
                }
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));

                this.slider.setMoveDelay(this.slider.calculateMoveDelay());
                this.slider.setAttackCooldown(20);
                this.slider.setMoveDirection(null);

                // Stop the Slider's movement.
                this.slider.playSound(this.slider.getCollideSound(), 2.5F, 1.0F / (this.slider.getRandom().nextFloat() * 0.2F + 0.9F));
                this.slider.setDeltaMovement(Vec3.ZERO);
            } else if (!(entity instanceof Player player && player.isCreative()) && !(entity instanceof Slider)) {
                entity.setDeltaMovement(this.slider.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
