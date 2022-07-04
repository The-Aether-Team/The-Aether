package com.gildedgames.aether.entity.projectile.dart;

import com.gildedgames.aether.client.AetherSoundEvents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public abstract class AbstractDart extends AbstractArrow {
    private int ticksInAir = 0;

    protected AbstractDart(EntityType<? extends AbstractDart> type, Level level) {
        super(type, level);
    }

    public AbstractDart(EntityType<? extends AbstractDart> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 500) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float f = (float) this.getDeltaMovement().length();
        int i = Mth.ceil(Mth.clamp((double) f * this.getBaseDamage(), 0.0, 2.147483647E9));

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.arrow(this, this);
        } else {
            damagesource = DamageSource.arrow(this, entity1);
            if (entity1 instanceof LivingEntity livingEntity) {
                livingEntity.setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        if (this.getBaseDamage() == 0.0 || entity.hurt(damagesource, (float)i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity livingentity) {
                if (this.getKnockback() > 0) {
                    Vec3 vector3d = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale((double) this.getKnockback() * 0.6);
                    if (vector3d.lengthSqr() > 0.0) {
                        livingentity.push(vector3d.x, 0.1, vector3d.z);
                    }
                }

                if (!this.level.isClientSide && entity1 instanceof LivingEntity livingEntity1) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects(livingEntity1, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if (livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer serverPlayer && !this.isSilent()) {
                    serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            }

            this.playSound(this.getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        } else {
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);

            this.yRotO += 180.0F;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            }
        }
        this.setNoGravity(false);
    }

    @Override
    protected void onHitBlock(@Nonnull BlockHitResult result) {
        super.onHitBlock(result);
        this.setNoGravity(false);
    }

    @Nonnull
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return AetherSoundEvents.ENTITY_DART_HIT.get();
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
