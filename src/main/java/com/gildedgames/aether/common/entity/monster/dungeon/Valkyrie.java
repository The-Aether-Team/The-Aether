package com.gildedgames.aether.common.entity.monster.dungeon;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.ai.goal.target.MostDamageTargetGoal;
import com.gildedgames.aether.common.registry.AetherItems;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * This class holds the implementation for valkyries. Valkyries are neutral mobs that patrol the silver dungeon.
 * They won't attack unless provoked. They can teleport within the temple. They respond to the player through chat
 * messages and drop a victory medal upon their defeat.
 */
public class Valkyrie extends AbstractValkyrie implements NeutralMob {
    /** Prevents the player from quickly talking to the valkyrie in succession. */
    private int chatTimer;
    /** Keeps track of the previous y motion value. */
    private double motionYo;
    /** General neutral mob necessities */
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    public Valkyrie(EntityType<? extends Valkyrie> type, Level worldIn) {
        super(type, worldIn);
        this.teleportTimer = this.getRandom().nextInt(200);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Nonnull
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MAX_HEALTH, 50.0);
    }

    @Override
    public void tick() {
        this.motionYo = this.getDeltaMovement().y;
        super.tick();
        if (this.isOnGround()) {
            this.setEntityOnGround(true);
        }
    }

    /**
     * Handles some movement logic for the valkyrie.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.onGround && this.getTarget() != null && this.motionYo >= 0 && this.getDeltaMovement().y < 0 && this.distanceTo(this.getTarget()) <= 16F && this.hasLineOfSight(this.getTarget())) {
            double x = this.getTarget().getX() - this.getX();
            double z = this.getTarget().getZ() - this.getZ();
            double angle = Math.atan2(z, x);
            this.setDeltaMovement(Math.cos(angle) * 0.25, this.getDeltaMovement().y, Math.sin(angle) * 0.25);
            this.setYRot((float) angle * (180F / (float) Math.PI) - 90F);
        }
        if (!this.onGround && Math.abs(this.getDeltaMovement().y - this.motionYo) > 0.07D && Math.abs(this.getDeltaMovement().y - this.motionYo) < 0.09D) {
            double fallSpeed;
            AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
            if (gravity != null) {
                fallSpeed = Math.max(gravity.getValue() * -0.625, -0.275);
            } else {
                fallSpeed = -0.275;
            }
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.055, 0));
            if (this.getDeltaMovement().y < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
                this.setEntityOnGround(false);
            }
        }
    }

    /**
     * Increments the chat timer.
     */
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (this.chatTimer > 0) {
            this.chatTimer--;
        }
    }

    /**
     * Sends a message to the player who interacted with the valkyrie.
     */
    private void chatItUp(Player player, Component message) {
        player.sendMessage(message, player.getUUID());
    }

    /**
     * Allows the players to chat up the valkyries if they're not provoked. Players will see the chat messages only
     * if they are the one who interacted with the valkyrie.
     */
    @Override
    @Nonnull
    protected InteractionResult mobInteract(Player player, @Nonnull InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (this.getTarget() == null) {
            this.lookAt(player, 180.0F, 180.0F);
            if (!this.level.isClientSide && this.chatTimer <= 0) {
                String translationId;
                if (item.getItem() == AetherItems.VICTORY_MEDAL.get()) {
                    if (item.getCount() >= 10) {
                        translationId = "gui.aether.valkyrie.dialog.medal.1";
                    } else if (item.getCount() >= 5) {
                        translationId = "gui.aether.valkyrie.dialog.medal.2";
                    } else {
                        translationId = "gui.aether.valkyrie.dialog.medal.3";
                    }
                } else {
                    translationId = "gui.aether.valkyrie.dialog." + (char) (random.nextInt(3) + '1');
                }
                this.chatItUp(player, new TranslatableComponent(translationId));
                this.chatTimer = 60;
            }
        }
        return super.mobInteract(player, hand);
    }

    /**
     * The valkyrie will be provoked to attack the player if attacked.
     * This also handles the defeat message if their health drops below 0.
     */
    @Override
    public boolean hurt(@Nonnull DamageSource source, float pDamageAmount) {
        boolean result = super.hurt(source, pDamageAmount);
        if (!this.level.isClientSide && source.getEntity() instanceof Player player) {
            if (this.getTarget() == null && level.getDifficulty() != Difficulty.PEACEFUL && this.getHealth() > 0) {
                chatItUp(player, new TranslatableComponent("gui.aether.valkyrie.dialog.attack." + (char) (random.nextInt(3) + '1')));
            }
        }
        return result;
    }

    /**
     * If the valkyrie kills the player, they will speak.
     */
    @Override
    public boolean doHurtTarget(@Nonnull Entity pEntity) {
        boolean result = super.doHurtTarget(pEntity);
        if (pEntity instanceof ServerPlayer player && player.getHealth() <= 0) {
            this.chatItUp(player, new TranslatableComponent("gui.aether.valkyrie.dialog.playerdeath." + (char) (random.nextInt(3) + '1')));
        }
        return result;
    }

    /**
     * Plays the valkyrie's defeat message.
     */
    @Override
    public void die(DamageSource pCause) {
        if (pCause.getEntity() instanceof Player player) {
            this.chatItUp(player, new TranslatableComponent("gui.aether.valkyrie.dialog.defeated." + (char) (random.nextInt(3) + '1')));
        }
        this.spawnExplosionParticles();
        super.die(pCause);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pTime) {
        this.remainingPersistentAngerTime = pTime;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return AetherSoundEvents.ENTITY_VALKYRIE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_VALKYRIE_DEATH.get();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }
}