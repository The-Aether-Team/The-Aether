package com.gildedgames.aether.event.listeners.abilities;

import com.gildedgames.aether.event.hooks.AbilityHooks;
import com.gildedgames.aether.item.combat.abilities.armor.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ArmorAbilityListener {
    /**
     * @see ValkyrieArmor#handleFlight(LivingEntity)
     * @see NeptuneArmor#boostWaterSwimming(LivingEntity)
     * @see PhoenixArmor#boostLavaSwimming(LivingEntity)
     * @see PhoenixArmor#damageArmor(LivingEntity)
     */
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        ValkyrieArmor.handleFlight(livingEntity);
        NeptuneArmor.boostWaterSwimming(livingEntity);
        PhoenixArmor.boostLavaSwimming(livingEntity);
        PhoenixArmor.damageArmor(livingEntity);
    }

    /**
     * @see GravititeArmor#boostedJump(LivingEntity)
     */
    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntity();
        GravititeArmor.boostedJump(livingEntity);
    }

    /**
     * @see AbilityHooks.ArmorHooks#fallCancellation(LivingEntity)
     */
    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntity();
        event.setCanceled(AbilityHooks.ArmorHooks.fallCancellation(livingEntity));
    }

    /**
     * @see PhoenixArmor#extinguishUser(LivingEntity, DamageSource)
     */
    @SubscribeEvent
    public static void onEntityAttack(LivingAttackEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        event.setCanceled(PhoenixArmor.extinguishUser(livingEntity, damageSource));
    }

    /**
     * @see ObsidianArmor#protectUser(LivingEntity, float)
     */
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        float damageAmount = event.getAmount();
        event.setAmount(ObsidianArmor.protectUser(entity, damageAmount));
    }
}
