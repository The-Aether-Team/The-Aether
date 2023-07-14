package com.aetherteam.aether.entity.monster;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public abstract class AbstractWhirlwind extends Mob {
    public static final EntityDataAccessor<Integer> DATA_COLOR_ID = SynchedEntityData.defineId(AbstractWhirlwind.class, EntityDataSerializers.INT);

    public int lifeLeft;
    public int actionTimer;
    public int stuckTick;
    public float movementAngle;
    public float movementCurve;
    protected boolean isPullingEntity = false;
    protected boolean isEvil = false;

    public AbstractWhirlwind(EntityType<? extends AbstractWhirlwind> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MoveGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.025)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_COLOR_ID, this.getDefaultColor());
    }

    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.setColorData(this.getDefaultColor());
        this.movementAngle = this.random.nextFloat() * 360.0F;
        this.movementCurve = (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
        return super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
    }

    public static boolean checkWhirlwindSpawnRules(EntityType<? extends AbstractWhirlwind> whirlwind, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && level.getRawBrightness(pos, 0) > 12 && Mob.checkMobSpawnRules(whirlwind, level, reason, pos, random);
    }

    /**
     * Warning for "deprecation" is suppressed because vanilla calls {@link LevelReader#getSeaLevel()} just fine.
     */
    @SuppressWarnings("deprecation")
    public boolean checkSpawnObstruction(@Nonnull LevelReader level) {
        if (level.isUnobstructed(this) && !level.containsAnyLiquid(this.getBoundingBox())) {
            return this.getY() >= level.getSeaLevel() + 1;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.lifeLeft--;
        if (!this.level.isClientSide) {
            if (this.lifeLeft <= 0 || this.isInFluidType()) {
                this.discard();
            }
        }
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {
            if (this.verticalCollision && !this.verticalCollisionBelow) {
                this.stuckTick += 4;
            } else if (this.stuckTick > 0) {
                this.stuckTick--;
            }

            if (this.getTarget() != null) {
                this.actionTimer++;
            }
            if (this.actionTimer >= 128) {
                this.handleDrops();
                this.actionTimer = 0;
            }
        } else {
            this.updateParticles();
        }

        super.aiStep();

        /*
          This code is used to move other entities around the whirlwind.
         */
        List<Entity> entityList = this.level.getEntities(this, this.getBoundingBox().expandTowards(2.5, 2.5, 2.5))
                .stream().filter((entity -> !entity.getType().is(AetherTags.Entities.WHIRLWIND_UNAFFECTED))).toList();
        this.isPullingEntity = !entityList.isEmpty();
        for (Entity entity : entityList) {
            double x = (float) entity.getX();
            double y = (float) entity.getY() - entity.getMyRidingOffset() * 0.6F;
            double z = (float) entity.getZ();
            double distance = this.distanceTo(entity);
            double d1 = y - this.getY();

            if (distance <= 1.5 + d1) {
                entity.setDeltaMovement(entity.getDeltaMovement().x, 0.15000000596046448, entity.getDeltaMovement().z);
                entity.resetFallDistance();

                if (d1 > 1.5) {
                    entity.setDeltaMovement(entity.getDeltaMovement().x, -0.44999998807907104 + d1 * 0.34999999403953552, entity.getDeltaMovement().z);
                    distance += d1 * 1.5;
                } else {
                    entity.setDeltaMovement(entity.getDeltaMovement().x, 0.125, entity.getDeltaMovement().z);
                }

                double d2 = Math.atan2(this.getX() - x, this.getZ() - z) / 0.01745329424738884;
                d2 += 160.0;
                entity.setDeltaMovement(-Math.cos(0.01745329424738884 * d2) * (distance + 0.25) * 0.10000000149011612, entity.getDeltaMovement().y, Math.sin(0.01745329424738884 * d2) * (distance + 0.25) * 0.10000000149011612);

                if (entity instanceof AbstractWhirlwind) {
                    entity.discard();
                }
            } else {
                double d3 = Math.atan2(this.getX() - x, this.getZ() - z) / 0.01745329424738884;
                entity.setDeltaMovement(entity.getDeltaMovement().add(Math.sin(0.01745329424738884 * d3) * 0.0099999997764825821, entity.getDeltaMovement().y, Math.cos(0.01745329424738884 * d3) * 0.0099999997764825821));
            }

            if (!this.level.isEmptyBlock(this.blockPosition())) {
                this.lifeLeft -= 50;
            }
        }
        if (this.stuckTick > 40) {
            this.lifeLeft = 0;
        }
    }

    /**
     * This method is called in aiStep to handle the item drop behavior of the whirlwind.
     * This method should only be called on the logical server!
     */
    protected void handleDrops() {
        if (this.random.nextInt(4) == 0) {
            LootContext.Builder builder = new LootContext.Builder((ServerLevel) this.level)
                    .withParameter(LootContextParams.ORIGIN, this.position())
                    .withParameter(LootContextParams.THIS_ENTITY, this);
            LootTable lootTable = this.level.getServer().getLootTables().get(this.getLootLocation());
            List<ItemStack> list = lootTable.getRandomItems(builder.create(LootContextParamSets.SELECTOR));
            for (ItemStack itemstack : list) {
                this.level.playSound(null, this.blockPosition(), AetherSoundEvents.ENTITY_WHIRLWIND_DROP.get(), SoundSource.HOSTILE, 0.5F, 1.0F);
                this.spawnAtLocation(itemstack, 1);
            }
        }
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float damage) {
        return false;
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
    }

    public abstract void updateParticles();

    public abstract ResourceLocation getLootLocation();

    public int getColorData() {
        return this.entityData.get(DATA_COLOR_ID);
    }

    public void setColorData(int color) {
        this.entityData.set(DATA_COLOR_ID, color);
    }

    public abstract int getDefaultColor();

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    protected boolean canRide(@Nonnull Entity vehicle) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Movement Angle", this.movementAngle);
        tag.putFloat("Movement Curve", this.movementCurve);
        tag.putInt("Life Left", this.lifeLeft);
        tag.putInt("Color", this.getColorData());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Movement Angle")) {
            this.movementAngle = tag.getFloat("Movement Angle");
        }
        if (tag.contains("Movement Curve")) {
            this.movementCurve = tag.getFloat("Movement Curve");
        }
        if (tag.contains("Life Left")) {
            this.lifeLeft = tag.getInt("Life Left");
        }
        if (tag.contains("Color")) {
            this.setColorData(tag.getInt("Color"));
        }
    }

    /**
     * This goal handles movement for whirlwinds.
     */
    protected static class MoveGoal extends Goal {
        private final AbstractWhirlwind whirlwind;
        protected float movementAngle;
        protected float movementCurve;

        public MoveGoal(AbstractWhirlwind entity) {
            this.whirlwind = entity;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            if (this.movementAngle == 0) {
                this.movementAngle = this.whirlwind.movementAngle;
                this.movementCurve = this.whirlwind.movementCurve;
            }
            if (!this.whirlwind.isEvil || this.whirlwind.getTarget() == null) {
                BlockPos offset = BlockPos.containing(this.whirlwind.position().add(this.whirlwind.getDeltaMovement()));
                if (this.whirlwind.level.getHeight(Heightmap.Types.WORLD_SURFACE, offset.getX(), offset.getZ()) < offset.getY() - this.whirlwind.getMaxFallDistance()) {
                    this.movementAngle += 180;
                } else {
                    this.movementAngle += this.movementCurve;
                }
                this.whirlwind.setDeltaMovement(Math.cos(this.movementAngle * ((float) Math.PI / 180)) * this.whirlwind.getAttribute(Attributes.MOVEMENT_SPEED).getValue(), this.whirlwind.getDeltaMovement().y, Math.sin(this.movementAngle * ((float) Math.PI / 180)) * this.whirlwind.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
            } else {
                this.whirlwind.setDeltaMovement(Vec3.ZERO);
            }
        }
    }
}
