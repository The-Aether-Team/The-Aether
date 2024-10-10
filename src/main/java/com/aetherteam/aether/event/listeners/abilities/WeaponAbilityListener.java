package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class WeaponAbilityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(WeaponAbilityListener::onDartHurt);
        bus.addListener(WeaponAbilityListener::onArrowHit);
        bus.addListener(WeaponAbilityListener::onLightningStrike);
        bus.addListener(WeaponAbilityListener::onEntityDamage);
    }

    /**
     * @see AbilityHooks.WeaponHooks#stickDart(LivingEntity, DamageSource)
     */
    public static void onDartHurt(LivingDamageEvent.Pre event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        AbilityHooks.WeaponHooks.stickDart(livingEntity, damageSource);
    }

    /**
     * @see AbilityHooks.WeaponHooks#phoenixArrowHit(HitResult, Projectile)
     */
    public static void onArrowHit(ProjectileImpactEvent event) {
        HitResult hitResult = event.getRayTraceResult();
        Projectile projectile = event.getProjectile();
        if (!event.isCanceled()) {
            AbilityHooks.WeaponHooks.phoenixArrowHit(hitResult, projectile);
        }
    }

    /**
     * @see AbilityHooks.WeaponHooks#lightningTracking(Entity, LightningBolt)
     */
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        LightningBolt lightningBolt = event.getLightning();
        if (!event.isCanceled() && AbilityHooks.WeaponHooks.lightningTracking(entity, lightningBolt)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#reduceWeaponEffectiveness(LivingEntity, Entity, float)
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#reduceArmorEffectiveness(LivingEntity, Entity, float)
     */
    public static void onEntityDamage(LivingDamageEvent.Pre event) {
        LivingEntity targetEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity sourceEntity = damageSource.getDirectEntity();
        event.setNewDamage(AbilityHooks.WeaponHooks.reduceWeaponEffectiveness(targetEntity, sourceEntity, event.getNewDamage()));
        event.setNewDamage(AbilityHooks.WeaponHooks.reduceArmorEffectiveness(targetEntity, sourceEntity, event.getNewDamage()));
    }
}
