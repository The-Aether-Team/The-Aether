package com.gildedgames.aether.common.entity.passive;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.ai.FallingRandomWalkingGoal;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class AerbunnyEntity extends AetherAnimalEntity
{
    public static final DataParameter<Integer> DATA_PUFFINESS_ID = EntityDataManager.defineId(AerbunnyEntity.class, DataSerializers.INT);

    public AerbunnyEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AerbunnyEntity(World worldIn) {
        this(AetherEntityTypes.AERBUNNY.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(AetherItems.BLUE_BERRY.get()), false));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new HopGoal(this));
        this.goalSelector.addGoal(6, new FallingRandomWalkingGoal(this, 2.0D, 6));
    }

    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.MAX_HEALTH, 5.0D);
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PUFFINESS_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();
        this.setPuffiness(this.getPuffiness() - 1);
        if (this.getPuffiness() < 0) {
            this.setPuffiness(0);
        }
        if (this.getVehicle() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) this.getVehicle();
            this.yRot = player.yRot;
            this.yRotO = this.yRot;
            this.xRot = player.xRot * 0.5F;
            this.setRot(this.yRot, this.xRot);
            this.yBodyRot = this.yRot;
            this.yHeadRot = this.yBodyRot;

            player.fallDistance = 0.0F;

            if (!player.isOnGround() && !player.isFallFlying()) {
                if (!player.abilities.flying) {
                    player.setDeltaMovement(player.getDeltaMovement().add(0.0D, 0.05D, 0.0D));
                }
                IAetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer.isJumping() && player.getDeltaMovement().y < -0.225D) {
                        player.setDeltaMovement(player.getDeltaMovement().x, 0.125D, player.getDeltaMovement().z);
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

    @Override
    public void aiStep() {
        if (this.getDeltaMovement().y < -0.1D) {
            this.setDeltaMovement(getDeltaMovement().x, -0.1D, getDeltaMovement().z);
        }
        super.aiStep();
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (player.isShiftKeyDown()) {
            return this.ridePlayer(player);
        } else {
            ActionResultType result = super.mobInteract(player, hand);
            if (result == ActionResultType.PASS || result == ActionResultType.FAIL) {
                return this.ridePlayer(player);
            }
            return result;
        }
    }

    private ActionResultType ridePlayer(PlayerEntity player) {
        if (!this.isBaby()) {
            this.level.playSound(player, this, AetherSoundEvents.ENTITY_AERBUNNY_LIFT.get(), SoundCategory.NEUTRAL, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            if (this.isPassenger()) {
                this.navigation.recomputePath();
                this.stopRiding();
                IAetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setAerbunny(null));
            } else {
                if (this.startRiding(player)) {
                    IAetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setAerbunny(this.getUUID()));
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
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
        if (this.level instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) this.level;
            for (int i = 0; i < 5; i++) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                double d3 = 10.0D;
                double x = this.getX() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (this.getBbWidth() - d0 * d3);
                double y = this.getY() + (double) (this.random.nextFloat() * this.getBbHeight()) - d1 * d3;
                double z = this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (this.getBbWidth() - d2 * d3);
                world.sendParticles(ParticleTypes.POOF, x, y, z, 1, d0, d1, d2, 0.0F);
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
    protected SoundEvent getHurtSound(DamageSource source) {
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
        return this.getVehicle() != null && this.getVehicle().isCrouching() ? 0.4D : 0.575D;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
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
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return AetherEntityTypes.AERBUNNY.get().create(this.level);
    }

    public static class HopGoal extends Goal
    {
        private final AerbunnyEntity aerbunny;

        public HopGoal(AerbunnyEntity entity) {
            this.aerbunny = entity;
            setFlags(EnumSet.of(Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return this.aerbunny.getDeltaMovement().z > 0.0D || this.aerbunny.getDeltaMovement().x > 0.0D || this.aerbunny.onGround;
        }

        @Override
        public void tick() {
            if (this.aerbunny.getDeltaMovement().x != 0.0F || aerbunny.getDeltaMovement().z != 0.0F) {
                this.aerbunny.jumpControl.jump();
            }
        }
    }
}
