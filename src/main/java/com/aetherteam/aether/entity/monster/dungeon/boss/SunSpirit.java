package com.aetherteam.aether.entity.monster.dungeon.boss;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.data.resources.registries.AetherDamageTypes;
import com.aetherteam.aether.entity.AetherBossMob;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.ai.controller.BlankMoveControl;
import com.aetherteam.aether.entity.monster.dungeon.FireMinion;
import com.aetherteam.aether.entity.projectile.crystal.AbstractCrystal;
import com.aetherteam.aether.entity.projectile.crystal.FireCrystal;
import com.aetherteam.aether.entity.projectile.crystal.IceCrystal;
import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.mixin.mixins.common.accessor.LookAtPlayerGoalAccessor;
import com.aetherteam.aether.network.packet.clientbound.BossInfoPacket;
import com.aetherteam.nitrogen.entity.BossRoomTracker;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class SunSpirit extends PathfinderMob implements AetherBossMob<SunSpirit>, Enemy, IEntityWithComplexSpawn {
    private static final double DEFAULT_SPEED_MODIFIER = 1.0;
    private static final double FROZEN_SPEED_MODIFIER = 0.3;
    private static final float INCINERATION_DAMAGE = 10.0F;
    private static final int INCINERATION_FIRE_DURATION = 8;
    private static final int SUN_SPIRIT_FROZEN_DURATION = 175;
    private static final int ICE_CRYSTAL_SHOOT_COUNT_INTERVAL = 5;
    private static final int SHOOT_CRYSTAL_INTERVAL = 50;
    private static final int SPAWN_FIRE_INTERVAL = 35;

    private static final EntityDataAccessor<Boolean> DATA_IS_FROZEN = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_FROZEN_DURATION = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Component> DATA_BOSS_NAME = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.COMPONENT);
    private static final Music SUN_SPIRIT_MUSIC = new Music(AetherSoundEvents.MUSIC_BOSS_SUN_SPIRIT, 0, 0, true);
    public static final Map<Block, Function<BlockState, BlockState>> DUNGEON_BLOCK_CONVERSIONS = Map.ofEntries(
        Map.entry(AetherBlocks.LOCKED_HELLFIRE_STONE.get(), (blockState) -> AetherBlocks.HELLFIRE_STONE.get().defaultBlockState()),
        Map.entry(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get(), (blockState) -> AetherBlocks.LIGHT_HELLFIRE_STONE.get().defaultBlockState()),
        Map.entry(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE.get(), (blockState) -> Blocks.AIR.defaultBlockState()),
        Map.entry(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE.get(), (blockState) -> Blocks.AIR.defaultBlockState())
    );

    /**
     * Boss health bar manager
     */
    private final ServerBossEvent bossFight;
    @Nullable
    private BossRoomTracker<SunSpirit> dungeon;

    private Vec3 origin;
    private int xMax = 9;
    private int zMax = 9;
    private int chatLine;
    private int chatCooldown;

    protected double speedModifier;

    public SunSpirit(EntityType<? extends SunSpirit> type, Level level) {
        super(type, level);
        this.moveControl = new BlankMoveControl(this);
        this.bossFight = (ServerBossEvent) new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setPlayBossMusic(true);
        this.setBossFight(false);
        this.origin = this.position();
        this.xpReward = XP_REWARD_BOSS;
        this.noPhysics = true;
        this.speedModifier = DEFAULT_SPEED_MODIFIER;
        this.setPersistenceRequired();
    }

    /**
     * Generates a name for the boss and tracks the origin where the boss spawned.<br><br>
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
        this.setBossName(BossNameGenerator.generateSunSpiritName(this.getRandom()));
        this.origin = this.position();
        return spawnData;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new DoNothingGoal(this));
        this.goalSelector.addGoal(1, new SunSpiritLookGoal(this, Player.class, 40, 1));
        this.goalSelector.addGoal(2, new ShootFireballGoal(this));
        this.goalSelector.addGoal(3, new SummonFireGoal(this));
        this.goalSelector.addGoal(4, new FlyAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 500.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_FROZEN, false);
        builder.define(DATA_FROZEN_DURATION, 0);
        builder.define(DATA_BOSS_NAME, Component.literal("Sun Spirit"));
    }

    /**
     * Handles evaporating liquids, chat message cooldown, burning entities below the Sun Spirit, and adjusting the Sun Spirit's rotation.
     */
    @Override
    public void tick() {
        super.tick();
        this.evaporate();
        if (this.getChatCooldown() > 0) {
            this.chatCooldown--;
        }
        if (this.getHealth() > 0 && !this.isFrozen()) {
            double x = this.getX() + (this.getRandom().nextFloat() - 0.5F) * this.getRandom().nextFloat();
            double y = this.getBoundingBox().minY + this.getRandom().nextFloat() - 0.5;
            double z = this.getZ() + (this.getRandom().nextFloat() - 0.5F) * this.getRandom().nextFloat();
            this.level().addParticle(ParticleTypes.FLAME, x, y, z, 0, -0.075, 0);
            this.burnEntities();
        }
        this.setYRot(Mth.rotateIfNecessary(this.getYRot(), this.getYHeadRot(), 20));
        this.speedModifier = (this.isFrozen() ? FROZEN_SPEED_MODIFIER : DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Evaporates liquid blocks.
     *
     * @see AetherBossMob#evaporate(Mob, BlockPos, BlockPos, Predicate)
     */
    private void evaporate() {
        AABB boundingBox = this.getBoundingBox();
        BlockPos min = BlockPos.containing(boundingBox.minX - this.xMax, boundingBox.minY - 3, boundingBox.minZ - this.zMax);
        BlockPos max = BlockPos.containing(Math.ceil(boundingBox.maxX - 1) + this.xMax, Math.ceil(boundingBox.maxY - 1) + 4, Math.ceil(boundingBox.maxZ - 1) + this.zMax);
        AetherBossMob.super.evaporate(this, min, max, (blockState) -> true);
    }

    /**
     * Burns all entities directly under the Sun Spirit.
     */
    public void burnEntities() {
        List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0, -2, 0).contract(-0.75, 0, -0.75).contract(0.75, 0, 0.75));
        for (Entity target : entities) {
            if (target instanceof LivingEntity) {
                target.hurt(AetherDamageTypes.entityDamageSource(this.level(), AetherDamageTypes.INCINERATION, this), INCINERATION_DAMAGE);
                target.setSecondsOnFire(INCINERATION_FIRE_DURATION);
            }
        }
    }

    /**
     * Handles boss fight and health tracking, dungeon tracking, checking for Ice Crystal collision, and checking to set the Sun Spirit as frozen.
     */
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.trackDungeon();
        this.checkIceCrystals();
        if (this.getFrozenDuration() > 0) {
            this.setFrozenDuration(this.getFrozenDuration() - 1);
        } else {
            this.setFrozen(false);
        }
    }

    /**
     * Extra checks for seeing if an Ice Crystal is close enough to the Sun Spirit to damage it.
     */
    private void checkIceCrystals() {
        for (IceCrystal iceCrystal : this.level().getEntitiesOfClass(IceCrystal.class, this.getBoundingBox().inflate(0.1))) {
            iceCrystal.doDamage(this);
        }
    }

    /**
     * Plays the Sun Spirit's intro chat dialogue.
     *
     * @param player The interacting {@link Player}.
     * @param hand   The {@link InteractionHand}.
     * @return The {@link InteractionResult}.
     */
    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide() && !this.isBossFight()) {
            if (this.getChatCooldown() <= 0) {
                this.setChatCooldown(14);
                if (this.getDungeon() == null || this.getDungeon().isPlayerWithinRoomInterior(player)) {
                    if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
                        if (!AetherConfig.COMMON.repeat_sun_spirit_dialogue.get()) {
                            if (player.getData(AetherDataAttachments.AETHER_PLAYER).hasSeenSunSpiritDialogue() && this.chatLine == 0) {
                                this.chatLine = 10;
                            }
                        }
                        if (this.chatLine < 9) {
                            this.playSound(this.getInteractSound(), 1.0F, this.getVoicePitch());
                        }
                        switch (this.chatLine++) {
                            case 0 ->
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line0").withStyle(ChatFormatting.RED));
                            case 1 ->
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line1").withStyle(ChatFormatting.RED));
                            case 2 ->
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line2").withStyle(ChatFormatting.RED));
                            case 3 ->
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line3").withStyle(ChatFormatting.RED));
                            case 4 ->
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line4").withStyle(ChatFormatting.RED));
                            case 5 -> {
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line5.1").withStyle(ChatFormatting.RED));
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line5.2").withStyle(ChatFormatting.RED));
                            }
                            case 6 -> {
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line6.1").withStyle(ChatFormatting.RED));
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line6.2").withStyle(ChatFormatting.RED));
                            }
                            case 7 -> {
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line7.1").withStyle(ChatFormatting.RED));
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line7.2").withStyle(ChatFormatting.RED));
                            }
                            case 8 ->
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line8").withStyle(ChatFormatting.RED));
                            case 9 -> {
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line9").withStyle(ChatFormatting.GOLD));
                                this.setHealth(this.getMaxHealth());
                                this.setBossFight(true);
                                if (this.getDungeon() != null) {
                                    this.closeRoom();
                                }
                                this.playSound(this.getActivateSound(), 1.0F, this.getVoicePitch());
                                AetherEventDispatch.onBossFightStart(this, this.getDungeon());
                                player.getData(AetherDataAttachments.AETHER_PLAYER).setSeenSunSpiritDialogue(true);
                            }
                            default -> {
                                this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line10").withStyle(ChatFormatting.RED));
                                this.chatLine = 9;
                            }
                        }
                    } else {
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line1").withStyle(ChatFormatting.RED));
                    }
                } else {
                    this.displayTooFarMessage(player);
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    /**
     * Sends a message to nearby players. Useful for boss fights.
     *
     * @param message The message {@link Component}.
     */
    protected void chatWithNearby(Component message) {
        AABB room = this.getDungeon() == null ? this.getBoundingBox().inflate(16) : this.getDungeon().roomBounds();
        this.level().getNearbyPlayers(NON_COMBAT, this, room).forEach(player -> player.sendSystemMessage(message));
    }

    /**
     * Spawns a Fire Minion when the Sun Spirit is damaged and increases velocity.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (!this.level().isClientSide() && flag && this.getHealth() > 0 && source.getEntity() instanceof LivingEntity entity && source.getDirectEntity() instanceof IceCrystal) {
            this.setFrozen(true);
            this.setFrozenDuration(SUN_SPIRIT_FROZEN_DURATION);
            FireMinion minion = new FireMinion(AetherEntityTypes.FIRE_MINION.get(), this.level());
            minion.setPos(this.position());
            minion.setTarget(entity);
            this.level().addFreshEntity(minion);
        }
        return flag;
    }

    /**
     * Resets the boss fight.
     */
    @Override
    public void reset() {
        this.setBossFight(false);
        this.setTarget(null);
        if (this.dungeon != null) {
            this.openRoom();
        }
        AetherEventDispatch.onBossFightStop(this, this.getDungeon());
    }

    /**
     * Plays the Sun Spirit's defeat message, ends the boss fight, opens the room, grants advancements when the boss dies, and ends eternal day.
     *
     * @param source The {@link DamageSource}.
     */
    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide()) {
            this.setFrozen(true);
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth()); // Forces an update to the boss health meter.
            this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.dead").withStyle(ChatFormatting.AQUA));
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(source);
                this.tearDownRoom();
            }
            if (this.level().hasData(AetherDataAttachments.AETHER_TIME)) {
                var data = this.level().getData(AetherDataAttachments.AETHER_TIME);
                data.setEternalDay(false);
                data.updateEternalDay(this.level());
            }
        }
        super.die(source);
    }

    /**
     * Disallows the Sun Spirit from receiving knockback.
     *
     * @param strength The {@link Double} for knockback strength.
     * @param x        The {@link Double} for knockback x-direction.
     * @param z        The {@link Double} for knockback z-direction.
     */
    @Override
    public void knockback(double strength, double x, double z) {
    }

    /**
     * Disallows the Sun Spirit from being pushed.
     *
     * @param x The {@link Double} for x-motion.
     * @param y The {@link Double} for y-motion.
     * @param z The {@link Double} for z-motion.
     */
    @Override
    public void push(double x, double y, double z) {
    }

    /**
     * [CODE COPY] - {@link LivingEntity#canBeAffected(MobEffectInstance)}.<br><br>
     * The Sun Spirit is immune to all effects unless the event hook determines otherwise.
     */
    @Override //code copy
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, pEffectInstance);
        NeoForge.EVENT_BUS.post(event);
        if (event.getResult() != Event.Result.DEFAULT) {
            return event.getResult() == Event.Result.ALLOW;
        }
        return false;
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
        PacketDistributor.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId(), this.getId()), player);
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
        PacketDistributor.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId(), this.getId()), player);
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
            if (!serverPlayer.isAlive()) {
                serverPlayer.sendSystemMessage(Component.translatable("gui.aether.sun_spirit.playerdeath").withStyle(ChatFormatting.RED));
            }
            AetherEventDispatch.onBossFightPlayerRemove(this, this.getDungeon(), serverPlayer);
        }
    }

    /**
     * @return Whether the Sun Spirit should display as frozen, as a {@link Boolean}.
     */
    public boolean isFrozen() {
        return this.getEntityData().get(DATA_IS_FROZEN);
    }

    /**
     * Sets whether the Sun Spirit should display as frozen.
     *
     * @param frozen The {@link Boolean} value.
     */
    public void setFrozen(boolean frozen) {
        this.getEntityData().set(DATA_IS_FROZEN, frozen);
    }

    /**
     * @return The remaining duration for how long the Sun Spirit is frozen, as an {@link Integer}.
     */
    public int getFrozenDuration() {
        return this.getEntityData().get(DATA_FROZEN_DURATION);
    }

    /**
     * Sets the remaining duration for how long the Sun Spirit should be frozen.
     *
     * @param duration The {@link Integer} duration.
     */
    public void setFrozenDuration(int duration) {
        this.getEntityData().set(DATA_FROZEN_DURATION, duration);
    }

    /**
     * @return The {@link Component} for the boss name.
     */
    @Override
    public Component getBossName() {
        return this.getEntityData().get(DATA_BOSS_NAME);
    }

    /**
     * Sets the {@link Component} for the boss name and in the boss fight.
     *
     * @param component The name {@link Component}.
     */
    @Override
    public void setBossName(Component component) {
        this.getEntityData().set(DATA_BOSS_NAME, component);
        this.bossFight.setName(component);
    }

    /**
     * @return The {@link SunSpirit} {@link BossRoomTracker} for the Gold Dungeon.
     */
    @Nullable
    @Override
    public BossRoomTracker<SunSpirit> getDungeon() {
        return this.dungeon;
    }

    /**
     * Sets the tracker for the Gold Dungeon and also tracks the boss origin point and maximum movement distances.
     *
     * @param dungeon The {@link SunSpirit} {@link BossRoomTracker}.
     */
    @Override
    public void setDungeon(@Nullable BossRoomTracker<SunSpirit> dungeon) {
        this.dungeon = dungeon;
        if (dungeon != null) {
            this.origin = dungeon.originCoordinates();
            this.xMax = this.zMax = Mth.floor(dungeon.roomBounds().getXsize() / 2 - 5);
        } else {
            this.origin = this.position();
            this.xMax = 9;
            this.zMax = 9;
        }
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
        return ResourceLocation.fromNamespaceAndPath(Aether.MODID, "boss_bar/sun_spirit");
    }

    /**
     * @return The {@link ResourceLocation} for this boss's health bar background.
     */
    @Nullable
    @Override
    public ResourceLocation getBossBarBackgroundTexture() {
        return ResourceLocation.fromNamespaceAndPath(Aether.MODID, "boss_bar/sun_spirit_background");
    }

    /**
     * @return The {@link Music} for this boss's fight.
     */
    @Nullable
    @Override
    public Music getBossMusic() {
        return SUN_SPIRIT_MUSIC;
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

    /**
     * Makes the Sun Spirit immune to all damage except cold damage from Ice Crystals, as determined by {@link AetherTags.DamageTypes#IS_COLD}.
     *
     * @param source The {@link DamageSource}.
     * @return Whether the Sun Spirit is invulnerable to the damage, as a {@link Boolean}.
     */
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (this.isRemoved()) {
            return true;
        } else {
            if (this.isFrozen()) {
                return !(source.getEntity() instanceof LivingEntity) || source.getEntity() instanceof SunSpirit;
            } else {
                return !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !source.is(AetherTags.DamageTypes.IS_COLD);
            }
        }
    }

    protected SoundEvent getInteractSound() {
        return AetherSoundEvents.ENTITY_SUN_SPIRIT_INTERACT.get();
    }

    protected SoundEvent getActivateSound() {
        return AetherSoundEvents.ENTITY_SUN_SPIRIT_ACTIVATE.get();
    }

    protected SoundEvent getShootFireSound() {
        return AetherSoundEvents.ENTITY_SUN_SPIRIT_SHOOT_FIRE.get();
    }

    protected SoundEvent getShootIceSound() {
        return AetherSoundEvents.ENTITY_SUN_SPIRIT_SHOOT_ICE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AetherSoundEvents.ENTITY_SUN_SPIRIT_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SUN_SPIRIT_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return 3.0F;
    }

    /**
     * @return A false {@link Boolean}, preventing the Sun Spirit from being affected by explosions.
     */
    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    /**
     * @return A true {@link Boolean}, preventing the Sun Spirit from being affected by gravity.
     */
    @Override
    public boolean isNoGravity() {
        return true;
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#addBossSaveData(CompoundTag)
     */
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.addBossSaveData(tag);
        tag.putInt("ChatLine", this.chatLine);
        tag.putDouble("OffsetX", this.origin.x() - this.getX());
        tag.putDouble("OffsetY", this.origin.y() - this.getY());
        tag.putDouble("OffsetZ", this.origin.z() - this.getZ());
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#readBossSaveData(CompoundTag)
     */
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readBossSaveData(tag);
        if (tag.contains("ChatLine")) {
            this.chatLine = tag.getInt("ChatLine");
        }
        if (tag.contains("OffsetX")) {
            double offsetX = this.getX() + tag.getDouble("OffsetX");
            double offsetY = this.getY() + tag.getDouble("OffsetY");
            double offsetZ = this.getZ() + tag.getDouble("OffsetZ");
            this.origin = new Vec3(offsetX, offsetY, offsetZ);
        } else {
            this.origin = this.position();
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

    /**
     * Handles the Sun Spirit looking at the player, prevents it if the player is invisible.
     */
    public static class SunSpiritLookGoal extends LookAtPlayerGoal {
        public SunSpiritLookGoal(Mob mob, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability) {
            this(mob, lookAtType, lookDistance, probability, false);
        }

        public SunSpiritLookGoal(Mob mob, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability, boolean onlyHorizontal) {
            super(mob, lookAtType, lookDistance, probability, onlyHorizontal);
            TargetingConditions conditions;
            if (lookAtType == Player.class) {
                conditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().range(lookDistance).selector((entity) -> EntitySelector.notRiding(mob).test(entity));
            } else {
                conditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().range(lookDistance);
            }
            ((LookAtPlayerGoalAccessor) this).aether$setLookAtContext(conditions);
        }
    }

    /**
     * Sets the wanted movement direction of the Sun Spirit during the fight.
     */
    public static class FlyAroundGoal extends Goal {
        private final SunSpirit sunSpirit;
        private float rotation;
        private int courseChangeTimer;

        public FlyAroundGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.rotation = sunSpirit.random.nextFloat() * 360;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public void tick() {
            boolean changedCourse = this.outOfBounds();
            double x = Mth.sin(this.rotation * Mth.DEG_TO_RAD) * this.sunSpirit.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.sunSpirit.speedModifier;
            double z = -Mth.cos(this.rotation * Mth.DEG_TO_RAD) * this.sunSpirit.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.sunSpirit.speedModifier;
            this.sunSpirit.setDeltaMovement(x, 0, z);
            if (changedCourse || ++this.courseChangeTimer >= 20) {
                if (this.sunSpirit.getRandom().nextInt(3) == 0) {
                    this.rotation += this.sunSpirit.getRandom().nextFloat() - this.sunSpirit.getRandom().nextFloat() * 60;
                }
                this.rotation = Mth.wrapDegrees(this.rotation);
                this.courseChangeTimer = 0;
            }
        }

        protected boolean outOfBounds() {
            boolean flag = false;
            if ((this.sunSpirit.getDeltaMovement().x() >= 0 && this.sunSpirit.getX() >= this.sunSpirit.origin.x() + this.sunSpirit.xMax) ||
                    (this.sunSpirit.getDeltaMovement().x() <= 0 && this.sunSpirit.getX() <= this.sunSpirit.origin.x() - this.sunSpirit.xMax)) {
                this.rotation = 360 - this.rotation;
                flag = true;
            }
            if ((this.sunSpirit.getDeltaMovement().z() >= 0 && this.sunSpirit.getZ() >= this.sunSpirit.origin.z() + this.sunSpirit.zMax) ||
                    (this.sunSpirit.getDeltaMovement().z() <= 0 && this.sunSpirit.getZ() <= this.sunSpirit.origin.z() - this.sunSpirit.zMax)) {
                this.rotation = 180 - this.rotation;
                flag = true;
            }
            return flag;
        }

        @Override
        public boolean canUse() {
            return this.sunSpirit.isBossFight();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    /**
     * The Sun Spirit will stay at its origin point when not in a boss fight.
     */
    public static class DoNothingGoal extends Goal {
        private final SunSpirit sunSpirit;

        public DoNothingGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return !this.sunSpirit.isBossFight();
        }

        /**
         * Returns the Sun Spirit to its original position.
         */
        @Override
        public void start() {
            this.sunSpirit.setDeltaMovement(Vec3.ZERO);
            this.sunSpirit.setPos(this.sunSpirit.origin.x(), this.sunSpirit.origin.y(), this.sunSpirit.origin.z());
        }
    }

    /**
     * This goal makes the Sun Spirit shoot Fire Crystals and Ice Crystals randomly.
     * It shoots more crystals as its health gets lower.
     */
    public static class ShootFireballGoal extends Goal {
        private final SunSpirit sunSpirit;
        private int shootCrystalInterval;
        private int crystalCount;

        public ShootFireballGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.shootCrystalInterval = SHOOT_CRYSTAL_INTERVAL;
            this.crystalCount = ICE_CRYSTAL_SHOOT_COUNT_INTERVAL;
        }

        @Override
        public boolean canUse() {
            return this.sunSpirit.isBossFight() && --this.shootCrystalInterval <= 0;
        }

        @Override
        public void start() {
            AbstractCrystal crystal;
            if (--this.crystalCount <= 0) {
                crystal = new IceCrystal(this.sunSpirit.level(), this.sunSpirit);
                this.crystalCount = ICE_CRYSTAL_SHOOT_COUNT_INTERVAL;
                this.sunSpirit.playSound(this.sunSpirit.getShootIceSound(), 3.0F, this.sunSpirit.level().getRandom().nextFloat() - this.sunSpirit.level().getRandom().nextFloat() * 0.2F + 1.2F);
            } else {
                crystal = new FireCrystal(this.sunSpirit.level(), this.sunSpirit);
                this.sunSpirit.playSound(this.sunSpirit.getShootFireSound(), 3.0F, this.sunSpirit.level().getRandom().nextFloat() - this.sunSpirit.level().getRandom().nextFloat() * 0.2F + 1.2F);
            }
            this.sunSpirit.level().addFreshEntity(crystal);
            this.shootCrystalInterval = SHOOT_CRYSTAL_INTERVAL;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    /**
     * Randomly places fire below the Sun Spirit
     */
    public static class SummonFireGoal extends Goal {
        private final SunSpirit sunSpirit;
        private int summonFireInterval;

        public SummonFireGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.summonFireInterval = SPAWN_FIRE_INTERVAL;
        }

        @Override
        public boolean canUse() {
            return this.sunSpirit.isBossFight() && --this.summonFireInterval <= 0;
        }

        @Override
        public void start() {
            BlockPos pos = BlockPos.containing(this.sunSpirit.getX(), this.sunSpirit.getY(), this.sunSpirit.getZ());
            for (int i = 0; i <= 3; i++) {
                if (this.sunSpirit.level().isEmptyBlock(pos) && !this.sunSpirit.level().isEmptyBlock(pos.below())) {
                    this.sunSpirit.level().setBlock(pos, Blocks.FIRE.defaultBlockState(), 1 | 2 | 8);
                    break;
                }
                pos = pos.below();
            }
            this.summonFireInterval = SPAWN_FIRE_INTERVAL;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}
