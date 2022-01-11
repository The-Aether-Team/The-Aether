package com.gildedgames.aether.common.entity.monster;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class Whirlwind extends Mob {
    public static final EntityDataAccessor<Integer> COLOR_DATA = SynchedEntityData.defineId(Whirlwind.class, EntityDataSerializers.INT);

    public int lifeLeft;
    public int actionTimer;
    public float movementAngle;
    public float movementCurve;
    protected boolean isPullingEntity = false;
    protected boolean isEvil = false;

    public Whirlwind(EntityType<? extends Whirlwind> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.movementAngle = this.random.nextFloat() * 360F;
        this.movementCurve = (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public static boolean canWhirlwindSpawn(EntityType<? extends Whirlwind> typeIn, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return randomIn.nextInt(55) == 0
                && worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get()
                && worldIn.getMaxLocalRawBrightness(pos) > 8
                && Mob.checkMobSpawnRules(typeIn, worldIn, reason, pos, randomIn);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.025D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DestroyLeavesGoal(this));
        this.goalSelector.addGoal(2, new MoveGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COLOR_DATA, this.getDefaultColor());
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();
        this.lifeLeft--;
        if (!this.level.isClientSide) {
            if ((this.lifeLeft <= 0 || isInWater())) {
                this.removeAfterChangingDimensions();
            }
        }
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {
            if (this.getTarget() != null) {
                this.actionTimer++;
            }
            if (this.actionTimer >= 128) {
                this.handleDrops();
                this.actionTimer = 0;
                this.level.playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.HOSTILE, 0.5F, 1.0F);
            }
        } else {
            this.updateParticles();
        }
        super.aiStep();

        /**
         * This code is used to move other entities around the whirlwind.
         */
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().expandTowards(2.5D, 2.5D, 2.5D));
        this.isPullingEntity = !list.isEmpty();
        for (Entity entity : list) {
            double d9 = (float) entity.getX();
            double d11 = (float) entity.getY() - entity.getMyRidingOffset() * 0.6F;
            double d13 = (float) entity.getZ();
            double d15 = this.distanceTo(entity);
            double d17 = d11 - this.getY();

            if (d15 <= 1.5D + d17) {
                entity.setDeltaMovement(entity.getDeltaMovement().x, 0.15000000596046448D, entity.getDeltaMovement().z);
                entity.fallDistance = 0.0F;

                if (d17 > 1.5D) {
                    entity.setDeltaMovement(entity.getDeltaMovement().x, -0.44999998807907104D + d17 * 0.34999999403953552D, entity.getDeltaMovement().z);
                    d15 += d17 * 1.5D;
                } else {
                    entity.setDeltaMovement(entity.getDeltaMovement().x, 0.125D, entity.getDeltaMovement().z);
                }

                double d19 = Math.atan2(this.getX() - d9, this.getZ() - d13) / 0.01745329424738884D;
                d19 += 160D;
                entity.setDeltaMovement(-Math.cos(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D, entity.getDeltaMovement().y, Math.sin(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D);

                if (entity instanceof Whirlwind) {
                    entity.discard();
                }
            } else {
                double d20 = Math.atan2(this.getX() - d9, this.getZ() - d13) / 0.01745329424738884D;
                entity.setDeltaMovement(entity.getDeltaMovement().add(Math.sin(0.01745329424738884D * d20) * 0.0099999997764825821D, entity.getDeltaMovement().y, Math.cos(0.01745329424738884D * d20) * 0.0099999997764825821D));
            }

            if (!this.level.isEmptyBlock(this.blockPosition())) {
                this.lifeLeft -= 50;
            }
        }
    }

    /**
     * This method is called in aiStep to handle the item drop behavior of the whirlwind.
     * This method should only be called on the logical server!
     */
    protected abstract void handleDrops();

    @OnlyIn(Dist.CLIENT)
    public abstract void updateParticles();

    @Override
    public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
        return this.level.isUnobstructed(this) //&& this.level.getBlockCollisions(this, this.getBoundingBox()).count() == 0
                && !this.level.containsAnyLiquid(this.getBoundingBox());
    }

    public void setColorData(int color) {
        this.entityData.set(COLOR_DATA, color);
    }

    public int getColorData() {
        return this.entityData.get(COLOR_DATA);
    }

    public abstract int getDefaultColor();

    @Override
    public void addAdditionalSaveData(CompoundTag nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putFloat("movementAngle", this.movementAngle);
        nbttagcompound.putFloat("movementCurve", this.movementCurve);
        nbttagcompound.putInt("lifeLeft", this.lifeLeft);
        nbttagcompound.putInt("color", this.getColorData());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        this.movementAngle = nbttagcompound.getFloat("movementAngle");
        this.movementCurve = nbttagcompound.getFloat("movementCurve");
        this.lifeLeft = nbttagcompound.getInt("lifeLeft");
        this.setColorData(nbttagcompound.getInt("color"));
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        return false;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 3;
    }

    @Override
    public boolean onClimbable() {
        return horizontalCollision;
    }

    /**
     * This goal handles movement for whirlwinds.
     */
    protected static class MoveGoal extends Goal {
        private final Whirlwind whirlwind;
        protected float movementAngle;
        protected float movementCurve;

        public MoveGoal(Whirlwind entity) {
            this.whirlwind = entity;
            this.movementAngle = whirlwind.movementAngle;
            this.movementCurve = whirlwind.movementCurve;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            if (!this.whirlwind.isEvil || this.whirlwind.getTarget() == null) {
                this.whirlwind.setDeltaMovement(Math.cos(0.01745329F * this.movementAngle) * this.whirlwind.getAttribute(Attributes.MOVEMENT_SPEED).getValue(), this.whirlwind.getDeltaMovement().y, -Math.sin(0.01745329F * this.movementAngle) * this.whirlwind.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                this.movementAngle += this.movementCurve;
            } else {
                this.whirlwind.setDeltaMovement(Vec3.ZERO);
            }
        }
    }

    /**
     * This goal destroys leaves when the whirlwind is carrying an entity to make sure they stay out of the way.
     */
    protected static class DestroyLeavesGoal extends Goal { //TODO: Is this goal something we should keep?
        private final Whirlwind whirlwind;

        public DestroyLeavesGoal(Whirlwind entity) {
            this.whirlwind = entity;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return whirlwind.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && this.whirlwind.isPullingEntity;
        }

        @Override
        public void tick() {
            int x = (Mth.floor(whirlwind.getX()) - 1) + whirlwind.random.nextInt(3);
            int y = Mth.floor(whirlwind.getY()) + whirlwind.random.nextInt(5);
            int z = (Mth.floor(whirlwind.getZ()) - 1) + whirlwind.random.nextInt(3);
            if (whirlwind.level.getBlockState(new BlockPos.MutableBlockPos().set(x, y, z)).getBlock() instanceof LeavesBlock) {
                whirlwind.level.destroyBlock(new BlockPos(x, y, z), false);
            }
        }
    }
}
