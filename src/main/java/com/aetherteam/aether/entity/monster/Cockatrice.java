package com.aetherteam.aether.entity.monster;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.NotGrounded;
import com.aetherteam.aether.entity.WingedBird;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.entity.ai.navigator.FallPathNavigation;
import com.aetherteam.aether.entity.passive.MountableAnimal;
import com.aetherteam.aether.entity.projectile.PoisonNeedle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.NeoForgeMod;

public class Cockatrice extends Monster implements RangedAttackMob, WingedBird, NotGrounded {
    private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(Cockatrice.class, EntityDataSerializers.BOOLEAN);

    private float wingRotation;
    private float prevWingRotation;
    private float destPos;
    private float prevDestPos;
    private int flapCooldown;

    public Cockatrice(EntityType<? extends Cockatrice> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 60, 10.0F));
        this.goalSelector.addGoal(3, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FallPathNavigation(this, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ENTITY_ON_GROUND_ID, true);
    }

    /**
     * Cockatrices can spawn if {@link Mob#checkMobSpawnRules(EntityType, LevelAccessor, MobSpawnType, BlockPos, RandomSource)} and {@link Cockatrice#isDarkEnoughToSpawn(ServerLevelAccessor, BlockPos, RandomSource)} are true,
     * if the block at the spawn location isn't in the {@link AetherTags.Blocks#COCKATRICE_SPAWNABLE_BLACKLIST} tag, if the difficulty isn't peaceful, and they spawn with a random chance of 1/3.
     *
     * @param cockatrice The {@link Cockatrice} {@link EntityType}.
     * @param level      The {@link LevelAccessor}.
     * @param reason     The {@link MobSpawnType} reason.
     * @param pos        The spawn {@link BlockPos}.
     * @param random     The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkCockatriceSpawnRules(EntityType<? extends Cockatrice> cockatrice, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(cockatrice, level, reason, pos, random)
                && isDarkEnoughToSpawn(level, pos, random)
                && !level.getBlockState(pos.below()).is(AetherTags.Blocks.COCKATRICE_SPAWNABLE_BLACKLIST)
                && level.getDifficulty() != Difficulty.PEACEFUL
                && (reason != MobSpawnType.NATURAL || random.nextInt(3) == 0);
    }

    /**
     * [CODE COPY] - {@link Monster#isDarkEnoughToSpawn(ServerLevelAccessor, BlockPos, RandomSource)}
     * Thundering weather checks are not included.
     */
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

    /**
     * Handles wing animation.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        this.animateWings();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround()) {
            this.setEntityOnGround(true);
        }

        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                this.hasImpulse = true;
                this.setEntityOnGround(false);
            }
        }

        // Handles flap cooldown for sounds.
        if (this.getFlapCooldown() > 0) {
            this.setFlapCooldown(this.getFlapCooldown() - 1);
        } else if (this.getFlapCooldown() == 0) {
            if (!this.onGround()) {
                this.level().playSound(null, this, AetherSoundEvents.ENTITY_COCKATRICE_FLAP.get(), SoundSource.NEUTRAL, 0.15F, Mth.clamp(this.getRandom().nextFloat(), 0.7F, 1.0F) + Mth.clamp(this.getRandom().nextFloat(), 0.0F, 0.3F));
                this.setFlapCooldown(15);
            }
        }
    }

    /**
     * [CODE COPY] - {@link MountableAnimal#jumpFromGround()}.
     */
    @Override
    public void jumpFromGround() {
        super.jumpFromGround();
        this.setEntityOnGround(false);
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.monster.AbstractSkeleton#performRangedAttack(LivingEntity, float)}.<br><br>
     * The y-scale of the Poison Needle's origin position is increased.
     */
    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        PoisonNeedle needle = new PoisonNeedle(this.level(), this);
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.75) - needle.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Mth.sqrt((float) (Mth.square(d0) + Mth.square(d2)));
        needle.shoot(d0, d1 + d3 * 0.2, d2, 1.0F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(AetherSoundEvents.ENTITY_COCKATRICE_SHOOT.get(), 2.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(needle);
    }

    /**
     * @return Whether this entity has been set as on the ground, as a {@link Boolean} value.
     */
    @Override
    public boolean isEntityOnGround() {
        return this.getEntityData().get(DATA_ENTITY_ON_GROUND_ID);
    }

    /**
     * Sets whether this entity is on the ground.
     *
     * @param onGround The {@link Boolean} value.
     */
    @Override
    public void setEntityOnGround(boolean onGround) {
        this.getEntityData().set(DATA_ENTITY_ON_GROUND_ID, onGround);
    }

    /**
     * @return The {@link Float} value for the wing rotation; used for animation.
     */
    @Override
    public float getWingRotation() {
        return this.wingRotation;
    }

    /**
     * Sets the wing rotation for animation.
     *
     * @param rotation The {@link Float} value.
     */
    @Override
    public void setWingRotation(float rotation) {
        this.wingRotation = rotation;
    }

    /**
     * @return The previous {@link Float} value for the wing rotation; used for animation.
     */
    @Override
    public float getPrevWingRotation() {
        return this.prevWingRotation;
    }

    /**
     * Sets the previous wing rotation for animation.
     *
     * @param rotation The {@link Float} value.
     */
    @Override
    public void setPrevWingRotation(float rotation) {
        this.prevWingRotation = rotation;
    }

    /**
     * @return The {@link Float} value for the amplitude of how far the wings should rotate during animation.
     */
    @Override
    public float getWingDestPos() {
        return this.destPos;
    }

    /**
     * Sets the amplitude of how far the wings should rotate during animation.
     *
     * @param pos The {@link Float} value.
     */
    @Override
    public void setWingDestPos(float pos) {
        this.destPos = pos;
    }

    /**
     * @return The previous {@link Float} value for the amplitude of how far the wings should rotate during animation.
     */
    @Override
    public float getPrevWingDestPos() {
        return this.prevDestPos;
    }

    /**
     * Sets the previous amplitude of how far the wings should rotate during animation.
     *
     * @param pos The {@link Float} value.
     */
    @Override
    public void setPrevWingDestPos(float pos) {
        this.prevDestPos = pos;
    }

    /**
     * @return The {@link Integer} value for how long until the Cockatrice can play the flap sound effect again.
     */
    public int getFlapCooldown() {
        return this.flapCooldown;
    }

    /**
     * Sets how long until the Cockatrice can play the flap sound effect again.
     *
     * @param flapCooldown The {@link Integer} value.
     */
    public void setFlapCooldown(int flapCooldown) {
        this.flapCooldown = flapCooldown;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_COCKATRICE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_COCKATRICE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_COCKATRICE_DEATH.get();
    }

    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() ? super.getMaxFallDistance() : 14;
    }

    /**
     * Makes Cockatrices immune to Inebriation.
     *
     * @param effect The {@link MobEffectInstance} to check whether this mob is affected by.
     * @return Whether the mob is affected.
     */
    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != AetherEffects.INEBRIATION.get() && super.canBeAffected(effect);
    }
}
