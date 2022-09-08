package com.gildedgames.aether.entity.monster.dungeon.boss;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.api.BossNameGenerator;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.BossMob;
import com.gildedgames.aether.entity.ai.controller.BlankMoveControl;
import com.gildedgames.aether.entity.ai.goal.target.InBossRoomTargetGoal;
import com.gildedgames.aether.entity.ai.goal.target.MostDamageTargetGoal;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.BossInfoPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

public class Slider extends PathfinderMob implements BossMob<Slider>, Enemy {
    public static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.COMPONENT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_X_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_Z_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);

    private DungeonTracker<Slider> bronzeDungeon;
    private final ServerBossEvent bossFight;
    private MostDamageTargetGoal mostDamageTargetGoal;

    private int chatCooldown;

    private boolean canMove;
    private int moveDelay;
    private float velocity;
    private Direction direction = null;

    public Slider(EntityType<? extends Slider> entityType, Level level) {
        super(entityType, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setVisible(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setRot(0, 0);
        this.moveControl = new BlankMoveControl(this);
        this.setPersistenceRequired();
    }

    /**
     * Generates a name for the boss and adjusts its position.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.alignSpawnPos();
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setBossName(BossNameGenerator.generateSliderName());
        return data;
    }

    /**
     * Aligns the slider with the blocks below it
     */
    protected void alignSpawnPos() {
        this.moveTo(Mth.floor(this.getX()), this.getY(), Mth.floor(this.getZ()));
    }

    @Override
    protected void registerGoals() {
        this.mostDamageTargetGoal = new MostDamageTargetGoal(this);
        this.targetSelector.addGoal(1, this.mostDamageTargetGoal); //todo: will need to verify if this hierarchy of target goals works as intended in multiplayer
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new InBossRoomTargetGoal<>(this, Player.class));
        this.goalSelector.addGoal(1, new SliderMoveGoal(this));
    }

    public static AttributeSupplier.Builder createSliderAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 500.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_AWAKE_ID, false);
        this.entityData.define(DATA_BOSS_NAME_ID, Component.literal("Slider"));
        this.entityData.define(DATA_HURT_ANGLE_ID, 0.0F);
        this.entityData.define(DATA_HURT_ANGLE_X_ID, 0.0F);
        this.entityData.define(DATA_HURT_ANGLE_Z_ID, 0.0F);
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide() && this.getDungeon() == null) {
            DungeonTracker.createDebugDungeon(this);
        }
        super.tick();
        if (!this.isAwake() || (this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }
        if (!this.level.isClientSide() && this.getDungeon() == null && (this.getTarget() == null || !this.getTarget().isAlive() || this.getTarget().getHealth() <= 0.0)) {
            this.reset();
        }
        if (!this.canMove) {
            this.setDeltaMovement(Vec3.ZERO);
        }
        this.collide();
        this.evaporate();

        if (this.chatCooldown > 0) {
            this.chatCooldown--;
        }
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.trackDungeon();
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (source == DamageSource.OUT_OF_WORLD) {
            super.hurt(source, amount);
        } else if (source.getDirectEntity() instanceof LivingEntity livingEntity && this.level.getDifficulty() != Difficulty.PEACEFUL) {
            if (livingEntity.getMainHandItem().is(AetherTags.Items.SLIDER_DAMAGING_ITEMS)) {
                if (super.hurt(source, amount) && this.getHealth() > 0) {
                    if (!this.isBossFight()) {
                        this.start();
                    }
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.75F));

                    double a = Math.abs(this.position().x() - livingEntity.position().x());
                    double c = Math.abs(this.position().z() - livingEntity.position().z());
                    if (a > c) {
                        this.setHurtAngleZ(1);
                        this.setHurtAngleX(0);
                        if (this.position().x() > livingEntity.position().x()) {
                            this.setHurtAngleZ(-1);
                        }
                    } else {
                        this.setHurtAngleX(1);
                        this.setHurtAngleZ(0);
                        if (this.position().z() > livingEntity.position().z()) {
                            this.setHurtAngleX(-1);
                        }
                    }
                    this.setHurtAngle(0.7F - (this.getHealth() / 875.0F));

                    livingEntity.getMainHandItem().hurtAndBreak(1, livingEntity, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));

                    this.mostDamageTargetGoal.addAggro(livingEntity, amount);

                    return true;
                }
            } else {
                if (!this.level.isClientSide && livingEntity instanceof Player player) {
                    if (this.chatCooldown <= 0) {
                        this.displayInvalidToolMessage(player);
                        this.chatCooldown = 15;
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void displayInvalidToolMessage(Player player) {
        player.sendSystemMessage(Component.translatable("gui.aether.slider.message.attack.invalid"));
    }

    @Override
    public void die(@Nonnull DamageSource damageSource) {
        this.setDeltaMovement(Vec3.ZERO);
        this.explode();
        if (this.level instanceof ServerLevel) {
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(damageSource);
                this.tearDownRoom();
            }
        }
        super.die(damageSource);
    }

    private void start() {
        if (this.getAwakenSound() != null) {
            this.playSound(this.getAwakenSound(), 2.5F, 1.0F / (this.random.nextFloat() * 0.2F + 0.9F));
        }
        this.setAwake(true);
        this.setBossFight(true);
        if (this.getDungeon() != null) {
            this.getDungeon().debugBounds();
            this.closeRoom();
        }
    }

    private void collide() {
        if (this.isAwake() && !this.isDeadOrDying()) {
            AABB collisionBounds = new AABB(this.getBoundingBox().minX - 0.1, this.getBoundingBox().minY - 0.1, this.getBoundingBox().minZ - 0.1,
                    this.getBoundingBox().maxX + 0.1, this.getBoundingBox().maxY + 0.1, this.getBoundingBox().maxZ + 0.1);
            for (Entity entity : this.getLevel().getEntities(this, collisionBounds)) {
                if (entity instanceof LivingEntity livingEntity && entity.hurt(new EntityDamageSource("crush", this), 6)) {
                    if (livingEntity instanceof Player player && player.getUseItem().is(Items.SHIELD) && player.isBlocking()) {
                        player.getCooldowns().addCooldown(Items.SHIELD, 100);
                        player.stopUsingItem();
                        this.level.broadcastEntityEvent(player, (byte) 30);
                    }
                    entity.setDeltaMovement(entity.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
                    this.playSound(this.getCollideSound(), 2.5F, 1.0F / (this.random.nextFloat() * 0.2F + 0.9F));
                    this.stop();
                } else if (!(entity instanceof Player player && player.isCreative()) && !(entity instanceof Slider)) {
                    entity.setDeltaMovement(this.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
                }
            }
        }
    }

    private void evaporate() {
        AABB entity = this.getBoundingBox();
        BlockPos min = new BlockPos(entity.minX - 1, entity.minY - 1, entity.minZ - 1);
        BlockPos max = new BlockPos(Math.ceil(entity.maxX - 1) + 1, Math.ceil(entity.maxY - 1) + 1, Math.ceil(entity.maxZ - 1) + 1);
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (this.level.getBlockState(pos).getBlock() instanceof LiquidBlock) {
                this.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                this.blockDestroySmoke(pos);
            } else if (!this.level.getFluidState(pos).isEmpty()) {
                this.level.setBlockAndUpdate(pos, this.level.getBlockState(pos).setValue(BlockStateProperties.WATERLOGGED, false));
            }
        }
    }

    private void stop() {
        this.canMove = false;
        this.moveDelay = 12;
        this.direction = Direction.UP;
        this.velocity = 0.0F;
        this.setDeltaMovement(Vec3.ZERO);
    }

    public void reset() {
        this.stop();
        this.setAwake(false);
        this.setBossFight(false);
        this.setTarget(null);
        this.setHealth(this.getMaxHealth());
        if (this.getDungeon() != null) {
            this.setPos(this.getDungeon().originCoordinates());
            this.openRoom();
        }
    }

    /**
     * Called on every block in the dungeon when the boss is defeated.
     */
    @Override
    @Nullable
    public BlockState convertBlock(BlockState state) {
        if (state.is(AetherBlocks.LOCKED_CARVED_STONE.get())) {
            return AetherBlocks.CARVED_STONE.get().defaultBlockState();
        }
        if (state.is(AetherBlocks.LOCKED_SENTRY_STONE.get())) {
            return AetherBlocks.SENTRY_STONE.get().defaultBlockState();
        }
        if (state.is(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get()) || state.is(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get())) {
            return Blocks.AIR.defaultBlockState();
        }
        return null;
    }

    private void explode() {
        for (int i = 0; i < (this.getHealth() <= 0 ? 16 : 48); i++) {
            double x = this.position().x() + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
            double y = this.getBoundingBox().minY + 1.75 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
            double z = this.position().z() + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
            this.level.addParticle(ParticleTypes.POOF, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    private void blockDestroySmoke(BlockPos pos) {
        double a = pos.getX() + 0.5D + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375D;
        double b = pos.getY() + 0.5D + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375D;
        double c = pos.getZ() + 0.5D + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375D;
        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.POOF, a, b, c, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    @Override
    public void startSeenByPlayer(@Nonnull ServerPlayer player) {
        super.startSeenByPlayer(player);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId()), player);
    }

    /**
     * Removes the given player from the list of players tracking this entity.
     */
    @Override
    public void stopSeenByPlayer(@Nonnull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId()), player);
        this.bossFight.removePlayer(player);
    }

    @Override
    public void onDungeonPlayerAdded(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.addPlayer(serverPlayer);
        }
    }

    @Override
    public void onDungeonPlayerRemoved(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.removePlayer(serverPlayer);
        }
    }

    public boolean isAwake() {
        return this.entityData.get(DATA_AWAKE_ID);
    }

    public void setAwake(boolean ready) {
        this.entityData.set(DATA_AWAKE_ID, ready);
    }

    @Override
    public Component getBossName() {
        return this.entityData.get(DATA_BOSS_NAME_ID);
    }

    @Override
    public void setBossName(Component component) {
        this.entityData.set(DATA_BOSS_NAME_ID, component);
        this.bossFight.setName(component);
    }

    public float getHurtAngleX() {
        return this.entityData.get(DATA_HURT_ANGLE_X_ID);
    }

    public void setHurtAngleX(float hurtAngleX) {
        this.entityData.set(DATA_HURT_ANGLE_X_ID, hurtAngleX);
    }

    public float getHurtAngleZ() {
        return this.entityData.get(DATA_HURT_ANGLE_Z_ID);
    }

    public void setHurtAngleZ(float hurtAngleZ) {
        this.entityData.set(DATA_HURT_ANGLE_Z_ID, hurtAngleZ);
    }

    public float getHurtAngle() {
        return this.entityData.get(DATA_HURT_ANGLE_ID);
    }

    public void setHurtAngle(float hurtAngle) {
        this.entityData.set(DATA_HURT_ANGLE_ID, hurtAngle);
    }

    @Override
    public DungeonTracker<Slider> getDungeon() {
        return this.bronzeDungeon;
    }

    @Override
    public void setDungeon(DungeonTracker<Slider> dungeon) {
        this.bronzeDungeon = dungeon;
    }


    @Override
    public int getDeathScore() {
        return this.deathScore;
    }

    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }

    public boolean isCritical() {
        return this.getHealth() <= 125;
    }

    protected SoundEvent getAwakenSound() {
        return AetherSoundEvents.ENTITY_SLIDER_AWAKEN.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_SLIDER_AMBIENT.get();
    }

    public SoundEvent getCollideSound() {
        return AetherSoundEvents.ENTITY_SLIDER_COLLIDE.get();
    }

    public SoundEvent getMoveSound() {
        return AetherSoundEvents.ENTITY_SLIDER_MOVE.get();
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_SLIDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SLIDER_DEATH.get();
    }

    @Nonnull
    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.setBossName(name);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target.canBeSeenAsEnemy();
    }

    @Override
    public void knockback(double pStrength, double pX, double pZ) {

    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    public float getYRot() {
        return 0;
    }

    @Override
    protected boolean canRide(@Nonnull Entity vehicle) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean shouldDiscardFriction() {
        return true;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean isFullyFrozen() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("BossName", Component.Serializer.toJson(this.getBossName()));
        tag.putBoolean("BossFight", this.isBossFight());
        tag.putBoolean("Awake", this.isAwake());
        if (this.getDungeon() != null) {
            tag.put("Dungeon", this.getDungeon().addAdditionalSaveData());
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag); //todo: Should abstract this duplicated code to the boss interface
        if (tag.contains("BossName")) {
            Component name = Component.Serializer.fromJson(tag.getString("BossName"));
            if (name != null) {
                this.setBossName(name);
            }
        }
        if (tag.contains("BossFight")) {
            this.setBossFight(tag.getBoolean("BossFight"));
        }
        if (tag.contains("Awake")) {
            this.setAwake(tag.getBoolean("Awake"));
        }
        if (tag.contains("Dungeon") && tag.get("Dungeon") instanceof CompoundTag dungeonTag) {
            this.setDungeon(DungeonTracker.readAdditionalSaveData(dungeonTag, this));
        }
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public int getMoveDelay() {
        return this.moveDelay;
    }

    public void setMoveDelay(int moveDelay) {
        this.moveDelay = moveDelay;
    }

    public void decreaseMoveDelay(int amount) {
        this.moveDelay -= amount;
    }

    public float getVelocity() {
        return this.velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public void increaseVelocity(float amount) {
        this.velocity += amount;
    }

    public int getChatCooldown() {
        return this.chatCooldown;
    }
    public void setChatCooldown(int cooldown) {
        this.chatCooldown = cooldown;
    }

    public void increaseChatTime(int amount) {
        this.chatCooldown += amount;
    }

    public boolean canMove() {
        return this.canMove;
    }

    public void setCanMove(boolean move) {
        this.canMove = move;
    }

    public static class SliderMoveGoal extends Goal {
        protected final Slider slider;

        public SliderMoveGoal(Slider slider) {
            this.slider = slider;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.slider.isAwake() && this.slider.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return this.slider.isAwake() && this.slider.getTarget() != null;
        }

        @Override
        public void stop() {
            this.slider.stop();
        }

        @Override
        public void tick() {
            if (this.slider.getTarget() != null) {
                if (this.slider.canMove) {
                    boolean crushed = this.crushedBlocks();
                    if (!crushed) {
                        if (this.slider.velocity < this.getMaxVelocity()) {
                            // The Slider increases it's speed based on the speed it has saved
                            this.slider.velocity += this.getVelocityIncrease();
                        }
                        this.slider.setDeltaMovement(Vec3.ZERO);
                        if (this.slider.direction != null) {
                            // The Slider moves based on its direction
                            this.slider.setDeltaMovement(this.slider.direction.getStepX() * this.slider.velocity, this.slider.direction.getStepY() * this.slider.velocity, this.slider.direction.getStepZ() * this.slider.velocity);
                            if (this.reachedTarget()) {
                                // Once the Slider reaches its target, it stops moving, and starts the movement delay timer.
                                this.stop();
                                this.slider.moveDelay = this.calculateMoveDelay();
                            }
                        }
                    }
                } else if (this.slider.moveDelay > 0) {
                    // When the Slider decreases its move delay
                    this.slider.decreaseMoveDelay(this.getMoveDelayDecrease());
                    this.slider.setDeltaMovement(Vec3.ZERO);
                } else {
                    // When the Slider starts moving
                    this.slider.direction = this.calculateDirection();
                    this.slider.playSound(this.slider.getMoveSound(), 2.5F, 1.0F / (this.slider.getRandom().nextFloat() * 0.2F + 0.9F));
                    this.slider.canMove = true;
                }
            } else {
                // The Slider stops moving if it's target is null
                this.stop();
            }
            // When hitting walls, the Slider sometimes moves up a TINY fraction of a block.
            // This realigns it as long as it's not moving up or down and is within 1/20 of a block above or below a block.
            this.alignNearY();
        }

        /** Calculates what direction the slider should slide */
        public Direction calculateDirection() {
            Direction newDirection;

            double xDiff = Math.abs(this.slider.position().x() - this.slider.getTarget().position().x());
            double yDiff = Math.abs(this.slider.getBoundingBox().minY - this.slider.getTarget().getBoundingBox().minY);
            double zDiff = Math.abs(this.slider.position().z() - this.slider.getTarget().position().z());

            if (xDiff > zDiff) {
                newDirection = Direction.EAST;
                if (this.slider.position().x() > this.slider.getTarget().position().x()) {
                    newDirection = Direction.WEST;
                }
            } else {
                newDirection = Direction.SOUTH;
                if (this.slider.position().z() > this.slider.getTarget().position().z()) {
                    newDirection = Direction.NORTH;
                }
            }

            if // Dungeon exists
            ((((this.slider.getDungeon() != null &&
            // Target is too high to be hit if the Slider was on the floor
            this.slider.getTarget().getBoundingBox().minY >= (this.getFloorLevel() + this.slider.getBoundingBox().getYsize()))
            // The target is above the slider
            && (this.slider.getBoundingBox().maxY - this.slider.getTarget().getBoundingBox().minY) <= 0)
            // The target is farther away on the Y axis
            && (yDiff > xDiff && yDiff > zDiff))) {
                newDirection = Direction.UP;
            }

            if // Slider is much further above than any other axis
            ((((yDiff > (xDiff * 0.75F) && yDiff > (zDiff * 0.75F))
            // slider is above target
            && (this.slider.getBoundingBox().minY > this.slider.getTarget().getBoundingBox().minY))
            // Slider has a dungeon
            || (this.slider.getDungeon() != null
            // Target is low enough to be hit if the Slider is on the ground
            && (((this.slider.getTarget().getBoundingBox().minY < (this.getFloorLevel() + (this.slider.getBoundingBox().getYsize()))))
            // Slider is not already on the floor
            && this.slider.getBoundingBox().minY > this.getFloorLevel())))) {
                newDirection = Direction.DOWN;
            }
            return newDirection;
        }

        public boolean reachedTarget()
        {
            if (this.slider.direction == Direction.UP) {
                return
                // If the dungeon exists
                (this.slider.getDungeon() != null ?
                // Return whether or not the player is low enough to be hit from the floor
                (((this.slider.getTarget().getBoundingBox().minY < (this.getFloorLevel() + this.slider.getBoundingBox().getYsize()))
                // Or/Otherwise whether or not the slider has reached the player's hitbox
                || ((this.slider.getBoundingBox().minY > this.slider.getTarget().getBoundingBox().minY)))
                // Or the slider has reached the dungeon ceiling
                || this.slider.getBoundingBox().maxY >= this.getCeilingLevel())
                : (this.slider.getBoundingBox().minY > this.slider.getTarget().getBoundingBox().minY));
            } else if (this.slider.direction == Direction.DOWN) {
                return
                // If the dungeon exists, and the player's height is low enough that it could be hit by the slider if it was on the floor (the bottom of the player's hitbox is below where the top of the slider's hitbox would be if it was on the floor)
                (this.slider.getDungeon() != null &&
                (this.slider.getTarget().getBoundingBox().minY < (this.getFloorLevel() + this.slider.getBoundingBox().getYsize())))
                // then return whether or not the slider is on the ground already
                ? this.slider.getBoundingBox().minY <= this.getFloorLevel() :
                // Otherwise, return the default logic (whether or not the Slider is above the player)
                ((this.slider.getBoundingBox().minY <= this.slider.getTarget().getBoundingBox().minY));

            } else if (this.slider.direction == Direction.EAST) {
                return  (this.slider.position().x() > this.slider.getTarget().position().x() + 0.125);
            } else if (this.slider.direction == Direction.WEST) {
                return (this.slider.position().x() < this.slider.getTarget().position().x() - 0.125);
            } else if (this.slider.direction == Direction.SOUTH) {
                return (this.slider.position().z() > this.slider.getTarget().position().z() + 0.125);
            } else if (this.slider.direction == Direction.NORTH) {
                return  (this.slider.position().z() < this.slider.getTarget().position().z() - 0.125);
            }
            return false;
        }

        protected boolean crushedBlocks() {
            AABB entity = this.slider.getBoundingBox();
            BlockPos minInside = new BlockPos(entity.minX, entity.minY, entity.minZ);
            BlockPos maxInside = new BlockPos(Math.ceil(entity.maxX - 1.0), Math.ceil(entity.maxY - 1.0), Math.ceil(entity.maxZ - 1.0));
            if (this.crush(minInside, maxInside, true)) {
                return true;
            } else {
                BlockPos min;
                BlockPos max;
                if (this.slider.direction == Direction.UP) {
                    min = new BlockPos(entity.minX, entity.maxY, entity.minZ);
                    max = new BlockPos(Math.ceil(entity.maxX - 1.0), entity.maxY, Math.ceil(entity.maxZ - 1.0));
                    return this.crush(min, max, false);
                } else if (this.slider.direction == Direction.DOWN) {
                    // The Slider does not try to break the dungeon floor
                    min = new BlockPos(entity.minX, (this.slider.getDungeon() != null ? Math.max(entity.minY - 1.0, this.slider.getDungeon().roomBounds().minY + 1.0F) : entity.minY - 1.0), entity.minZ);
                    max = new BlockPos(Math.ceil(entity.maxX - 1.0), (this.slider.getDungeon() != null ? Math.min(entity.maxY - 1.0, this.slider.getDungeon().roomBounds().maxY - 1.0F) : entity.maxY - 1.0), Math.ceil(entity.maxZ - 1.0));
                    return this.crush(min, max, false);
                } else if (this.slider.direction == Direction.EAST) {
                    min = new BlockPos(entity.maxX, entity.minY, entity.minZ);
                    max = new BlockPos(entity.maxX, Math.ceil(entity.maxY - 1.0), Math.ceil(entity.maxZ - 1.0));
                    return this.crush(min, max, false);
                } else if (this.slider.direction == Direction.WEST) {
                    min = new BlockPos(entity.minX - 1.0, entity.minY, entity.minZ);
                    max = new BlockPos(entity.minX - 1.0, Math.ceil(entity.maxY - 1.0), Math.ceil(entity.maxZ - 1.0));
                    return this.crush(min, max, false);
                } else if (this.slider.direction == Direction.SOUTH) {
                    min = new BlockPos(entity.minX, entity.minY, entity.maxZ);
                    max = new BlockPos(Math.ceil(entity.maxX - 1.0), Math.ceil(entity.maxY - 1.0), entity.maxZ);
                    return this.crush(min, max, false);
                } else if (this.slider.direction == Direction.NORTH) {
                    min = new BlockPos(entity.minX, entity.minY, entity.minZ - 1.0);
                    max = new BlockPos(Math.ceil(entity.maxX - 1.0), Math.ceil(entity.maxY - 1.0), entity.minZ - 1.0);
                    return this.crush(min, max, false);
                } else {
                    return false;
                }
            }
        }

        protected boolean crush(BlockPos min, BlockPos max, boolean isInside) {
            boolean flag = false;
            if (this.slider.getDeltaMovement().equals(Vec3.ZERO) || isInside) {
                for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
                    BlockState blockState = this.slider.level.getBlockState(pos);
                    if (!blockState.isAir() && !blockState.is(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS)) {
                        if (ForgeEventFactory.getMobGriefingEvent(this.slider.level, this.slider) && this.slider.getDungeon() != null && this.slider.getDungeon().roomBounds().contains(Vec3.atCenterOf(pos))) {
                            this.slider.level.destroyBlock(pos, true, this.slider);
                            this.blockDestroySmoke(pos);
                            this.slider.level.playSound(null, this.slider.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 3.0F, (0.625F + (this.slider.random.nextFloat() - this.slider.random.nextFloat()) * 0.2F) * 0.7F);
                            this.slider.playSound(this.slider.getCollideSound(), 2.5F, 1.0F / (this.slider.random.nextFloat() * 0.2F + 0.9F));
                            this.stop();
                            flag = true;
                        }
                    }
                }
            }
            return flag;
        }

        /** Returns the Slider which this goal is for */
        public Slider getSlider() {
            return this.slider;
        }

        /** Destroys a block and spawns smoke. Added so addons can use this private method in their custom Slider AI, if they have one */
        protected void blockDestroySmoke(BlockPos pos){
            this.slider.blockDestroySmoke(pos);
        }

        /** Sets the slider's position to the block it is on, because it sometimes moves slightly up (or possibly down, added just in case) */
        protected void alignNearY() {
            if (this.slider.getY() % 1 != 0.0) {
                if (this.slider.getY() % 1 <= 0.05 && (this.slider.direction.getAxis() != Direction.Axis.Y)) {
                    this.slider.setPos(this.slider.getX(), Mth.floor(this.slider.getY()), this.slider.getZ());
                } else if (this.slider.getY() % 1 >= 0.95 && (this.slider.direction.getAxis() != Direction.Axis.Y)) {
                    this.slider.setPos(this.slider.getX(), Mth.ceil(this.slider.getY()), this.slider.getZ());
                }
            }
        }

        /** Returns how much should be added to the Slider's velocity when sliding */
        protected float getVelocityIncrease() {
            float velocity = this.slider.isCritical() ? 0.07F : 0.035F;
            return velocity;
        }

        /** Returns what the delay of the Slider's movement should be set to when it stops, based on whether or not the Slider is in the critical stage or not */
        protected int calculateMoveDelay() {
            return this.slider.isCritical() ? 4 : 8;
        }

        /** Returns the decrease (negative) that the move delay should decrease each tick */
        protected int getMoveDelayDecrease() {
            return (this.slider.isCritical() && this.slider.moveDelay > 1 && this.slider.random.nextInt(2) == 0) ? 2 : 1;
        }

        /** Returns the fastest the slider can go */
        protected float getMaxVelocity() {
            return 2.0F;
        }

        /** Returns the Y level of the floor */
        protected double getFloorLevel() {
            return this.slider.getDungeon().roomBounds().minY + 1;
        }

        /** Returns the Y level of the ceiling */
        protected double getCeilingLevel() {
            return this.slider.getDungeon().roomBounds().maxY;
        }
    }
}