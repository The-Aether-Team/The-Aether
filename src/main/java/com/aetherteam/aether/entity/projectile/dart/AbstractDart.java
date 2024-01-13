package com.aetherteam.aether.entity.projectile.dart;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.mixin.mixins.common.accessor.PlayerAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.Nullable;
import java.util.function.Supplier;

public abstract class AbstractDart extends AbstractArrow {
    @Nullable
    private final Supplier<Item> pickupItem;
    private int ticksInAir = 0;

    protected AbstractDart(EntityType<? extends AbstractDart> type, Level level, Supplier<Item> pickupItem) {
        super(type, level);
        this.pickupItem = pickupItem;
    }

    public AbstractDart(EntityType<? extends AbstractDart> type, Level level, LivingEntity shooter, Supplier<Item> pickupItem) {
        super(type, shooter, level);
        this.pickupItem = pickupItem;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround()) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 500) {
            if (!this.level().isClientSide()) {
                this.discard();
            }
        }
    }

    /**
     * Handles shield damaging when this projectile hits an entity.
     * @param result The {@link HitResult} of the projectile.
     */
    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof Player player && player.isBlocking()) {
                PlayerAccessor playerAccessor = (PlayerAccessor) player;
                playerAccessor.callHurtCurrentlyUsedShield(3.0F);
            }
        }
    }

    /**
     * [CODE COPY] - {@link AbstractArrow#onHitEntity(EntityHitResult)}.<br><br>
     * Removed behavior for the Piercing enchantment.
     */
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float f = (float) this.getDeltaMovement().length();
        int i = Mth.ceil(Mth.clamp((double) f * this.getBaseDamage(), 0.0, Integer.MAX_VALUE));

        if (this.isCritArrow()) {
            long j = this.random.nextInt(i / 2 + 2);
            i = (int) Math.min(j + (long) i, 2147483647L);
        }

        Entity owner = this.getOwner();
        DamageSource damageSource;
        if (owner == null) {
            damageSource = this.damageSources().arrow(this, this);
        } else {
            damageSource = this.damageSources().arrow(this, owner);
            if (owner instanceof LivingEntity livingEntity) {
                livingEntity.setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        if (entity.hurt(damageSource, (float) i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity livingentity) {
                if (this.getKnockback() > 0) {
                    double d0 = Math.max(0.0, 1.0 - livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale((double) this.getKnockback() * 0.6 * d0);
                    if (vec3.lengthSqr() > 0.0) {
                        livingentity.push(vec3.x(), 0.1, vec3.z());
                    }
                }

                if (!this.level().isClientSide() && owner instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, owner);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)owner, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if (livingentity != owner && livingentity instanceof Player && owner instanceof ServerPlayer serverPlayer && !this.isSilent()) {
                    serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            }

            this.playSound(this.getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        } else {
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level().isClientSide() && this.getDeltaMovement().lengthSqr() < 1.0E-7) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            }
        }
        this.setNoGravity(false); // Restores gravity to the dart when it hits an entity.
    }

    /**
     * Restores gravity to the dart when it hits a block.
     * @param result The {@link BlockHitResult} of the projectile.
     */
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.setNoGravity(false);
    }
   
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return AetherSoundEvents.ENTITY_DART_HIT.get();
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.pickupItem != null ? new ItemStack(this.pickupItem.get()) : ItemStack.EMPTY;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TicksInAir", this.ticksInAir);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("TicksInAir")) {
            this.ticksInAir = tag.getInt("TicksInAir");
        }
    }
   
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
