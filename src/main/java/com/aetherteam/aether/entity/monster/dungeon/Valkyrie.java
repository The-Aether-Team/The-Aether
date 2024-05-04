package com.aetherteam.aether.entity.monster.dungeon;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.network.chat.Component;
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

public class Valkyrie extends AbstractValkyrie implements NeutralMob {
    /**
     * General neutral mob necessities. Valkyries will only attack when provoked.
     */
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;
    /**
     * Timer for how long until the player can interact to talk with a Valkyrie again.
     * This Prevents the player from quickly talking to the Valkyrie in succession.
     */
    private int chatTimer;

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
     * Allows the players to chat with the Valkyries if they're not provoked. Players will see the chat messages only
     * if they are the one who interacted with the valkyrie.
     *
     * @param player The interacting {@link Player}.
     * @param hand   The {@link InteractionHand}.
     * @return The {@link InteractionResult}.
     */
    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND) {
            if (this.getTarget() == null) {
                this.lookAt(player, 180.0F, 180.0F); // Look at player.
                if (!this.level().isClientSide() && this.chatTimer <= 0) {
                    String translationId;
                    if (item.getItem() == AetherItems.VICTORY_MEDAL.get()) { // Change what message is displayed depending on how many medals a player shows a Valkyrie.
                        if (item.getCount() >= 10) {
                            translationId = "gui.aether.valkyrie.dialog.medal.1";
                        } else if (item.getCount() >= 5) {
                            translationId = "gui.aether.valkyrie.dialog.medal.2";
                        } else {
                            translationId = "gui.aether.valkyrie.dialog.medal.3";
                        }
                    } else {
                        translationId = "gui.aether.valkyrie.dialog." + (char) (this.getRandom().nextInt(3) + '1');
                    }
                    this.chat(player, Component.translatable(translationId));
                    this.playSound(this.getInteractSound(), 1.0F, this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat() * 0.2F + 1.2F);
                    this.chatTimer = 60;
                }
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Plays the message for the Valkyrie going to attack the player when hurt.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (!this.level().isClientSide() && source.getEntity() instanceof Player player) {
            if (this.getTarget() == null && this.level().getDifficulty() != Difficulty.PEACEFUL && this.getHealth() > 0) {
                this.chat(player, Component.translatable("gui.aether.valkyrie.dialog.attack." + (char) (this.getRandom().nextInt(3) + '1')));
            }
        }
        return result;
    }

    /**
     * Plays the message for the Valkyrie defeating the player.
     *
     * @param entity The hurt {@link Entity}.
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (entity instanceof ServerPlayer player && player.getHealth() <= 0) {
            this.chat(player, Component.translatable("gui.aether.valkyrie.dialog.playerdeath." + (char) (this.getRandom().nextInt(3) + '1'), player.getDisplayName()));
        }
        return result;
    }

    /**
     * Plays the Valkyrie's defeat message.
     *
     * @param source The {@link DamageSource}.
     */
    @Override
    public void die(DamageSource source) {
        if (source.getEntity() instanceof Player player) {
            this.chat(player, Component.translatable("gui.aether.valkyrie.dialog.defeated." + (char) (this.getRandom().nextInt(3) + '1')));
        }
        this.spawnExplosionParticles();
        super.die(source);
    }

    /**
     * Sets a random time for how long this entity should be angry.
     */
    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.getRandom()));
    }

    /**
     * @return How long this entity should be angry, as an {@link Integer}.
     */
    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    /**
     * Sets how long this entity should be angry.
     *
     * @param time The {@link Integer} value.
     */
    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.remainingPersistentAngerTime = time;
    }

    /**
     * @return The {@link UUID} of the target to be angry at.
     */
    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    /**
     * Sets the target to be angry at.
     *
     * @param target The target's {@link UUID}.
     */
    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    protected SoundEvent getInteractSound() {
        return AetherSoundEvents.ENTITY_VALKYRIE_INTERACT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AetherSoundEvents.ENTITY_VALKYRIE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_VALKYRIE_DEATH.get();
    }
}
