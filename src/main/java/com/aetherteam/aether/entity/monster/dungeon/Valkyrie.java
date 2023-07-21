package com.aetherteam.aether.entity.monster.dungeon;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * This class holds the implementation for valkyries. Valkyries are neutral mobs that patrol the silver dungeon.
 * They won't attack unless provoked. They can teleport within the temple. They respond to the player through chat
 * messages and drop a victory medal upon their defeat.
 */
public class Valkyrie extends AbstractValkyrie implements NeutralMob {
    /** Prevents the player from quickly talking to the valkyrie in succession. */
    protected int chatTimer;
    /** General neutral mob necessities */
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    public Valkyrie(EntityType<? extends Valkyrie> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

   
    public static AttributeSupplier.Builder createMobAttributes() {
        return createAttributes()
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MAX_HEALTH, 50.0);
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
     * Allows the players to chat up the valkyries if they're not provoked. Players will see the chat messages only
     * if they are the one who interacted with the valkyrie.
     */
    @Override
   
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND) {
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
                    this.chatItUp(player, Component.translatable(translationId));
                    this.chatTimer = 60;
                }
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * The valkyrie will be provoked to attack the player if attacked.
     * This also handles the defeat message if their health drops below 0.
     */
    @Override
    public boolean hurt(DamageSource source, float pDamageAmount) {
        boolean result = super.hurt(source, pDamageAmount);
        if (!this.level.isClientSide && source.getEntity() instanceof Player player) {
            if (this.getTarget() == null && level.getDifficulty() != Difficulty.PEACEFUL && this.getHealth() > 0) {
                chatItUp(player, Component.translatable("gui.aether.valkyrie.dialog.attack." + (char) (random.nextInt(3) + '1')));
            }
        }
        return result;
    }

    /**
     * If the valkyrie kills the player, they will speak.
     */
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        boolean result = super.doHurtTarget(pEntity);
        if (pEntity instanceof ServerPlayer player && player.getHealth() <= 0) {
            this.chatItUp(player, Component.translatable("gui.aether.valkyrie.dialog.playerdeath." + (char) (random.nextInt(3) + '1'), ComponentUtils.getDisplayName(player.getGameProfile())));
        }
        return result;
    }

    /**
     * Plays the valkyrie's defeat message.
     */
    @Override
    public void die(DamageSource pCause) {
        if (pCause.getEntity() instanceof Player player) {
            this.chatItUp(player, Component.translatable("gui.aether.valkyrie.dialog.defeated." + (char) (random.nextInt(3) + '1')));
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
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AetherSoundEvents.ENTITY_VALKYRIE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_VALKYRIE_DEATH.get();
    }
}