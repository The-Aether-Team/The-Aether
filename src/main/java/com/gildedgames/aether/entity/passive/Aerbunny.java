package com.gildedgames.aether.entity.passive;

import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.ExplosionParticlePacket;
import com.gildedgames.aether.network.packet.server.AerbunnyPuffPacket;
import com.gildedgames.aether.util.EntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public class Aerbunny extends AetherAnimal {
    public static final EntityDataAccessor<Integer> DATA_PUFFINESS_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> DATA_FAST_FALLING = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.BOOLEAN);

    public int puffSubtract;
    private boolean afraid;
    private Vec3 lastPos;

    public Aerbunny(EntityType<? extends Aerbunny> type, Level level) {
        super(type, level);
        this.moveControl = new AerbunnyMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RunLikeHellGoal(this, 1.3));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2, Ingredient.of(AetherTags.Items.AERBUNNY_TEMPTATION_ITEMS), false));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new FallingRandomStrollGoal(this, 1.0, 80));
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PUFFINESS_ID, 0);
        this.entityData.define(DATA_FAST_FALLING, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isFastFalling()) {
            this.handleFallSpeed();
        } else if (this.onGround) {
            this.setFastFalling(false);
        }
        this.setPuffiness(this.getPuffiness() - this.puffSubtract);
        if (this.getPuffiness() > 0) {
            this.puffSubtract = 1;
        } else {
            this.puffSubtract = 0;
            this.setPuffiness(0);
        }
        this.handlePlayerInput();
        if (this.isOnGround() || !this.getFeetBlockState().isAir() || (this.getVehicle() != null && (this.getVehicle().isOnGround() || !this.getVehicle().getFeetBlockState().isAir()))) {
            this.lastPos = null;
        }
    }

    @Override
    protected float getFlyingSpeed() {
        return this.getSpeed() * 0.21600002F;
    }

    private void handleFallSpeed() {
        AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1);
            if (this.getDeltaMovement().y < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
            }
        }
    }

    private void handlePlayerInput() {
        if (this.getVehicle() instanceof Player player) {
            if (player.isSpectator()) {
                this.stopRiding();
            }

            EntityUtil.copyRotations(this, player);

            player.resetFallDistance();
            if (!player.isOnGround() && !player.isFallFlying()) {
                AttributeInstance playerGravity = player.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
                if (playerGravity != null) {
                    if (!player.getAbilities().flying && !player.isInWater() && !player.isInLava() && playerGravity.getValue() > 0.02) {
                        player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.05, 0.0));
                    }
                }
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    if (this.level.isClientSide) {
                        if (player.getDeltaMovement().y <= 0.0) {
                            if (this.lastPos == null) {
                                this.lastPos = this.position();
                            }
                            if (!player.isOnGround() && aetherPlayer.isJumping() && player.getDeltaMovement().y <= 0.0 && this.position().y() < this.lastPos.y() - 1.1) {
                                player.setDeltaMovement(player.getDeltaMovement().x, 0.125, player.getDeltaMovement().z);
                                AetherPacketHandler.sendToServer(new AerbunnyPuffPacket(this.getId()));
                                this.lastPos = null;
                            }
                        }
                    }
                });
            } else if (player.isFallFlying()) {
                this.stopRiding();
            }
            if (player instanceof ServerPlayer serverPlayer) {
                ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
            }
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.isAlive() && this.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && !this.level.getBlockState(BlockPos.containing(this.getX(), this.getEyeY(), this.getZ())).is(Blocks.BUBBLE_COLUMN)
                && this.isPassenger() && this.getVehicle() != null && !this.getVehicle().canBeRiddenUnderFluidType(ForgeMod.WATER_TYPE.get(), this) && this.level.isClientSide) {
            this.stopRiding();
        }
    }

    @Nonnull
    @Override
    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        InteractionResult result = super.mobInteract(player, hand);
        if (!(this.getVehicle() instanceof Player vehicle) || vehicle.equals(player)) {
            if (player.isShiftKeyDown() || result == InteractionResult.PASS || result == InteractionResult.FAIL) {
                return this.ridePlayer(player);
            }
        }
        return result;
    }

    private InteractionResult ridePlayer(Player player) {
        if (!this.isBaby()) {
            if (this.isPassenger()) {
                this.stopRiding();
                this.setFastFalling(true);
                Vec3 playerMovement = player.getDeltaMovement();
                this.setDeltaMovement(playerMovement.x * 5, playerMovement.y * 0.5 + 0.5, playerMovement.z * 5);
            } else if (this.startRiding(player)) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setMountedAerbunny(this));
                this.level.playSound(player, this, AetherSoundEvents.ENTITY_AERBUNNY_LIFT.get(), SoundSource.NEUTRAL, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void stopRiding() {
        if (this.getVehicle() instanceof Player player) {
            AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setMountedAerbunny(null));
        }
        super.stopRiding();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean flag = super.hurt(pSource, pAmount);
        if (flag && pSource.getEntity() instanceof Player) {
            this.setAfraid(true);
        }
        return flag;
    }

    /**
     * Handle the small hops in the air
     */
    protected void midairJump() {
        Vec3 motion = this.getDeltaMovement();
        if (motion.y < 0) {
            this.puff();
        }
        this.setDeltaMovement(new Vec3(motion.x, 0.25, motion.z));
    }

    public void puff() {
        if (this.level instanceof ServerLevel) {
            this.setPuffiness(11);
            this.spawnExplosionParticle();
        }
    }

    private void spawnExplosionParticle() {
        AetherPacketHandler.sendToNear(new ExplosionParticlePacket(this.getId(), 5), this.getX(), this.getY(), this.getZ(), 10.0, level.dimension());
    }

    public int getPuffiness() {
        return this.entityData.get(DATA_PUFFINESS_ID);
    }

    public void setPuffiness(int i) {
        this.entityData.set(DATA_PUFFINESS_ID, i);
    }

    public boolean isFastFalling() {
        return this.entityData.get(DATA_FAST_FALLING);
    }

    public void setFastFalling(boolean flag) {
        this.entityData.set(DATA_FAST_FALLING, flag);
    }

    public boolean isAfraid() {
        return this.afraid;
    }

    public void setAfraid(boolean fear) {
        this.afraid = fear;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherTags.Items.AERBUNNY_TEMPTATION_ITEMS);
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
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
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Afraid", this.afraid);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.afraid = tag.getBoolean("Afraid");
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

    public static class RunLikeHellGoal extends Goal {
        private final Aerbunny aerbunny;
        private final double speedModifier;

        public RunLikeHellGoal(Aerbunny aerbunny, double speedModifier) {
            this.aerbunny = aerbunny;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.aerbunny.isAfraid();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.aerbunny.getNavigation().isDone() && this.aerbunny.random.nextInt(20) != 0;
        }

        @Override
        public void start() {
            LivingEntity attacker = this.aerbunny.level.getNearestPlayer(this.aerbunny, 12);
            if (attacker == null) {
                return;
            }
            Vec3 position = this.aerbunny.position();
            double angle = Mth.atan2(position.x() - attacker.getX(), position.z() - attacker.getZ());
            float angleOffset = this.aerbunny.random.nextFloat() * 2 - 1;
            angle += angleOffset * 0.75;
            double x = position.x() + Math.sin(angle) * 8;
            double z = position.z() + Math.cos(angle) * 8;
            boolean flag = this.aerbunny.navigation.moveTo(x, this.aerbunny.getY(), z, this.speedModifier);
            if (!flag) {
                this.aerbunny.getLookControl().setLookAt(attacker, 30, 30);
            }
        }

        @Override
        public void tick() {
            if (this.aerbunny.getRandom().nextInt(4) == 0) {
                ((ServerLevel)this.aerbunny.level).sendParticles(ParticleTypes.SPLASH, this.aerbunny.getRandomX(0.5), this.aerbunny.getRandomY(), this.aerbunny.getRandomZ(0.5), 2, 0, 0, 0, 0);
            }
        }
    }

    public static class AerbunnyMoveControl extends MoveControl {
        private final Aerbunny aerbunny;

        public AerbunnyMoveControl(Aerbunny aerbunny) {
            super(aerbunny);
            this.aerbunny = aerbunny;
        }

        @Override
        public void tick() {
            super.tick();
            if (this.aerbunny.zza != 0) {
                if (this.aerbunny.onGround) {
                    this.aerbunny.jumpControl.jump();
                } else {
                    int x = Mth.floor(this.aerbunny.getX());
                    int y = Mth.floor(this.aerbunny.getBoundingBox().minY);
                    int z = Mth.floor(this.aerbunny.getZ());
                    if (this.checkForSurfaces(this.aerbunny.level, x, y, z)) {
                        this.aerbunny.midairJump();
                    }
                }
            }
        }

        private boolean checkForSurfaces(Level level, int x, int y, int z) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);
            if (level.getBlockState(pos.setY(y - 1)).isAir()) {
                return false;
            }
            return level.getBlockState(pos.setY(y + 2)).isAir() && level.getBlockState(pos.setY(y + 1)).isAir();
        }
    }
}
