package com.aetherteam.aether.entity.monster;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.projectile.PoisonNeedle;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class AechorPlant extends PathfinderMob implements RangedAttackMob {
    public static final EntityDataAccessor<Integer> DATA_SIZE_ID = SynchedEntityData.defineId(AechorPlant.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_POISON_REMAINING_ID = SynchedEntityData.defineId(AechorPlant.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> DATA_TARGETING_ENTITY_ID = SynchedEntityData.defineId(AechorPlant.class, EntityDataSerializers.BOOLEAN);

    public float sinage;
    public float sinageAdd;

    public AechorPlant(EntityType<? extends AechorPlant> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,  new RangedAttackGoal(this, 1.0, 60, 10.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SIZE_ID, 0);
        this.entityData.define(DATA_POISON_REMAINING_ID, 0);
        this.entityData.define(DATA_TARGETING_ENTITY_ID, false);
    }

    @Override
    public void onSyncedDataUpdated(@Nonnull EntityDataAccessor<?> dataAccessor) {
        if (DATA_SIZE_ID.equals(dataAccessor)) {
            this.setBoundingBox(this.makeBoundingBox());
        }
        super.onSyncedDataUpdated(dataAccessor);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.setPos(Math.floor(this.getX()) + 0.5, this.getY(), Math.floor(this.getZ()) + 0.5);
        this.setSize(this.random.nextInt(4) + 1);
        this.setPoisonRemaining(2);
        this.sinage = this.random.nextFloat() * 6.0F;
        return super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
    }

    public static boolean checkAechorPlantSpawnRules(EntityType<? extends AechorPlant> aechorPlant, LevelAccessor level, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && level.getBlockState(pos.below()).is(AetherTags.Blocks.AECHOR_PLANT_SPAWNABLE_ON)
                && level.getRawBrightness(pos, 0) > 8
                && (spawnReason != MobSpawnType.NATURAL || random.nextInt(10) == 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.getBlockState(this.blockPosition().below()).is(AetherTags.Blocks.AECHOR_PLANT_SPAWNABLE_ON)) {
            this.kill();
        }

        if (!this.level.isClientSide()) {
            if (this.getTarget() != null) {
                this.setTargetingEntity(true);
            } else if (this.getTarget() == null && this.getTargetingEntity()) {
                this.setTargetingEntity(false);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.sinage += this.sinageAdd;
        if (this.hurtTime > 0) {
            this.sinageAdd = 0.45F;
        } else if (this.getTargetingEntity()) {
            this.sinageAdd = 0.3F;
        } else {
            this.sinageAdd = 0.15F;
        }
        if (this.sinage >= Mth.TWO_PI) {
            this.sinage -= Mth.TWO_PI;
        }
    }

    @Override
    protected void jumpFromGround() { }

    @Nonnull
    @Override
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(AetherItems.SKYROOT_BUCKET.get()) && this.getPoisonRemaining() > 0) {
            this.setPoisonRemaining(this.getPoisonRemaining() - 1);
            ItemStack itemStack1 = ItemUtils.createFilledResult(itemStack, player, AetherItems.SKYROOT_POISON_BUCKET.get().getDefaultInstance());
            player.setItemInHand(hand, itemStack1);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    protected void doPush(@Nonnull Entity entity) {
        if (!this.isPassengerOfSameVehicle(entity)) {
            if (!entity.noPhysics && !this.noPhysics) {
                double d0 = entity.getX() - this.getX();
                double d1 = entity.getZ() - this.getZ();
                double d2 = Mth.absMax(d0, d1);
                if (d2 >= (double) 0.01F) {
                    d2 = Math.sqrt(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0 / d2;
                    if (d3 > 1.0) {
                        d3 = 1.0;
                    }

                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.05F;
                    d1 *= 0.05F;

                    if (!entity.isVehicle()) {
                        entity.push(d0, 0.0, d1);
                    }
                }
            }
        }
    }

    @Override
    public boolean hurt(@Nonnull DamageSource damageSource, float amount) {
        if (this.hurtTime == 0) {
            for (int i = 0; i < 8; ++i) {
                double d1 = this.getX() + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
                double d2 = this.getY() + 0.25 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
                double d3 = this.getZ() + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
                double d4 = (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
                double d5 = (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.5;
                this.level.addParticle(ParticleTypes.PORTAL, d1, d2, d3, d4, 0.25, d5);
            }
        }
        return super.hurt(damageSource, amount);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        PoisonNeedle needle = new PoisonNeedle(this.level, this);
        double x = target.getX() - this.getX();
        double z = target.getZ() - this.getZ();
        double sqrt = Math.sqrt(x * x + z * z + 0.1);
        double y = 0.1 + sqrt * 0.5 + (this.getY() - target.getY()) * 0.25;
        double distance = 1.5 / sqrt;
        x *= distance;
        z *= distance;
        needle.shoot(x, y + 0.5F, z, 0.285F + (float) y * 0.08F, 1.0F);
        this.playSound(AetherSoundEvents.ENTITY_AECHOR_PLANT_SHOOT.get(), 2.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(needle);
    }

    @Nonnull
    @Override
    protected AABB makeBoundingBox() {
        float width = 0.75F + this.getSize() * 0.125F;
        float height = 0.5F + this.getSize() * 0.075F;
        EntityDimensions newDimensions = EntityDimensions.fixed(width, height);
        return newDimensions.makeBoundingBox(this.position());
    }

    public int getSize() {
        return this.entityData.get(DATA_SIZE_ID);
    }

    public void setSize(int size) {
        this.entityData.set(DATA_SIZE_ID, size);
    }

    public int getPoisonRemaining() {
        return this.entityData.get(DATA_POISON_REMAINING_ID);
    }

    public void setPoisonRemaining(int poisonRemaining) {
        this.entityData.set(DATA_POISON_REMAINING_ID, poisonRemaining);
    }

    public boolean getTargetingEntity() {
        return this.entityData.get(DATA_TARGETING_ENTITY_ID);
    }

    public void setTargetingEntity(boolean targetingEntity) {
        this.entityData.set(DATA_TARGETING_ENTITY_ID, targetingEntity);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_AECHOR_PLANT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_AECHOR_PLANT_DEATH.get();
    }

    @Override
    public boolean hasLineOfSight(@Nonnull Entity entity) {
        return this.distanceTo(entity) <= 8.0 && super.hasLineOfSight(entity);
    }

    @Override
    protected float getStandingEyeHeight(@Nonnull Pose pose, @Nonnull EntityDimensions size) {
        return 0.5F;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance potionEffect) {
        return potionEffect.getEffect() != AetherEffects.INEBRIATION.get() && super.canBeAffected(potionEffect);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Size", this.getSize());
        tag.putInt("Poison Remaining", this.getPoisonRemaining());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Size")) {
            this.setSize(tag.getInt("Size"));
        }
        if (tag.contains("Poison Remaining")) {
            this.setPoisonRemaining(tag.getInt("Poison Remaining"));
        }
    }
}


