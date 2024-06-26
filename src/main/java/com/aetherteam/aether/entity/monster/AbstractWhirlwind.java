package com.aetherteam.aether.entity.monster;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.utils.FabricUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public abstract class AbstractWhirlwind extends Mob {
    public static final EntityDataAccessor<Integer> DATA_COLOR_ID = SynchedEntityData.defineId(AbstractWhirlwind.class, EntityDataSerializers.INT);

    private int lifeLeft;
    private int dropsTimer;
    private int stuckTick;
    private float movementAngle;
    private float movementCurve;
    private boolean isEvil = false;

    public AbstractWhirlwind(EntityType<? extends AbstractWhirlwind> type, Level level) {
        super(type, level);
        if (level.isClientSide()) {
            this.movementAngle = this.getRandom().nextFloat() * 360.0F;
            this.movementCurve = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MoveGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.025)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_COLOR_ID, this.getDefaultColor());
    }

    /**
     * Whirlwinds can spawn if {@link Mob#checkMobSpawnRules(EntityType, LevelAccessor, MobSpawnType, BlockPos, RandomSource)} is true,
     * if they are spawning at a light level above 12, and if the difficulty isn't peaceful.
     * @param whirlwind The {@link AbstractWhirlwind} {@link EntityType}.
     * @param level The {@link LevelAccessor}.
     * @param reason The {@link MobSpawnType} reason.
     * @param pos The spawn {@link BlockPos}.
     * @param random The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkWhirlwindSpawnRules(EntityType<? extends AbstractWhirlwind> whirlwind, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(whirlwind, level, reason, pos, random)
                && level.getRawBrightness(pos, 0) > 12
                && level.getDifficulty() != Difficulty.PEACEFUL;
    }

    /**
     * Removes a Whirlwind from the world if its lifespan is over or if it is in liquid.
     */
    @Override
    public void tick() {
        super.tick();
        this.lifeLeft--;
        if (!this.level().isClientSide()) {
            if (this.lifeLeft <= 0 || FabricUtils.isInFluidType(this)) {
                this.discard();
            }
        }
    }

    /**
     * Handles the life timer, drops timer, and entity movement modifications.
     */
    @Override
    public void aiStep() {
        if (!this.level().isClientSide()) {
            if (this.verticalCollision && !this.verticalCollisionBelow) { // Marks the Whirlwind as stuck if it is colliding with a ceiling.
                this.stuckTick += 4;
            } else if (this.stuckTick > 0) {
                this.stuckTick--;
            }

            if (this.getTarget() != null) { // Increases the timer to check when to create drops when a player is near.
                this.dropsTimer++;
            }
            if (this.dropsTimer >= 128) {
                this.spawnDrops();
                this.dropsTimer = 0;
            }
        } else {
            this.spawnParticles();
        }

        super.aiStep();

        // This code is used to move other entities around the Whirlwind.
        List<Entity> entityList = this.level().getEntities(this, this.getBoundingBox().expandTowards(2.5, 2.5, 2.5))
                .stream().filter((entity -> !entity.getType().is(AetherTags.Entities.WHIRLWIND_UNAFFECTED))).toList();
        for (Entity entity : entityList) {
            double x = (float) entity.getX();
            double y = (float) entity.getY() - entity.getMyRidingOffset() * 0.6F;
            double z = (float) entity.getZ();
            double distance = this.distanceTo(entity);
            double d1 = y - this.getY();

            if (distance <= 1.5 + d1) {
                entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.15, entity.getDeltaMovement().z());
                entity.resetFallDistance();

                if (d1 > 1.5) {
                    entity.setDeltaMovement(entity.getDeltaMovement().x(), -0.45 + d1 * 0.35, entity.getDeltaMovement().z());
                    distance += d1 * 1.5;
                } else {
                    entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.125, entity.getDeltaMovement().z());
                }

                double d2 = Math.atan2(this.getX() - x, this.getZ() - z) / 0.0175;
                d2 += 160.0;
                entity.setDeltaMovement(-Math.cos(0.0175 * d2) * (distance + 0.25) * 0.1, entity.getDeltaMovement().y, Math.sin(0.0175 * d2) * (distance + 0.25) * 0.1);

                if (entity instanceof AbstractWhirlwind) {
                    entity.discard();
                }
            } else {
                double d3 = Math.atan2(this.getX() - x, this.getZ() - z) / 0.0175;
                entity.setDeltaMovement(entity.getDeltaMovement().add(Math.sin(0.0175 * d3) * 0.01, entity.getDeltaMovement().y, Math.cos(0.0175 * d3) * 0.01));
            }

            if (!this.level().isEmptyBlock(this.blockPosition())) {
                this.lifeLeft -= 50;
            }
        }
        if (this.stuckTick > 40) { // The Whirlwind will be killed if it is stuck for more than 40 ticks.
            this.lifeLeft = 0;
        }
    }

    /**
     * This method is called in aiStep to handle the item drop behavior of the Whirlwind.
     */
    protected void spawnDrops() {
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.getRandom().nextInt(4) == 0) {
                LootParams parameters = new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.SELECTOR);
                LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(this.getLootLocation());
                List<ItemStack> list = lootTable.getRandomItems(parameters);
                for (ItemStack itemstack : list) {
                    serverLevel.playSound(null, this.blockPosition(), AetherSoundEvents.ENTITY_WHIRLWIND_DROP.get(), SoundSource.HOSTILE, 0.5F, 1.0F);
                    this.spawnAtLocation(itemstack, 1);
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        return false;
    }

    /**
     * [CODE COPY] - {@link Entity#kill()}.
     */
    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    public abstract void spawnParticles();

    public abstract ResourceLocation getLootLocation();

    /**
     * @return The {@link Integer} for the decimal color of this Whirlwind.
     */
    public int getColorData() {
        return this.getEntityData().get(DATA_COLOR_ID);
    }

    /**
     * Sets the color of this Whirlwind.
     * @param color The {@link Integer} value for the decimal color.
     */
    public void setColorData(int color) {
        this.getEntityData().set(DATA_COLOR_ID, color);
    }

    /**
     * @return The {@link Integer} for how much life duration is left.
     */
    public int getLifeLeft() {
        return this.lifeLeft;
    }

    /**
     * Sets how much life duration is left.
     * @param lifeLeft The {@link Integer} value.
     */
    public void setLifeLeft(int lifeLeft) {
        this.lifeLeft = lifeLeft;
    }

    /**
     * @return Whether the Whirlwind is evil, as a {@link Boolean}.
     */
    public boolean isEvil() {
        return this.isEvil;
    }

    /**
     * Sets whether the Whirlwind is evil.
     * @param evil The {@link Boolean} value.
     */
    public void setEvil(boolean evil) {
        this.isEvil = evil;
    }

    public abstract int getDefaultColor();

    /**
     * @return A {@link Boolean} for whether the Whirlwind should climb, which is true if it is colliding horizontally with a block.
     */
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
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Movement Angle", this.movementAngle);
        tag.putFloat("Movement Curve", this.movementCurve);
        tag.putInt("Life Left", this.lifeLeft);
        tag.putInt("Color", this.getColorData());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
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
     * This goal handles movement for Whirlwinds.
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
                if (this.whirlwind.level().getHeight(Heightmap.Types.WORLD_SURFACE, offset.getX(), offset.getZ()) < offset.getY() - this.whirlwind.getMaxFallDistance()) {
                    this.movementAngle += 180;
                } else {
                    this.movementAngle += this.movementCurve;
                }
                double modifier = 1.0;
                AttributeInstance speed = this.whirlwind.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speed != null) {
                    modifier = speed.getValue();
                }
                this.whirlwind.setDeltaMovement(Math.cos(this.movementAngle * Mth.DEG_TO_RAD) * modifier, this.whirlwind.getDeltaMovement().y, Math.sin(this.movementAngle * Mth.DEG_TO_RAD) * modifier);
            } else {
                this.whirlwind.setDeltaMovement(Vec3.ZERO);
            }
        }
    }
}
