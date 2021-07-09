package com.gildedgames.aether.common.entity.projectile.combat;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Arrays;

public abstract class AbstractDartEntity extends AbstractArrowEntity
{
    private int ticksInAir = 0;

    protected AbstractDartEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AbstractDartEntity(EntityType<? extends AbstractArrowEntity> type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 500) {
            this.remove();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        float f = (float)this.getDeltaMovement().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.getBaseDamage(), 0.0D, 2.147483647E9D));

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.arrow(this, this);
        } else {
            damagesource = DamageSource.arrow(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        if (entity.hurt(damagesource, (float)i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                if (this.knockback > 0) {
                    Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D);
                    if (vector3d.lengthSqr() > 0.0D) {
                        livingentity.push(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
                }
            }

            this.playSound(this.getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.remove();
        } else {
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.yRot += 180.0F;
            this.yRotO += 180.0F;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                if (this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.remove();
            }
        }
        this.setNoGravity(false);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {
        super.onHitBlock(result);
        this.setNoGravity(false);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return AetherSoundEvents.ENTITY_DART_HIT.get();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
