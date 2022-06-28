package com.gildedgames.aether.common.entity.monster.dungeon;

import com.gildedgames.aether.common.entity.BossMob;
import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.BossInfoPacket;
import com.gildedgames.aether.core.util.BossNameGenerator;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation for the sun spirit, the final boss of the Aether. When the sun spirit is defeated, eternal day will
 * end in the dimension.
 */
public class SunSpirit extends Monster implements BossMob {
    public static final EntityDataAccessor<Boolean> DATA_IS_FROZEN = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.COMPONENT);

    /** The sun spirit will return here when not in a fight. */
    private BlockPos originPos;
    /** Boss health bar manager */
    private final ServerBossEvent bossFight;

    public SunSpirit(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
//        this.bossFight.setVisible(false);
        this.xpReward = XP_REWARD_BOSS;
    }

    /**
     * Generates a name for the boss.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setBossName(BossNameGenerator.generateSunSpiritName());
        this.originPos = new BlockPos(this.position());
        return data;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 48.0F));
    }

    public static AttributeSupplier.Builder createSunSpiritAttributes() {
        return AbstractValkyrie.createAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 1.0);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_FROZEN, false);
        this.entityData.define(DATA_BOSS_NAME, Component.literal("Sun Spirit"));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("BossName", Component.Serializer.toJson(this.getBossName()));
        tag.putInt("OriginX", this.originPos.getX());
        tag.putInt("OriginY", this.originPos.getY());
        tag.putInt("OriginZ", this.originPos.getZ());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        Component name = Component.Serializer.fromJson(tag.getString("BossName"));
        if (name != null) {
            this.setBossName(name);
        }
        if (tag.contains("OriginX")) {
            this.originPos = new BlockPos(tag.getInt("OriginX"), tag.getInt("OriginY"), tag.getInt("OriginZ"));
        }
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.setFrozen(this.hurtTime > 0);
    }

    /**
     * Plays the sun spirit's defeat message and ends eternal day.
     */
    @Override
    public void die(@Nonnull DamageSource pCause) {
        if (!this.level.isClientSide) {
            this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.dead").withStyle(ChatFormatting.AQUA));
            this.level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).ifPresent(aetherTime -> {
                aetherTime.setEternalDay(false);
                aetherTime.updateEternalDay();
            });
        }
        super.die(pCause);
    }

    /**
     * Sends a message to nearby players. Useful for boss fights.
     */
    protected void chatWithNearby(Component message) {
        this.level.getNearbyPlayers(NON_COMBAT, this, this.getBoundingBox().inflate(16, 16, 16)).forEach(player ->
                player.sendSystemMessage(message));
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    @Override
    public void startSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId()), pPlayer);
        this.bossFight.addPlayer(pPlayer);
    }

    /**
     * Removes the given player from the list of players tracking this entity.
     */
    @Override
    public void stopSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.stopSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId()), pPlayer);
        this.bossFight.removePlayer(pPlayer);
    }

    public boolean isFrozen() {
        return this.entityData.get(DATA_IS_FROZEN);
    }

    public void setFrozen(boolean frozen) {
        this.entityData.set(DATA_IS_FROZEN, frozen);
    }

    @Override
    public Component getBossName() {
        return this.entityData.get(DATA_BOSS_NAME);
    }

    @Override
    public void setBossName(Component component) {
        this.entityData.set(DATA_BOSS_NAME, component);
        this.bossFight.setName(component);
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }
}
