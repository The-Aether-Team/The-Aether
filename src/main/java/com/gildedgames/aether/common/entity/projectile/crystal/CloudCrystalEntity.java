package com.gildedgames.aether.common.entity.projectile.crystal;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.CloudParticlePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class CloudCrystalEntity extends AbstractCrystalEntity
{
    public CloudCrystalEntity(EntityType<? extends AbstractCrystalEntity> entityType, World world) {
        super(entityType, world);
    }

    public CloudCrystalEntity(World world) {
        super(AetherEntityTypes.CLOUD_CRYSTAL.get(), world);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            float bonus = entity instanceof BlazeEntity ? 3.0F : 0.0F;
            if (livingEntity.hurt(new IndirectEntityDamageSource("ice_crystal", this, this.getOwner()).setProjectile(), 5.0F + bonus)) {
                livingEntity.addEffect(new EffectInstance(Effects.WEAKNESS, 10));
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), this.getImpactExplosionSoundEvent(), SoundCategory.HOSTILE, 2.0F, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F);
                this.spawnExplosionParticles();
                this.remove();
            }
        }
    }

    @Override
    public void spawnExplosionParticles() {
        if (this.level.isClientSide) {
            for (int i = 0; i < 20; ++i) {
                double d0 = (this.random.nextFloat() - 0.5F) * 0.5D;
                double d1 = (this.random.nextFloat() - 0.5F) * 0.5D;
                double d2 = (this.random.nextFloat() - 0.5F) * 0.5D;
                this.level.addParticle(AetherParticleTypes.FREEZER.get(), this.getX(), this.getY(), this.getZ(), d0 * 0.5D, d1 * 0.5D, d2 * 0.5D);
            }
        } else {
            AetherPacketHandler.sendToAll(new CloudParticlePacket(this.getId()));
        }
    }

    @Override
    public SoundEvent getImpactExplosionSoundEvent() {
        return SoundEvents.GLASS_BREAK;
    }
}
