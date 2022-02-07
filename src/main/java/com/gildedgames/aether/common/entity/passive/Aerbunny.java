package com.gildedgames.aether.common.entity.passive;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.ai.FallingRandomStrollGoal;
import com.gildedgames.aether.common.entity.ai.navigator.FallPathNavigator;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.util.EntityUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public class Aerbunny extends AetherAnimalEntity
{
    public static final EntityDataAccessor<Integer> DATA_PUFFINESS_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);

    public Aerbunny(EntityType<? extends Aerbunny> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(AetherTags.Items.AERBUNNY_TEMPTATION_ITEMS), false));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new HopGoal(this));
        this.goalSelector.addGoal(6, new FallingRandomStrollGoal(this, 2.0, 6));
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return new FallPathNavigator(this, level);
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PUFFINESS_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();
        double fallSpeed = this.hasEffect(MobEffects.SLOW_FALLING) ? -0.05 : -0.1;
        if (this.getDeltaMovement().y < fallSpeed) {
            this.setDeltaMovement(getDeltaMovement().x, fallSpeed, getDeltaMovement().z);
        }
        this.setPuffiness(this.getPuffiness() - 1);
        if (this.getPuffiness() < 0) {
            this.setPuffiness(0);
        }
        if (this.getVehicle() instanceof Player player) {
            EntityUtil.copyRotations(this, player);

            player.resetFallDistance();
            if (!player.isOnGround() && !player.isFallFlying()) {
                if (!player.getAbilities().flying && !player.isInWater() && !player.isInLava()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.05, 0.0));
                }
                IAetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer.isJumping() && player.getDeltaMovement().y < -0.225) {
                        player.setDeltaMovement(player.getDeltaMovement().x, 0.125, player.getDeltaMovement().z);
                        if (!this.level.isClientSide) {
                            this.puff();
                        }
                    }
                });
            } else if (player.isFallFlying()) {
                this.stopRiding();
            }
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.isAlive() && this.isEyeInFluid(FluidTags.WATER) && !this.level.getBlockState(new BlockPos(this.getX(), this.getEyeY(), this.getZ())).is(Blocks.BUBBLE_COLUMN)
                && this.isPassenger() && this.getVehicle() != null && !this.getVehicle().canBeRiddenInWater(this) && this.level.isClientSide) {
            this.stopRiding();
        }
    }

    @Nonnull
    @Override
    public InteractionResult mobInteract(Player player, @Nonnull InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            return this.ridePlayer(player);
        } else {
            InteractionResult result = super.mobInteract(player, hand);
            if (result == InteractionResult.PASS || result == InteractionResult.FAIL) {
                return this.ridePlayer(player);
            }
            return result;
        }
    }

    private InteractionResult ridePlayer(Player player) {
        if (!this.isBaby()) {
            this.level.playSound(player, this, AetherSoundEvents.ENTITY_AERBUNNY_LIFT.get(), SoundSource.NEUTRAL, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            if (this.isPassenger()) {
                this.navigation.recomputePath();
                this.stopRiding();
                IAetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setMountedAerbunny(null));
            } else {
                if (this.startRiding(player)) {
                    IAetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setMountedAerbunny(this));
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.puff();
    }

    private void puff() {
        this.setPuffiness(11);
        this.spawnExplosionParticle();
    }

    private void spawnExplosionParticle() {
        if (this.level instanceof ServerLevel) {
            for (int i = 0; i < 5; i++) {
                EntityUtil.spawnMovementExplosionParticles(this);
            }
        }
    }

    public int getPuffiness() {
        return this.entityData.get(DATA_PUFFINESS_ID);
    }

    public void setPuffiness(int i) {
        this.entityData.set(DATA_PUFFINESS_ID, i);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherTags.Items.AERBUNNY_TEMPTATION_ITEMS);
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source) {
        return AetherSoundEvents.ENTITY_AERBUNNY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_AERBUNNY_DEATH.get();
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public double getMyRidingOffset() {
        return this.getVehicle() != null && this.getVehicle().isCrouching() ? 0.4 : 0.575;
    }

    @Override
    public boolean isInvulnerableTo(@Nonnull DamageSource damageSource) {
        return (this.getVehicle() != null && this.getVehicle() == damageSource.getEntity()) || super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean isInWall() {
        return !this.isPassenger() && super.isInWall();
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@Nonnull ServerLevel level, @Nonnull AgeableMob entity) {
        return AetherEntityTypes.AERBUNNY.get().create(level);
    }

    public static class HopGoal extends Goal
    {
        private final Aerbunny aerbunny;

        public HopGoal(Aerbunny entity) {
            this.aerbunny = entity;
            setFlags(EnumSet.of(Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return this.aerbunny.getDeltaMovement().z > 0.0 || this.aerbunny.getDeltaMovement().x > 0.0 || this.aerbunny.onGround;
        }

        @Override
        public void tick() {
            if (this.aerbunny.getDeltaMovement().x != 0.0 || this.aerbunny.getDeltaMovement().z != 0.0) {
                this.aerbunny.jumpControl.jump();
            }
        }
    }
}
