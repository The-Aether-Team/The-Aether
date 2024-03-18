package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.event.hooks.AbilityHooks;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityStruckByLightningEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.ProjectileImpactEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class WeaponAbilityListener {
    /**
     * @see AbilityHooks.WeaponHooks#stickDart(LivingEntity, DamageSource)
     */
    public static void onDartHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        if (!event.isCanceled()) {
            AbilityHooks.WeaponHooks.stickDart(livingEntity, damageSource);
        }
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
    public static void onEntityDamage(LivingDamageEvent event) {
        LivingEntity targetEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity sourceEntity = damageSource.getDirectEntity();
        event.setAmount(AbilityHooks.WeaponHooks.reduceWeaponEffectiveness(targetEntity, sourceEntity, event.getAmount()));
        event.setAmount(AbilityHooks.WeaponHooks.reduceArmorEffectiveness(targetEntity, sourceEntity, event.getAmount()));
    }

    public static void init() {
        LivingHurtEvent.HURT.register(WeaponAbilityListener::onDartHurt);
        EntityEvents.ENTITY_STRUCK_BY_LIGHTING.register(WeaponAbilityListener::onLightningStrike);
        ProjectileImpactEvent.PROJECTILE_IMPACT.register(WeaponAbilityListener::onArrowHit);
        LivingDamageEvent.DAMAGE.register(WeaponAbilityListener::onEntityDamage);
    }
}
