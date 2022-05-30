package com.gildedgames.aether.common.entity.monster.dungeon;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.BossInfoPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * This class holds the implementation of valkyrie queens. They are the boss version of valkyries, and they fight
 * in the same way, with the additional ability to shoot thunder crystal projectiles at their enemies.
 */
public class ValkyrieQueen extends AbstractValkyrie {
    public static final EntityDataAccessor<Boolean> DATA_IS_INVULNERABLE = SynchedEntityData.defineId(ValkyrieQueen.class, EntityDataSerializers.BOOLEAN);
    /** Boss health bar manager */
    private final ServerBossEvent bossFight;

    public ValkyrieQueen(EntityType<? extends ValkyrieQueen> type, Level level) {
        super(type, level);
        this.bossFight  = new ServerBossEvent(new TextComponent("QUEEEEEENNNN"), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setVisible(false);
        this.xpReward = 50;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
    }

    @Nonnull
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 28.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 13.5)
                .add(Attributes.MAX_HEALTH, 500.0);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_INVULNERABLE, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("invulnerable", this.entityData.get(DATA_IS_INVULNERABLE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_IS_INVULNERABLE, tag.getBoolean("invulnerable"));
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
    }

    /**
     * Allows the players to start a conversation with the valkyrie queen.
     */
    @Override
    @Nonnull
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (this.getTarget() == null) {
            if (!this.level.isClientSide && this.level.getDifficulty() == Difficulty.PEACEFUL && this.chatTimer <= 0) {
                this.chatItUp(player, new TranslatableComponent("gui.aether.queen.peaceful"));
                this.chatTimer = 60;
            } else {
                this.lookAt(player, 180.0F, 180.0F);
                if (!this.level.isClientSide && this.chatTimer <= 0) {
                    if (!this.isInvulnerable() || item.getItem() == AetherItems.VICTORY_MEDAL.get() && item.getCount() >= 10) {
                        this.setInvulnerable(false);
                        this.chatItUp(player, new TranslatableComponent("gui.aether.queen.ready"));
                    } else {
                        this.chatItUp(player, new TranslatableComponent("gui.aether.queen.no_medals"));
                    }
                    this.chatTimer = 60;
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    /**
     * The valkyrie queen is invulnerable until 10 victory medals are presented.
     */
    @Override
    public boolean hurt(@Nonnull DamageSource source, float pDamageAmount) {
        boolean flag = !this.isInvulnerable() && super.hurt(source, pDamageAmount);
        if (!this.level.isClientSide && source.getEntity() instanceof Player player) {
            if (this.getTarget() == null && flag && level.getDifficulty() != Difficulty.PEACEFUL && this.getHealth() > 0) {
                this.bossFight.setVisible(true);
                chatItUp(player, new TranslatableComponent("gui.aether.queen.fight"));
            }
        }
        return flag;
    }

    /**
     * If the valkyrie kills the player, they will speak.
     */
    @Override
    public boolean doHurtTarget(@Nonnull Entity pEntity) {
        boolean result = super.doHurtTarget(pEntity);
        if (pEntity instanceof ServerPlayer player && player.getHealth() <= 0) {
            this.chatItUp(player, new TranslatableComponent("gui.aether.queen.playerdeath"));
        }
        return result;
    }


    /**
     * Plays the valkyrie's defeat message.
     */
    @Override
    public void die(DamageSource pCause) {
        if (pCause.getEntity() instanceof Player player) {
            this.chatItUp(player, new TranslatableComponent("gui.aether.queen.defeated"));
        }
        this.spawnExplosionParticles();
        super.die(pCause);
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

    public boolean isInvulnerable() {
        return this.entityData.get(DATA_IS_INVULNERABLE);
    }

    public void setInvulnerable(boolean invulnerable) {
        this.entityData.set(DATA_IS_INVULNERABLE, invulnerable);
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return AetherSoundEvents.ENTITY_VALKYRIE_QUEEN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_VALKYRIE_QUEEN_DEATH.get();
    }
}
