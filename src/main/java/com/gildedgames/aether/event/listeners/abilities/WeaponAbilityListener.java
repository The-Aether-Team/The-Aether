package com.gildedgames.aether.event.listeners.abilities;

import com.gildedgames.aether.event.hooks.AbilityHooks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WeaponAbilityListener {
    @SubscribeEvent
    public static void onArrowHit(ProjectileImpactEvent event) {
        HitResult hitResult = event.getRayTraceResult();
        Projectile projectile = event.getProjectile();
        AbilityHooks.WeaponHooks.phoenixArrowHit(hitResult, projectile);
    }

    @SubscribeEvent
    public static void onDartHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        AbilityHooks.WeaponHooks.stickDart(livingEntity, damageSource);
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        LightningBolt lightningBolt = event.getLightning();
        AbilityHooks.WeaponHooks.lightningTracking(event, entity, lightningBolt); // Has to take event due to the event being canceled within a lambda.
    }
}
