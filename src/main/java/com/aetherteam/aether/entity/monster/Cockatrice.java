package com.aetherteam.aether.entity.monster;

import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.NotGrounded;
import com.aetherteam.aether.entity.WingedBird;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.entity.ai.navigator.FallPathNavigation;
import com.aetherteam.aether.entity.projectile.PoisonNeedle;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.AetherTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;

import javax.annotation.Nonnull;

public class Cockatrice extends Monster implements RangedAttackMob, WingedBird, NotGrounded {
    private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(Cockatrice.class, EntityDataSerializers.BOOLEAN);

    public float wingRotation;
    public float prevWingRotation;
    public float destPos;
    public float prevDestPos;

    private int flapCooldown;

    public Cockatrice(EntityType<? extends Cockatrice> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2,  new RangedAttackGoal(this, 1.0, 60, 10.0F));
        this.goalSelector.addGoal(3, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return new FallPathNavigation(this, level);
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ENTITY_ON_GROUND_ID, true);
    }

    public static boolean checkCockatriceSpawnRules(EntityType<? extends Cockatrice> cockatrice, ServerLevelAccessor level, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(AetherTags.Blocks.COCKATRICE_SPAWNABLE_BLACKLIST) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(level, pos, random) && Mob.checkMobSpawnRules(cockatrice, level, spawnReason, pos, random) && (spawnReason != MobSpawnType.NATURAL || random.nextInt(3) == 0);
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensiontype = level.dimensionType();
            int i = dimensiontype.monsterSpawnBlockLightLimit();
            if (i < 15 && level.getBrightness(LightLayer.BLOCK, pos) > i) {
                return false;
            } else {
                return level.getMaxLocalRawBrightness(pos) <= dimensiontype.monsterSpawnLightTest().sample(random);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.animateWings();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnGround()) {
            this.setEntityOnGround(true);
        }

        AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1);
            if (this.getDeltaMovement().y < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
                this.hasImpulse = true;
                this.setEntityOnGround(false);
            }
        }

        if (this.getFlapCooldown() > 0) {
            this.setFlapCooldown(this.getFlapCooldown() - 1);
        } else if (this.getFlapCooldown() == 0) {
            if (!this.isOnGround()) {
                this.level.playSound(null, this, AetherSoundEvents.ENTITY_COCKATRICE_FLAP.get(), SoundSource.NEUTRAL, 0.15F, Mth.clamp(this.random.nextFloat(), 0.7F, 1.0F) + Mth.clamp(this.random.nextFloat(), 0.0F, 0.3F));
                this.setFlapCooldown(15);
            }
        }
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.setEntityOnGround(false);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        PoisonNeedle needle = new PoisonNeedle(this.level, this);
        double d0 = target.getX() - this.getX();
        double d1 = target.getBoundingBox().minY + (double) (target.getBbHeight() / 3.0F) - needle.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Mth.sqrt((float) (Mth.square(d0) + Mth.square(d2)));
        needle.shoot(d0, d1 + d3 * 0.2000000029802322, d2, 1.0F, (float) (14 - this.level.getDifficulty().getId() * 4));
        this.playSound(AetherSoundEvents.ENTITY_COCKATRICE_SHOOT.get(), 2.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(needle);
    }

    @Override
    public boolean isEntityOnGround() {
        return this.entityData.get(DATA_ENTITY_ON_GROUND_ID);
    }

    @Override
    public void setEntityOnGround(boolean onGround) {
        this.entityData.set(DATA_ENTITY_ON_GROUND_ID, onGround);
    }

    @Override
    public float getWingRotation() {
        return this.wingRotation;
    }

    @Override
    public void setWingRotation(float rot) {
        this.wingRotation = rot;
    }

    @Override
    public float getPrevWingRotation() {
        return this.prevWingRotation;
    }

    @Override
    public void setPrevWingRotation(float rot) {
        this.prevWingRotation = rot;
    }

    @Override
    public float getDestPos() {
        return this.destPos;
    }

    @Override
    public void setDestPos(float pos) {
        this.destPos = pos;
    }

    @Override
    public float getPrevDestPos() {
        return this.prevDestPos;
    }

    @Override
    public void setPrevDestPos(float pos) {
        this.prevDestPos = pos;
    }

    public int getFlapCooldown() {
        return this.flapCooldown;
    }

    public void setFlapCooldown(int flapCooldown) {
        this.flapCooldown = flapCooldown;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_COCKATRICE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_COCKATRICE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_COCKATRICE_DEATH.get();
    }

    @Override
    public int getMaxFallDistance() {
        return this.isOnGround() ? super.getMaxFallDistance() : 14;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance potionEffect) {
        return potionEffect.getEffect() != AetherEffects.INEBRIATION.get() && super.canBeAffected(potionEffect);
    }
}
