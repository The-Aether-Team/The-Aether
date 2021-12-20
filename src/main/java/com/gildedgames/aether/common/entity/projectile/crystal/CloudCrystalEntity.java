package com.gildedgames.aether.common.entity.projectile.crystal;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.util.*;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;

public class CloudCrystalEntity extends AbstractCrystalEntity
{
    public CloudCrystalEntity(EntityType<? extends AbstractCrystalEntity> entityType, Level world) {
        super(entityType, world);
    }

    public CloudCrystalEntity(Level world) {
        super(AetherEntityTypes.CLOUD_CRYSTAL.get(), world);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            float bonus = entity instanceof Blaze ? 3.0F : 0.0F;
            if (livingEntity.hurt(new IndirectEntityDamageSource("ice_crystal", this, this.getOwner()).setProjectile(), 5.0F + bonus)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10));
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), this.getImpactExplosionSoundEvent(), SoundSource.HOSTILE, 2.0F, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F);
                this.spawnExplosionParticles();
                this.discard();
            }
        }
    }

    @Override
    public void spawnExplosionParticles() {
        if (this.level instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) this.level;
            for (int i = 0; i < 20; i++) {
                double d0 = (this.random.nextFloat() - 0.5F) * 0.5D;
                double d1 = (this.random.nextFloat() - 0.5F) * 0.5D;
                double d2 = (this.random.nextFloat() - 0.5F) * 0.5D;
                world.sendParticles(AetherParticleTypes.FROZEN.get(), this.getX(), this.getY(), this.getZ(), 1, d0 * 0.5D, d1 * 0.5D, d2 * 0.5D, 0.0F);
            }
        }
    }

    @Override
    public SoundEvent getImpactExplosionSoundEvent() {
        return AetherSoundEvents.ENTITY_CLOUD_CRYSTAL_EXPLODE.get();
    }
}
