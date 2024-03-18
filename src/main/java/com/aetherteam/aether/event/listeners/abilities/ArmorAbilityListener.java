package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.item.combat.abilities.armor.GravititeArmor;
import com.aetherteam.aether.item.combat.abilities.armor.NeptuneArmor;
import com.aetherteam.aether.item.combat.abilities.armor.PhoenixArmor;
import com.aetherteam.aether.item.combat.abilities.armor.ValkyrieArmor;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ArmorAbilityListener {
    /**
     * @see ValkyrieArmor#handleFlight(LivingEntity)
     * @see NeptuneArmor#boostWaterSwimming(LivingEntity)
     * @see PhoenixArmor#boostLavaSwimming(LivingEntity)
     * @see PhoenixArmor#damageArmor(LivingEntity)
     */
    public static void onEntityUpdate(LivingEntityEvents.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (!event.isCanceled()) {
            ValkyrieArmor.handleFlight(livingEntity);
            NeptuneArmor.boostWaterSwimming(livingEntity);
            PhoenixArmor.boostLavaSwimming(livingEntity);
            PhoenixArmor.damageArmor(livingEntity);
        }
    }

    /**
     * @see GravititeArmor#boostedJump(LivingEntity)
     */
    public static void onEntityJump(LivingEntityEvents.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntity();
        GravititeArmor.boostedJump(livingEntity);
    }

    /**
     * @see AbilityHooks.ArmorHooks#fallCancellation(LivingEntity)
     */
    public static void onEntityFall(LivingEntityEvents.Fall.FallEvent event) {
        LivingEntity livingEntity = (LivingEntity) event.getEntity();
        if (!event.isCanceled()) {
            event.setCanceled(AbilityHooks.ArmorHooks.fallCancellation(livingEntity));
        }
    }

    /**
     * @see PhoenixArmor#extinguishUser(LivingEntity, DamageSource)
     */
    public static void onEntityAttack(LivingAttackEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        if (!event.isCanceled()) {
            event.setCanceled(PhoenixArmor.extinguishUser(livingEntity, damageSource));
        }
    }

    public static void init() {
        LivingEntityEvents.LivingTickEvent.TICK.register(ArmorAbilityListener::onEntityUpdate);
        LivingEntityEvents.LivingJumpEvent.JUMP.register(ArmorAbilityListener::onEntityJump);
        LivingEntityEvents.FALL.register(ArmorAbilityListener::onEntityFall);
        LivingAttackEvent.ATTACK.register(ArmorAbilityListener::onEntityAttack);
    }
}
