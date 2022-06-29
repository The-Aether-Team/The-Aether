package com.gildedgames.aether.event.listeners.abilities;

import com.gildedgames.aether.event.hooks.AbilityHooks;
import com.gildedgames.aether.common.item.combat.abilities.armor.*;
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
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        ValkyrieArmor.handleFlight(livingEntity);
        NeptuneArmor.boostWaterSwimming(livingEntity);
        PhoenixArmor.boostLavaSwimming(livingEntity);
        PhoenixArmor.damageArmor(livingEntity);
    }

    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        GravititeArmor.boostedJump(livingEntity);
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        event.setCanceled(AbilityHooks.ArmorHooks.fallCancellation(livingEntity));
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingAttackEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        event.setCanceled(PhoenixArmor.extinguishUser(livingEntity, damageSource));
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        float damageAmount = event.getAmount();
        event.setAmount(ObsidianArmor.protectUser(entity, damageAmount));
    }
}
