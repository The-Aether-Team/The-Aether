package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityStruckByLightningEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.ProjectileImpactCallback;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class WeaponAbilityListener {
    /**
     * @see AbilityHooks.WeaponHooks#stickDart(LivingEntity, DamageSource)
     */
    public static float onDartHurt(DamageSource damageSource, LivingEntity livingEntity, float amount) {
        AbilityHooks.WeaponHooks.stickDart(livingEntity, damageSource);
        return amount;
    }

    /**
     * @see AbilityHooks.WeaponHooks#phoenixArrowHit(HitResult, Projectile)
     */
    @SubscribeEvent
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
    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        LivingEntity targetEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity sourceEntity = damageSource.getDirectEntity();
        event.setAmount(AbilityHooks.WeaponHooks.reduceWeaponEffectiveness(targetEntity, sourceEntity, event.getAmount()));
        event.setAmount(AbilityHooks.WeaponHooks.reduceArmorEffectiveness(targetEntity, sourceEntity, event.getAmount()));
    }

    public static void init() {
        LivingEntityEvents.HURT.register(WeaponAbilityListener::onDartHurt);
        EntityEvents.ENTITY_STRUCK_BY_LIGHTING.register(WeaponAbilityListener::onLightningStrike);
        ProjectileImpactCallback.EVENT.register();
    }
}
