package com.gildedgames.aether.entity.monster.dungeon.boss;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.api.BossNameGenerator;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.BossMob;
import com.gildedgames.aether.entity.BossRoom;
import com.gildedgames.aether.entity.ai.controller.SliderMovementController;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

public class Slider extends PathfinderMob implements BossMob, Enemy, BossRoom<Slider> {
    public static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.COMPONENT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_X_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_Z_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);

    private DungeonTracker<Slider> bronzeDungeon;
    private final ServerBossEvent bossFight;
    private MostDamageTargetGoal mostDamageTargetGoal;
    private int chatTime;

    private Vec3 targetPosition;
    private boolean canMove;
    private int moveDelay;
    private float velocity;
    private Direction direction = null;
    private Direction lastDirection = null;
    private int unstuckTimer = 0;

    public Slider(EntityType<? extends Slider> entityType, Level level) {
        super(entityType, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setVisible(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setRot(0, 0);
        this.moveControl = new SliderMovementController(this);
        this.setPersistenceRequired();
    }

    /**
     * Generates a name for the boss.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setBossName(BossNameGenerator.generateSliderName());
        return data;
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
        this.evaporate();
        this.trackDungeon();
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (!this.level.isClientSide && source.getEntity() instanceof LivingEntity livingEntity && level.getDifficulty() != Difficulty.PEACEFUL) {
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
                }
            } else {
                if (livingEntity instanceof Player player) {
                    if (this.chatTime-- <= 0) {
                        player.sendSystemMessage(Component.translatable("gui.aether.slider.message.attack.invalid"));
                        this.chatTime = 15;
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void doPush(@Nonnull Entity entity) {
        if (this.isAwake()) {
            boolean attack = entity.hurt(new EntityDamageSource("crush", this), 6);
            if (attack && entity instanceof LivingEntity livingEntity) {
                if (livingEntity instanceof Player player && player.getUseItem().is(Items.SHIELD) && player.isBlocking()) {
                    player.getCooldowns().addCooldown(Items.SHIELD, 100);
                    this.level.broadcastEntityEvent(player, (byte) 30);
                }
                livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.20, 0.0));
                this.playSound(this.getCollideSound(), 2.5F, 1.0F / (this.random.nextFloat() * 0.2F + 0.9F));
                this.stop();
            }
        }
        super.doPush(entity);
    }

    @Override
    public void die(@Nonnull DamageSource damageSource) {
        this.explode();
        if (this.getDungeon() != null) {
            this.getDungeon().grantAdvancements(damageSource);
        }
        if (this.level instanceof ServerLevel) {
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        }
        //todo open door and open treasure chest compartment
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
        }
        //todo close door
    }

    private void trackDungeon() {
        if (!this.level.isClientSide() && this.getDungeon() != null) {
            this.getDungeon().trackPlayers();
            if (this.isAwake() && this.getDungeon().dungeonPlayers().isEmpty()) {
                this.reset();
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

    private void reset() {
        this.lastDirection = null;
        this.unstuckTimer = 0;
        this.stop();
        this.setAwake(false);
        this.setBossFight(false);
        this.setTarget(null);
        this.setHealth(this.getMaxHealth());
        if (this.getDungeon() != null) {
            this.setPos(this.getDungeon().originCoordinates());
        }
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
        this.bossFight.addPlayer(player);
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

    protected SoundEvent getCollideSound() {
        return AetherSoundEvents.ENTITY_SLIDER_COLLIDE.get();
    }

    protected SoundEvent getMoveSound() {
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
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    public float getYRot() {
        return 0;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
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

    static class SliderMoveGoal extends Goal {
        private final Slider slider;

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
            if (this.slider.getDungeon() != null) {
                if (!this.slider.getDungeon().isBossWithinRoom()) {
                    this.slider.targetPosition = this.slider.getDungeon().originCoordinates();
                } else {
                    if (this.slider.targetPosition == null || !this.slider.targetPosition.equals(this.slider.getDungeon().originCoordinates()) || this.slider.getDungeon().isBossWithinOrigin()) {
                        if (this.slider.getTarget() != null) {
                            this.slider.targetPosition = new Vec3(this.slider.getTarget().position().x, this.slider.getTarget().getBoundingBox().minY, this.slider.getTarget().position().z);
                        }
                    }
                }
            } else {
                if (this.slider.getTarget() != null) {
                    this.slider.targetPosition = new Vec3(this.slider.getTarget().position().x, this.slider.getTarget().getBoundingBox().minY, this.slider.getTarget().position().z);
                }
            }
            if (this.slider.getTarget() != null) {
                if (this.slider.canMove) {
                    boolean crushed = this.crushedBlocks();
                    if (!crushed) {
                        --this.slider.unstuckTimer;
                        if (this.slider.velocity < 2.0) {
                            this.slider.velocity += this.slider.isCritical() ? 0.07F : 0.035F;
                        }
                        this.slider.setDeltaMovement(Vec3.ZERO);
                        if (this.slider.direction == Direction.UP) {
                            this.slider.setDeltaMovement(0.0, this.slider.velocity, 0.0);
                            if (this.slider.getBoundingBox().minY > this.slider.targetPosition.y() + 0.35 && this.slider.unstuckTimer <= 0) {
                                this.slider.stop();
                                this.slider.moveDelay = this.slider.isCritical() ? 4 : 8;
                            }
                        } else if (this.slider.direction == Direction.DOWN) {
                            this.slider.setDeltaMovement(0.0, -this.slider.velocity, 0.0);
                            if (this.slider.getBoundingBox().minY < this.slider.targetPosition.y() - 0.25 && this.slider.unstuckTimer <= 0) {
                                this.slider.stop();
                                this.slider.moveDelay = this.slider.isCritical() ? 4 : 8;
                            }
                        } else if (this.slider.direction == Direction.EAST) {
                            this.slider.setDeltaMovement(this.slider.velocity, 0.0, 0.0);
                            if (this.slider.position().x() > this.slider.targetPosition.x() + 0.125 && this.slider.unstuckTimer <= 0) {
                                this.slider.stop();
                                this.slider.moveDelay = this.slider.isCritical() ? 4 : 8;
                            }
                        } else if (this.slider.direction == Direction.WEST) {
                            this.slider.setDeltaMovement(-this.slider.velocity, 0.0, 0.0);
                            if (this.slider.position().x() < this.slider.targetPosition.x() - 0.125 && this.slider.unstuckTimer <= 0) {
                                this.slider.stop();
                                this.slider.moveDelay = this.slider.isCritical() ? 4 : 8;
                            }
                        } else if (this.slider.direction == Direction.SOUTH) {
                            this.slider.setDeltaMovement(0.0, 0.0, this.slider.velocity);
                            if (this.slider.position().z() > this.slider.targetPosition.z() + 0.125 && this.slider.unstuckTimer <= 0) {
                                this.slider.stop();
                                this.slider.moveDelay = this.slider.isCritical() ? 4 : 8;
                            }
                        } else if (this.slider.direction == Direction.NORTH) {
                            this.slider.setDeltaMovement(0.0, 0.0, -this.slider.velocity);
                            if (this.slider.position().z() < this.slider.targetPosition.z() - 0.125 && this.slider.unstuckTimer <= 0) {
                                this.slider.stop();
                                this.slider.moveDelay = this.slider.isCritical() ? 4 : 8;
                            }
                        }
                        this.slider.lastDirection = null;
                    }
                } else {
                    if (this.slider.moveDelay > 0) {
                        --this.slider.moveDelay;
                        if (this.slider.isCritical() && this.slider.random.nextInt(2) == 0) {
                            --this.slider.moveDelay;
                        }
                        this.slider.setDeltaMovement(Vec3.ZERO);
                    } else {
                        double xDiff = Math.abs(this.slider.position().x() - this.slider.targetPosition.x());
                        double yDiff = Math.abs(this.slider.getBoundingBox().minY - this.slider.targetPosition.y());
                        double zDiff = Math.abs(this.slider.position().z() - this.slider.targetPosition.z());

                        if (xDiff > zDiff) {
                            this.slider.direction = Direction.EAST;
                            if (this.slider.position().x() > this.slider.targetPosition.x()) {
                                this.slider.direction = Direction.WEST;
                            }
                        } else {
                            this.slider.direction = Direction.SOUTH;
                            if (this.slider.position().z() > this.slider.targetPosition.z()) {
                                this.slider.direction = Direction.NORTH;
                            }
                        }

                        if (yDiff > xDiff && yDiff > zDiff || yDiff > 0.25D && this.slider.random.nextInt(5) == 0) {
                            this.slider.direction = Direction.UP;
                            if (this.slider.position().y() > this.slider.targetPosition.y()) {
                                this.slider.direction = Direction.DOWN;
                            }
                        }

                        if (this.slider.unstuckTimer > 0) {
                            if (this.slider.direction != null && this.slider.lastDirection != null && this.slider.direction == this.slider.lastDirection) {
                                if (this.slider.direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
                                    this.slider.direction = Direction.UP;
                                } else {
                                    this.slider.direction = Direction.getRandom(this.slider.random);
                                }
                            }
                        }

                        this.slider.playSound(this.slider.getMoveSound(), 2.5F, 1.0F / (this.slider.getRandom().nextFloat() * 0.2F + 0.9F));
                        this.slider.canMove = true;
                    }
                }
            } else {
                this.slider.stop();
            }
        }

        private boolean crushedBlocks() {
            AABB entity = this.slider.getBoundingBox();
            BlockPos minInside = new BlockPos(entity.minX, entity.minY, entity.minZ);
            BlockPos maxInside = new BlockPos(Math.ceil(entity.maxX - 1), Math.ceil(entity.maxY - 1), Math.ceil(entity.maxZ - 1));
            if (this.crush(minInside, maxInside, true)) {
                return true;
            }
            if (this.slider.direction == Direction.UP) {
                BlockPos min = new BlockPos(entity.minX, entity.maxY, entity.minZ);
                BlockPos max = new BlockPos(Math.ceil(entity.maxX - 1), entity.maxY, Math.ceil(entity.maxZ - 1));
                return this.crush(min, max, false);
            } else if (this.slider.direction == Direction.DOWN) {
                BlockPos min = new BlockPos(entity.minX, entity.minY - 1, entity.minZ);
                BlockPos max = new BlockPos(Math.ceil(entity.maxX - 1), entity.minY - 1, Math.ceil(entity.maxZ - 1));
                return this.crush(min, max, false);
            } else if (this.slider.direction == Direction.EAST) {
                BlockPos min = new BlockPos(entity.maxX, entity.minY, entity.minZ);
                BlockPos max = new BlockPos(entity.maxX, Math.ceil(entity.maxY - 1), Math.ceil(entity.maxZ - 1));
                return this.crush(min, max, false);
            } else if (this.slider.direction == Direction.WEST) {
                BlockPos min = new BlockPos(entity.minX - 1, entity.minY, entity.minZ);
                BlockPos max = new BlockPos(entity.minX - 1, Math.ceil(entity.maxY - 1), Math.ceil(entity.maxZ - 1));
                return this.crush(min, max, false);
            } else if (this.slider.direction == Direction.SOUTH) {
                BlockPos min = new BlockPos(entity.minX, entity.minY, entity.maxZ);
                BlockPos max = new BlockPos(Math.ceil(entity.maxX - 1), Math.ceil(entity.maxY - 1), entity.maxZ);
                return this.crush(min, max, false);
            } else if (this.slider.direction == Direction.NORTH) {
                BlockPos min = new BlockPos(entity.minX, entity.minY, entity.minZ - 1);
                BlockPos max = new BlockPos(Math.ceil(entity.maxX - 1), Math.ceil(entity.maxY - 1), entity.minZ - 1);
                return this.crush(min, max, false);
            }
            return false;
        }

        private boolean crush(BlockPos min, BlockPos max, boolean isInside) {
            boolean flag = false;
            if (this.slider.getDeltaMovement().equals(Vec3.ZERO) || isInside) {
                for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
                    BlockState blockState = this.slider.level.getBlockState(pos);
                    if (!blockState.isAir() && !blockState.is(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS)) {
                        if (ForgeEventFactory.getMobGriefingEvent(this.slider.level, this.slider) && (this.slider.getDungeon() == null || this.slider.getDungeon().roomBounds().contains(Vec3.atCenterOf(pos)))) {
                            this.slider.level.destroyBlock(pos, true, this.slider);
                            this.slider.blockDestroySmoke(pos);
                            this.slider.level.playSound(null, this.slider.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 3.0F, (0.625F + (this.slider.random.nextFloat() - this.slider.random.nextFloat()) * 0.2F) * 0.7F);
                            this.slider.playSound(this.slider.getCollideSound(), 2.5F, 1.0F / (this.slider.random.nextFloat() * 0.2F + 0.9F));
                            this.slider.stop();
                            flag = true;
                        }
                    } else if (blockState.is(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS)) {
                        this.slider.unstuckTimer = 8;
                        this.slider.lastDirection = this.slider.direction;
                        this.slider.stop();
                        flag = true;
                    }
                }
            }
            return flag;
        }
    }
}