package com.aetherteam.aetherfabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class LivingEntityEvents {

    public static final Event<Jumped> ON_JUMP = EventFactory.createArrayBacked(Jumped.class, invokers -> livingEntity -> {
        for (var invoker : invokers) invoker.onJump(livingEntity);
    });

    public static final Event<ShieldBlock> ON_SHIELD_BLOCK = EventFactory.createArrayBacked(ShieldBlock.class, invokers -> (damageSource, callback) -> {
        for (var invoker : invokers) invoker.onBlock(damageSource, callback);
    });

    public static final Event<Fall> ON_FALL = EventFactory.createArrayBacked(Fall.class, invokers -> (entity, helper) -> {
        for (var invoker : invokers) invoker.onFall(entity, helper);
    });

    public static final Event<ExperienceDrop> ON_EXPERIENCE_DROP = EventFactory.createArrayBacked(ExperienceDrop.class, invokers -> (entity, attackingPlayer, helper) -> {
        for (var invoker : invokers) invoker.onExperienceDrop(entity, attackingPlayer, helper);
    });

    public static final Event<ModifyDamage> ON_DAMAGE = EventFactory.createArrayBacked(ModifyDamage.class, invokers -> (entity, source, originalDamage, newDamage) -> {
        for (var invoker : invokers) invoker.modifyDamage(entity, source, originalDamage, newDamage);
    });

    public static final Event<OnStatusEffect> ON_EFFECT = EventFactory.createArrayBacked(OnStatusEffect.class, invokers -> (entity, instance, result) -> {
        for (var invoker : invokers) {
            var newResult = invoker.onEffect(entity, instance, result);

            if (newResult != null) result = newResult;
        }
        return result;
    });

    public static final Event<Visibility> ON_VISIBILITY_CALCULATED = EventFactory.createArrayBacked(Visibility.class, invokers -> (livingEntity, lookingEntity, visibilityValue) -> {
        for (var invoker : invokers) invoker.adjustVisibility(livingEntity, lookingEntity, visibilityValue);
    });

    public static final Event<Swing> ON_SWING = EventFactory.createArrayBacked(Swing.class, invokers -> (stack, entity, hand, callback) -> {
        for (var invoker : invokers) invoker.onSwing(stack, entity, hand, callback);
    });

    public interface Visibility {
        void adjustVisibility(LivingEntity livingEntity, @Nullable Entity lookingEntity, MutableDouble visibilityValue);
    }

    public interface Swing {
        void onSwing(ItemStack stack, LivingEntity entity, InteractionHand hand, CancellableCallback callback);
    }

    public interface Jumped {
        void onJump(LivingEntity livingEntity);
    }

    public interface ShieldBlock {
        void onBlock(DamageSource damageSource, CancellableCallback callback);
    }

    public interface Fall {
        void onFall(LivingEntity entity, FallHelper event);
    }

    public interface ExperienceDrop {
        void onExperienceDrop(LivingEntity entity, @Nullable Player attackingPlayer, ExperienceDropHelper helper);
    }

    public interface OnDrops {
        void onDrops(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops, boolean recentlyHit, CancellableCallback callback);
    }

    @FunctionalInterface
    public interface ModifyDamage {
        /**
         * Called when a living entity is going to take damage. Can be used to cancel the damage entirely.
         *
         * <p>The amount corresponds to the "incoming" damage amount, before armor and other mitigations have been applied.
         *
         * @param entity         the entity
         * @param source         the source of the damage
         * @param originalDamage the amount of damage that the entity will take (before modifications)
         * @param newDamage      the amount of damage that the entity will be taking
         */
        void modifyDamage(LivingEntity entity, DamageSource source, float originalDamage, MutableFloat newDamage);
    }

    public interface OnStatusEffect {
        @Nullable TriState onEffect(LivingEntity entity, MobEffectInstance instance, TriState result);
    }
}
