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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

    private SliderMoveGoal moveGoal;

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
        this.targetSelector.addGoal(1, this.mostDamageTargetGoal);
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new InBossRoomTargetGoal<>(this, Player.class));
        this.moveGoal = new SliderMoveGoal(this);
        this.goalSelector.addGoal(1, this.moveGoal);
    }

    public static AttributeSupplier.Builder createSliderAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 400.0)
                .add(Attributes.FOLLOW_RANGE, 64.0);
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
        this.collide();
        this.evaporate();

        if (this.getChatCooldown() > 0) {
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
                    if (this.getChatCooldown() <= 0) {
                        this.displayInvalidToolMessage(player);
                        this.setChatCooldown(15);
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
                    this.moveGoal.stop();
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
                this.evaporateEffects(pos);
            } else if (!this.level.getFluidState(pos).isEmpty()) {
                this.level.setBlockAndUpdate(pos, this.level.getBlockState(pos).setValue(BlockStateProperties.WATERLOGGED, false));
                this.evaporateEffects(pos);
            }
        }
    }

    private void evaporateEffects(BlockPos pos) {
        this.blockDestroySmoke(pos);
        this.level.playSound(null, pos, AetherSoundEvents.WATER_EVAPORATE.get(), SoundSource.BLOCKS, 0.5F, 2.6F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.8F);
    }

    private void stop() {
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
        if (state.is(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get())) {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.is(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get())) {
            return AetherBlocks.SKYROOT_TRAPDOOR.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING));
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
        if (this.getDungeon() != null && this.getDungeon().isPlayerWithinRoom(player)) {
            this.bossFight.addPlayer(player);
        }
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
        return this.getHealth() <= 100;
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
    public void push(double x, double y, double z) {

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

    public int getChatCooldown() {
        return this.chatCooldown;
    }

    public void setChatCooldown(int cooldown) {
        this.chatCooldown = cooldown;
    }

    /**
     * This goal allows the slider to pick a spot to move to while avoiding unbreakable blocks.
     */
    public static class SliderMoveGoal extends Goal {
        protected final Slider slider;
        @Nullable
        protected BlockPos targetPoint;
        private Direction moveDir;
        private float velocity;
        private int moveDelay;

        public SliderMoveGoal(Slider slider) {
            this.slider = slider;
        }

        @Override
        public boolean canUse() {
            return this.slider.isAwake() && this.slider.getTarget() != null;
        }

        @Override
        public void tick() {
            BlockPos targetPos = this.slider.getTarget().blockPosition();
            BlockPos currentPos = this.slider.blockPosition();
            BlockPos difference = targetPos.subtract(currentPos);
            Direction lastDirection = this.moveDir;

            // Calculate a new target position for the slider to use
            if (this.targetPoint == null || this.moveDir != null && this.axisDistance(difference.getX(), difference.getY(), difference.getZ(), this.moveDir) <= 0) {
                this.moveDir = this.calculateDirection(difference.getX(), difference.getY(), difference.getZ());
                AABB pathBox = this.calculatePathBox(this.slider.getBoundingBox(), difference, this.moveDir);

                // If the block next to the slider is unbreakable, move up first.
                if (this.moveDir.getAxis() == Direction.Axis.Y || !this.setPathUpOrDown(pathBox, currentPos, targetPos, difference, this.moveDir)) {
                    BlockPos.MutableBlockPos offset = new BlockPos.MutableBlockPos();
                    // Find the next point in the path.
                    while (Math.abs(offset.getX()) <= Math.abs(difference.getX()) && Math.abs(offset.getY()) <= Math.abs(difference.getY()) && Math.abs(offset.getZ()) <= Math.abs(difference.getZ())) {
                        offset.move(this.moveDir);
                        if (this.slider.level.getBlockState(currentPos.offset(offset)).is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                            break;
                        }
                    }
                    offset.move(this.moveDir.getOpposite());
                    this.targetPoint = currentPos.offset(offset);
                }
                if (this.moveDir != lastDirection) {
                    this.stop();
                }
            }

            // Move along the calculated path
            if (this.targetPoint != null) {
                if (this.moveDelay > 0) {
                    if (--this.moveDelay <= 0) {
                        this.slider.playSound(this.slider.getMoveSound(), 2.5F, 1.0F / (this.slider.getRandom().nextFloat() * 0.2F + 0.9F));
                    }
                } else {
                    if (this.crush()) {
                        this.stop();
                        return;
                    }

                    if (this.velocity < this.getMaxVelocity()) {
                        // The Slider increases its speed based on the speed it has saved
                        this.velocity = Math.min(this.getMaxVelocity(), this.velocity + this.getVelocityIncrease());
                    }

                    if (this.moveDir == null) { // If the direction has changed
                        double x = this.targetPoint.getX() - this.slider.getX();
                        double y = this.targetPoint.getY() - this.slider.getY();
                        double z = this.targetPoint.getZ() - this.slider.getZ();
                        this.moveDir = this.calculateDirection(x, y, z);
                    }

                    Vec3 directionVec = new Vec3(this.moveDir.getStepX(), this.moveDir.getStepY(), this.moveDir.getStepZ());
                    Vec3 movement = directionVec.scale(this.velocity);

                    this.slider.setDeltaMovement(movement);
                }
            }
        }

        /**
         * Checks if the slider should move up or down. If so, set the path in that direction and return true;
         * @param currentPath - The expanded AABB including both the slider and its target point
         * @param currentPos - The slider's current position
         * @param targetPos - The slider's target's position
         * @param direction - The direction the slider wants to move in
         * @return - True if the slider changes direction to move up or down
         */
        private boolean setPathUpOrDown(AABB currentPath, BlockPos currentPos, BlockPos targetPos, BlockPos difference, Direction direction) {
            if (direction.getAxis() == Direction.Axis.Y) {
                return true;
            }

            AABB collisionBox = this.calculateAdjacentBox(this.slider.getBoundingBox(), direction);
            boolean isTouchingWall = false;

            for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(collisionBox.minX), Mth.floor(collisionBox.minY), Mth.floor(collisionBox.minZ), Mth.ceil(collisionBox.maxX - 1), Mth.ceil(collisionBox.maxY - 1), Mth.ceil(collisionBox.maxZ - 1))) {
                if (this.slider.level.getBlockState(pos).is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                    isTouchingWall = true;
                    break;
                }
            }

            if (isTouchingWall) {
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
                int y = Mth.floor(collisionBox.minY);
                while (isTouchingWall) {
                    y++;
                    isTouchingWall = false;
                    for (int x = Mth.floor(collisionBox.minX); x < collisionBox.maxX; x++) {
                        for (int z = Mth.floor(collisionBox.minZ); z < collisionBox.maxZ; z++) {
                            if (this.slider.level.getBlockState(pos.set(x, y, z)).is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                                isTouchingWall = true;
                            }
                        }
                    }
                }
                this.targetPoint = currentPos.atY(y);
                this.moveDir = Direction.UP;
                return true;
            } else if (this.slider.getY() > targetPos.getY()) { // Bring the slider back to the ground before attacking again.
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
                currentPath = this.calculateAdjacentBox(currentPath, Direction.DOWN);
                currentPath = currentPath.expandTowards(difference.getX(), difference.getY(), difference.getZ());
                // If there's a block in the way, don't take the low road.
                for (int x = Mth.floor(currentPath.minX); x < currentPath.maxX; x++) {
                    for (int z = Mth.floor(currentPath.minZ); z < currentPath.maxZ; z++) {
                        BlockState state = this.slider.level.getBlockState(pos.set(x, targetPos.getY(), z));
                        if (!state.isAir()) {
                            isTouchingWall = true;
                            break;
                        }
                    }
                }
                if (!isTouchingWall) {
                    this.targetPoint = currentPos.atY(targetPos.getY());
                    this.moveDir = Direction.DOWN;
                    return true;
                }
            }
            return false;
        }

        /**
         * Creates an AABB expanded to the point the slider wants to go to.
         */
        public AABB calculatePathBox(AABB box, BlockPos length, Direction direction) {
            return box.expandTowards((length.getX() - box.getXsize()) * direction.getStepX(), (length.getY() - box.getYsize()) * direction.getStepY(), (length.getZ() - box.getZsize()) * direction.getStepZ());
        }

        /**
         * Calculates a box adjacent to the original, with equal dimensions except for the axis it's translated along.
         */
        public AABB calculateAdjacentBox(AABB box, Direction direction) {
            double minX = box.minX;
            double minY = box.minY;
            double minZ = box.minZ;
            double maxX = box.maxX;
            double maxY = box.maxY;
            double maxZ = box.maxZ;
            if (direction == Direction.UP) {
                minY = maxY;
                maxY += 1;
            } else if (direction == Direction.DOWN) {
                maxY = minY;
                minY -= 1;
            } else if (direction == Direction.NORTH) {
                maxZ = minZ;
                minZ -= 1;
            } else if (direction == Direction.SOUTH) {
                minZ = maxZ;
                maxZ += 1;
            } else if (direction == Direction.EAST) {
                minX = maxX;
                maxX += 1;
            } else { // West
                maxX = minX;
                minX -= 1;
            }

            return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
        }

        public Direction calculateDirection(double x, double y, double z) {
            double absX = Math.abs(x);
            double absY = Math.abs(y);
            double absZ = Math.abs(z);
            if (absY > absX && absY > absZ) {
                return y > 0 ? Direction.UP : Direction.DOWN;
            } else if (absX > absZ) {
                return x > 0 ? Direction.EAST : Direction.WEST;
            } else {
                return z > 0 ? Direction.SOUTH : Direction.NORTH;
            }
        }

        public double axisDistance(double x, double y, double z, Direction direction) {
            return x * direction.getStepX() + y * direction.getStepY() + z * direction.getStepZ();
        }

        /**
         * Crushes any blocks in the slider's way.
         * @return True if there is a collision with a block.
         */
        protected boolean crush() {
            boolean collided = false;
            boolean crushed = false;
            if ((this.slider.horizontalCollision || this.slider.verticalCollision) && ForgeEventFactory.getMobGriefingEvent(this.slider.level, this.slider)) {
                AABB crushBox = this.slider.getBoundingBox().inflate(0.2);
                for(BlockPos pos : BlockPos.betweenClosed(Mth.floor(crushBox.minX), Mth.floor(crushBox.minY), Mth.floor(crushBox.minZ), Mth.floor(crushBox.maxX), Mth.floor(crushBox.maxY), Mth.floor(crushBox.maxZ))) {
                    BlockState blockState = this.slider.level.getBlockState(pos);
                    if (!blockState.isAir()) {
                        collided = true;
                        if (!blockState.is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                            crushed = this.slider.level.destroyBlock(pos, true, this.slider) || crushed;
                            this.slider.blockDestroySmoke(pos);
                        }
                    }
                }
            }
            if (crushed) {
                this.slider.level.playSound(null, this.slider.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 3.0F, (0.625F + (this.slider.random.nextFloat() - this.slider.random.nextFloat()) * 0.2F) * 0.7F);
                this.slider.playSound(this.slider.getCollideSound(), 2.5F, 1.0F / (this.slider.random.nextFloat() * 0.2F + 0.9F));
            }
            return collided;
        }

        @Override
        public void stop() {
            this.velocity = 0;
            this.moveDelay = this.calculateMoveDelay();
            this.slider.setDeltaMovement(Vec3.ZERO);
            this.targetPoint = null;
        }

        protected float getVelocityIncrease() {
            return this.slider.isCritical() ? 0.035F : 0.0175F;
        }

        protected float getMaxVelocity() {
            return 2.0F;
        }

        protected int calculateMoveDelay() {
            return this.slider.isCritical() ? 4 : 8;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}