package com.aetherteam.aether.entity.monster.dungeon.boss;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.AetherBossMob;
import com.aetherteam.aether.entity.ai.controller.BlankMoveControl;
import com.aetherteam.aether.entity.ai.goal.MostDamageTargetGoal;
import com.aetherteam.aether.entity.monster.dungeon.boss.goal.*;
import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.network.packet.clientbound.BossInfoPacket;
import com.aetherteam.nitrogen.entity.BossRoomTracker;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Slider extends PathfinderMob implements AetherBossMob<Slider>, Enemy, IEntityWithComplexSpawn {
    private static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Component> DATA_BOSS_NAME_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.COMPONENT);
    private static final EntityDataAccessor<Float> DATA_HURT_ANGLE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_HURT_ANGLE_X_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_HURT_ANGLE_Z_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    private static final Music SLIDER_MUSIC = new Music(AetherSoundEvents.MUSIC_BOSS_SLIDER, 0, 0, true);
    public static final Map<Block, Function<BlockState, BlockState>> DUNGEON_BLOCK_CONVERSIONS = Map.ofEntries(
        Map.entry(AetherBlocks.LOCKED_CARVED_STONE.get(), (blockState) -> AetherBlocks.CARVED_STONE.get().defaultBlockState()),
        Map.entry(AetherBlocks.LOCKED_SENTRY_STONE.get(), (blockState) -> AetherBlocks.SENTRY_STONE.get().defaultBlockState()),
        Map.entry(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get(), (blockState) -> Blocks.AIR.defaultBlockState()),
        Map.entry(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get(), (blockState) -> AetherBlocks.SKYROOT_TRAPDOOR.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, blockState.getValue(HorizontalDirectionalBlock.FACING)))
    );

    /**
     * Goal for targeting in groups of entities
     */
    private MostDamageTargetGoal mostDamageTargetGoal;

    /**
     * Boss health bar manager
     */
    private final ServerBossEvent bossFight;
    @Nullable
    private BossRoomTracker<Slider> bronzeDungeon;

    private int chatCooldown;

    private Direction moveDirection = null;
    private int moveDelay = this.calculateMoveDelay();
    private Vec3 targetPoint = null;
    private int attackCooldown = 0;

    public Slider(EntityType<? extends Slider> type, Level level) {
        super(type, level);
        this.moveControl = new BlankMoveControl(this);
        this.bossFight = (ServerBossEvent) new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setPlayBossMusic(true);
        this.setBossFight(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setRot(0, 0);
        this.setPersistenceRequired();
    }

    /**
     * Generates a name for the boss and adjusts its position.<br><br>
     * Warning for "deprecation" is suppressed because this is fine to override.
     *
     * @param level      The {@link ServerLevelAccessor} where the entity is spawned.
     * @param difficulty The {@link DifficultyInstance} of the game.
     * @param reason     The {@link MobSpawnType} reason.
     * @param spawnData  The {@link SpawnGroupData}.
     * @param tag        The {@link CompoundTag} to apply to this entity.
     * @return The {@link SpawnGroupData} to return.
     */
    @Override
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.setBossName(BossNameGenerator.generateSliderName(this.getRandom()));
        this.moveTo(Mth.floor(this.getX()), this.getY(), Mth.floor(this.getZ())); // Aligns the Slider with the blocks below it.
        return spawnData;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 400.0)
                .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CollideGoal(this));
        this.goalSelector.addGoal(2, new CrushGoal(this));
        this.goalSelector.addGoal(3, new BackOffAfterAttackGoal(this));
        this.goalSelector.addGoal(4, new SetPathUpOrDownGoal(this));
        this.goalSelector.addGoal(5, new AvoidObstaclesGoal(this));
        this.goalSelector.addGoal(6, new SliderMoveGoal(this));

        this.mostDamageTargetGoal = new MostDamageTargetGoal(this);
        this.targetSelector.addGoal(1, this.mostDamageTargetGoal);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_AWAKE_ID, false);
        this.getEntityData().define(DATA_BOSS_NAME_ID, Component.literal("Slider"));
        this.getEntityData().define(DATA_HURT_ANGLE_ID, 0.0F);
        this.getEntityData().define(DATA_HURT_ANGLE_X_ID, 0.0F);
        this.getEntityData().define(DATA_HURT_ANGLE_Z_ID, 0.0F);
    }

    /**
     * Handles stopping target tracking, liquid evaporation, and chat message cooldown.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.isAwake() || (this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }
        this.evaporate();
        if (this.getChatCooldown() > 0) {
            this.chatCooldown--;
        }
    }

    /**
     * Evaporates liquid blocks.
     *
     * @see AetherBossMob#evaporate(Mob, BlockPos, BlockPos, Predicate)
     */
    private void evaporate() {
        Pair<BlockPos, BlockPos> minMax = this.getDefaultBounds(this);
        AetherBossMob.super.evaporate(this, minMax.getLeft(), minMax.getRight(), (blockState) -> true);
    }

    /**
     * Handles boss fight tracking and dungeon tracking<br><br>
     * Warning for "unchecked" is suppressed because the brain is always a Slider brain.
     */
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.trackDungeon();
        if (this.moveDelay > 0) {
            --this.moveDelay;
        }
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
    }

    /**
     * Handles damaging the Slider.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        Optional<LivingEntity> damageResult = this.canDamageSlider(source);
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            super.hurt(source, amount);
            if (!this.level().isClientSide() && source.getEntity() instanceof LivingEntity living) {
                this.mostDamageTargetGoal.addAggro(living, amount); // AI goal for being hurt.
            }
        } else if (damageResult.isPresent()) {
            LivingEntity attacker = damageResult.get();
            if (super.hurt(source, amount) && this.getHealth() > 0) {
                if (!this.isBossFight()) {
                    this.start();
                }
                this.setDeltaMovement(this.getDeltaMovement().scale(0.75F));

                // Handle the Slider's model tilt when damaged.
                double a = Math.abs(this.position().x() - attacker.position().x());
                double c = Math.abs(this.position().z() - attacker.position().z());
                if (a > c) {
                    this.setHurtAngleZ(1);
                    this.setHurtAngleX(0);
                    if (this.position().x() > attacker.position().x()) {
                        this.setHurtAngleZ(-1);
                    }
                } else {
                    this.setHurtAngleX(1);
                    this.setHurtAngleZ(0);
                    if (this.position().z() > attacker.position().z()) {
                        this.setHurtAngleX(-1);
                    }
                }
                this.setHurtAngle(0.7F - (this.getHealth() / 875.0F));

                if (!this.level().isClientSide() && source.getEntity() instanceof LivingEntity living) {
                    this.mostDamageTargetGoal.addAggro(living, amount); // AI goal for being hurt.
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the Slider can be damaged, playing a chat message if the player attempts to damage with the wrong tool or is too far away.
     *
     * @param source The {@link DamageSource}.
     * @return An {@link Optional} that contains the attacking {@link LivingEntity} if the damage checks are successful.
     */
    private Optional<LivingEntity> canDamageSlider(DamageSource source) {
        if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
            if (source.getDirectEntity() instanceof LivingEntity attacker) {
                if (this.getDungeon() == null || this.getDungeon().isPlayerWithinRoomInterior(attacker)) { // Only allow damage within the boss room.
                    if (attacker.getMainHandItem().canPerformAction(ToolActions.PICKAXE_DIG)
                        || attacker.getMainHandItem().is(AetherTags.Items.SLIDER_DAMAGING_ITEMS)
                        || attacker.getMainHandItem().isCorrectToolForDrops(AetherBlocks.CARVED_STONE.get().defaultBlockState())) { // Check for correct tool.
                        return Optional.of(attacker);
                    } else {
                        return this.sendInvalidToolMessage(attacker);
                    }
                } else {
                    this.sendTooFarMessage(attacker);
                }
            } else if (source.getDirectEntity() instanceof Projectile projectile) {
                if (projectile.getOwner() instanceof LivingEntity attacker) {
                    if (this.getDungeon() == null || this.getDungeon().isPlayerWithinRoomInterior(attacker)) { // Only allow damage within the boss room.
                        if (projectile.getType().is(AetherTags.Entities.SLIDER_DAMAGING_PROJECTILES)) {
                            return Optional.of(attacker);
                        } else {
                            return this.sendInvalidToolMessage(attacker);
                        }
                    } else {
                        return this.sendTooFarMessage(attacker);
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Tells the player that they are using an invalid tool to attack the Slider.
     *
     * @param attacker The attacking {@link LivingEntity}.
     * @return An empty {@link Optional}.
     */
    private Optional<LivingEntity> sendInvalidToolMessage(LivingEntity attacker) {
        if (!this.level().isClientSide() && attacker instanceof Player player) {
            if (this.getChatCooldown() <= 0) {
                if (AetherConfig.COMMON.reposition_slider_message.get()) {
                    player.displayClientMessage(Component.translatable("gui.aether.slider.message.attack.invalid"), true); // Invalid tool.
                } else {
                    player.sendSystemMessage(Component.translatable("gui.aether.slider.message.attack.invalid")); // Invalid tool.
                }
                this.setChatCooldown(15);
            }
        }
        return Optional.empty();
    }

    /**
     * Tells the player that they are too far away to attack the Slider.
     *
     * @param attacker The attacking {@link LivingEntity}.
     * @return An empty {@link Optional}.
     */
    private Optional<LivingEntity> sendTooFarMessage(LivingEntity attacker) {
        if (!this.level().isClientSide() && attacker instanceof Player player) {
            if (this.getChatCooldown() <= 0) {
                this.displayTooFarMessage(player); // Too far from Slider
                this.setChatCooldown(15);
            }
        }
        return Optional.empty();
    }

    /**
     * Awakens the boss, starts the boss fight, and closes the boss room.
     */
    private void start() {
        if (this.getAwakenSound() != null) {
            this.playSound(this.getAwakenSound(), 2.5F, 1.0F / (this.getRandom().nextFloat() * 0.2F + 0.9F));
        }
        this.setAwake(true);
        this.setBossFight(true);
        if (this.getDungeon() != null) {
            this.closeRoom();
        }
        AetherEventDispatch.onBossFightStart(this, this.getDungeon());
    }

    /**
     * Resets the boss fight.
     */
    public void reset() {
        this.setDeltaMovement(Vec3.ZERO);
        this.setAwake(false);
        this.setBossFight(false);
        this.setTarget(null);
        this.setHealth(this.getMaxHealth());
        if (this.getDungeon() != null) {
            this.setPos(this.getDungeon().originCoordinates());
            this.openRoom();
        }
        AetherEventDispatch.onBossFightStop(this, this.getDungeon());
    }

    /**
     * Ends the boss fight, opens the room, and grants advancements when the boss dies.
     *
     * @param source The {@link DamageSource}.
     */
    @Override
    public void die(DamageSource source) {
        this.setDeltaMovement(Vec3.ZERO);
        this.explode();
        if (this.level() instanceof ServerLevel) {
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth()); // Forces an update to the boss health meter.
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(source);
                this.tearDownRoom();
            }
        }
        super.die(source);
    }

    /**
     * Explosion particles for the Slider.
     */
    private void explode() {
        for (int i = 0; i < (this.getHealth() <= 0 ? 16 : 48); i++) {
            double x = this.position().x() + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            double y = this.getBoundingBox().minY + 1.75 + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            double z = this.position().z() + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            this.level().addParticle(ParticleTypes.POOF, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    /**
     * Disallows the Slider from receiving knockback.
     *
     * @param strength The {@link Double} for knockback strength.
     * @param x        The {@link Double} for knockback x-direction.
     * @param z        The {@link Double} for knockback z-direction.
     */
    @Override
    public void knockback(double strength, double x, double z) {
    }

    /**
     * Disallows the Slider from being pushed.
     *
     * @param x The {@link Double} for x-motion.
     * @param y The {@link Double} for y-motion.
     * @param z The {@link Double} for z-motion.
     */
    @Override
    public void push(double x, double y, double z) {
    }

    /**
     * Required despite call to {@link Mob#setPersistenceRequired()} in constructor.
     */
    @Override
    public void checkDespawn() {
    }

    /**
     * Called on every block in the boss room when the boss is defeated.
     *
     * @param state The {@link BlockState} to try to convert.
     * @return The converted {@link BlockState}.
     */
    @Nullable
    @Override
    public BlockState convertBlock(BlockState state) {
        return DUNGEON_BLOCK_CONVERSIONS.getOrDefault(state.getBlock(), (blockState) -> null).apply(state);
    }

    /**
     * Tracks the player as a part of the boss fight when the player is nearby, displaying the boss bar for them.
     *
     * @param player The {@link ServerPlayer}.
     */
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        PacketRelay.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId(), this.getId()), player);
        if (this.getDungeon() == null || this.getDungeon().isPlayerTracked(player)) {
            this.bossFight.addPlayer(player);
            AetherEventDispatch.onBossFightPlayerAdd(this, this.getDungeon(), player);
        }
    }

    /**
     * Tracks the player as no longer in the boss fight when the player is nearby, removing the boss bar for them.
     *
     * @param player The {@link ServerPlayer}.
     */
    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        PacketRelay.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId(), this.getId()), player);
        this.bossFight.removePlayer(player);
        AetherEventDispatch.onBossFightPlayerRemove(this, this.getDungeon(), player);
    }

    /**
     * Adds a player to the boss fight when they've entered the dungeon.
     *
     * @param player The {@link Player}.
     */
    @Override
    public void onDungeonPlayerAdded(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.addPlayer(serverPlayer);
            AetherEventDispatch.onBossFightPlayerAdd(this, this.getDungeon(), serverPlayer);
        }
    }

    /**
     * Removes a player from the boss fight when they've left the dungeon.
     *
     * @param player The {@link Player}.
     */
    @Override
    public void onDungeonPlayerRemoved(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.removePlayer(serverPlayer);
            AetherEventDispatch.onBossFightPlayerRemove(this, this.getDungeon(), serverPlayer);
        }
    }

    /**
     * @return Whether the entity is awake, as a {@link Boolean}.
     */
    public boolean isAwake() {
        return this.getEntityData().get(DATA_AWAKE_ID);
    }

    /**
     * Sets whether the entity is awake.
     *
     * @param awake The {@link Boolean} value.
     */
    public void setAwake(boolean awake) {
        this.getEntityData().set(DATA_AWAKE_ID, awake);
    }

    /**
     * @return The {@link Component} for the boss name.
     */
    @Override
    public Component getBossName() {
        return this.getEntityData().get(DATA_BOSS_NAME_ID);
    }

    /**
     * Sets the {@link Component} for the boss name and in the boss fight.
     *
     * @param component The name {@link Component}.
     */
    @Override
    public void setBossName(Component component) {
        this.getEntityData().set(DATA_BOSS_NAME_ID, component);
        this.bossFight.setName(component);
    }

    /**
     * @return The {@link Float} for the x-angle for the Slider to tilt when damaged.
     */
    public float getHurtAngleX() {
        return this.getEntityData().get(DATA_HURT_ANGLE_X_ID);
    }

    /**
     * Sets the x-angle for the Slider to tilt when damaged.
     *
     * @param hurtAngleX The {@link Float} value.
     */
    public void setHurtAngleX(float hurtAngleX) {
        this.getEntityData().set(DATA_HURT_ANGLE_X_ID, hurtAngleX);
    }

    /**
     * @return The {@link Float} for the z-angle for the Slider to tilt when damaged.
     */
    public float getHurtAngleZ() {
        return this.getEntityData().get(DATA_HURT_ANGLE_Z_ID);
    }

    /**
     * Sets the z-angle for the Slider to tilt when damaged.
     *
     * @param hurtAngleZ The {@link Float} value.
     */
    public void setHurtAngleZ(float hurtAngleZ) {
        this.getEntityData().set(DATA_HURT_ANGLE_Z_ID, hurtAngleZ);
    }

    /**
     * @return The {@link Float} for the magnitude of the hurt angle tilt.
     */
    public float getHurtAngle() {
        return this.getEntityData().get(DATA_HURT_ANGLE_ID);
    }

    /**
     * Sets the magnitude of the hurt angle tilt.
     *
     * @param hurtAngle The {@link Float} value.
     */
    public void setHurtAngle(float hurtAngle) {
        this.getEntityData().set(DATA_HURT_ANGLE_ID, hurtAngle);
    }

    /**
     * @return The {@link Slider} {@link BossRoomTracker} for the Bronze Dungeon.
     */
    @Nullable
    @Override
    public BossRoomTracker<Slider> getDungeon() {
        return this.bronzeDungeon;
    }

    /**
     * Sets the tracker for the Bronze Dungeon.
     *
     * @param dungeon The {@link Slider} {@link BossRoomTracker}.
     */
    @Override
    public void setDungeon(@Nullable BossRoomTracker<Slider> dungeon) {
        this.bronzeDungeon = dungeon;
    }

    /**
     * @return Whether the boss fight is active and the boss bar is visible, as a {@link Boolean}.
     */
    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    /**
     * Sets whether the boss fight is active and the boss bar is visible.
     *
     * @param isFighting The {@link Boolean} value.
     */
    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }

    /**
     * @return The {@link ResourceLocation} for this boss's health bar.
     */
    @Nullable
    @Override
    public ResourceLocation getBossBarTexture() {
        return new ResourceLocation(Aether.MODID, "boss_bar/slider");
    }

    /**
     * @return The {@link ResourceLocation} for this boss's health bar background.
     */
    @Nullable
    @Override
    public ResourceLocation getBossBarBackgroundTexture() {
        return new ResourceLocation(Aether.MODID, "boss_bar/slider_background");
    }

    /**
     * @return The {@link Music} for this boss's fight.
     */
    @Nullable
    @Override
    public Music getBossMusic() {
        return SLIDER_MUSIC;
    }

    /**
     * @return The {@link Integer} for the cooldown until another chat message can display.
     */
    public int getChatCooldown() {
        return this.chatCooldown;
    }

    /**
     * Sets the cooldown for when another chat message can display.
     *
     * @param cooldown The {@link Integer} cooldown.
     */
    public void setChatCooldown(int cooldown) {
        this.chatCooldown = cooldown;
    }

    /**
     * @return The death score {@link Integer} for the awarded kill score from this entity.
     */
    @Override
    public int getDeathScore() {
        return this.deathScore;
    }

    @Nullable
    public Direction getMoveDirection() {
        return this.moveDirection;
    }

    public void setMoveDirection(@Nullable Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    public int getMoveDelay() {
        return this.moveDelay;
    }

    public void setMoveDelay(int moveDelay) {
        this.moveDelay = moveDelay;
    }

    @Nullable
    public Vec3 findTargetPoint() {
        Vec3 pos = this.targetPoint;
        if (pos != null) {
            return pos;
        } else {
            LivingEntity target = getTarget();
            return target == null ? null : target.position();
        }
    }

    @Nullable
    public Vec3 getTargetPoint() {
        return this.targetPoint;
    }

    public void setTargetPoint(@Nullable Vec3 targetPoint) {
        this.targetPoint = targetPoint;
    }

    public int attackCooldown() {
        return this.attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    /**
     * @return The {@link Integer} cooldown for when the Slider can move again.
     * Slightly randomized and dependent on whether the Slider is in critical mode.
     */
    public int calculateMoveDelay() {
        return this.isCritical() ? 1 + this.getRandom().nextInt(10) : 2 + this.getRandom().nextInt(14);
    }

    public static Direction calculateDirection(double x, double y, double z) {
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

    /**
     * Calculates a box adjacent to the original, with equal dimensions except for the axis it's translated along.
     *
     * @param box       The {@link AABB} bounding box.
     * @param direction The movement {@link Direction}.
     * @return The adjacent {@link AABB} bounding box.
     */
    public static AABB calculateAdjacentBox(AABB box, Direction direction) {
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

    /**
     * @return The {@link Float} for how much to add to the Slider's velocity.
     * Dependent on whether the Slider is in critical mode.
     */
    public float getVelocityIncrease() {
        return this.isCritical() ? 0.045F - (this.getHealth() / 10000) : 0.035F - (this.getHealth() / 30000);
    }

    /**
     * @return A {@link Boolean} for whether the Slider is in critical mode.
     * The Slider goes critical when its health is at 100.
     */
    public boolean isCritical() {
        return this.getHealth() <= 100;
    }

    /**
     * =
     *
     * @return The {@link Float} for the maximum velocity limit.
     */
    public float getMaxVelocity() {
        return 2.5F;
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.setBossName(name);
    }

    protected SoundEvent getAwakenSound() {
        return AetherSoundEvents.ENTITY_SLIDER_AWAKEN.get();
    }

    public SoundEvent getCollideSound() {
        return AetherSoundEvents.ENTITY_SLIDER_COLLIDE.get();
    }

    public SoundEvent getMoveSound() {
        return AetherSoundEvents.ENTITY_SLIDER_MOVE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_SLIDER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_SLIDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SLIDER_DEATH.get();
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target.canBeSeenAsEnemy();
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being affected by explosions.
     */
    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    @Override
    public float getYRot() {
        return 0;
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    /**
     * @return A {@link Boolean} for whether the Slider can be collided with as if it were a block.
     * It can only be collided with when it is asleep.
     */
    @Override
    public boolean canBeCollidedWith() {
        return !this.isAwake();
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being pushed.
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    /**
     * @return A true {@link Boolean}, preventing the Slider from being affected by gravity.
     */
    @Override
    public boolean isNoGravity() {
        return true;
    }

    /**
     * @return A true {@link Boolean}, preventing the Slider from being affected by friction.
     */
    @Override
    public boolean shouldDiscardFriction() {
        return true;
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being affected by liquids.
     */
    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being on fire.
     */
    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being affected by freezing.
     */
    @Override
    public boolean isFullyFrozen() {
        return false;
    }

    /**
     * Disallows the Slider from making footstep noises.
     *
     * @return The type of {@link net.minecraft.world.entity.Entity.MovementEmission}.
     */
    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#addBossSaveData(CompoundTag)
     */
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.addBossSaveData(tag);
        tag.putBoolean("Awake", this.isAwake());
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#readBossSaveData(CompoundTag)
     */
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readBossSaveData(tag);
        if (tag.contains("Awake")) {
            this.setAwake(tag.getBoolean("Awake"));
        }
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#addBossSaveData(CompoundTag)
     */
    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        this.addBossSaveData(tag);
        buffer.writeNbt(tag);
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#readBossSaveData(CompoundTag)
     */
    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        CompoundTag tag = additionalData.readNbt();
        if (tag != null) {
            this.readBossSaveData(tag);
        }
    }
}
