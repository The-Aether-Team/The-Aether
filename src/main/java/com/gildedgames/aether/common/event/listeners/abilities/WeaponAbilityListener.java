package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.event.hooks.AbilityHooks;
import com.gildedgames.aether.common.item.combat.abilities.weapon.HolystoneWeapon;
import com.gildedgames.aether.common.item.combat.abilities.weapon.SkyrootWeapon;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber
public class WeaponAbilityListener {
    @SubscribeEvent
    public static void trackEntityDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        SkyrootWeapon.entityKilledBySkyroot(livingEntity, damageSource);
    }

    @SubscribeEvent
    public static void doSkyrootDoubleDrops(LivingDropsEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Collection<ItemEntity> drops = event.getDrops();
        SkyrootWeapon.entityDropsBySkyroot(livingEntity, damageSource, drops);
    }

    @SubscribeEvent
    public static void onArrowHit(ProjectileImpactEvent event) {
        HitResult hitResult = event.getRayTraceResult();
        Projectile projectile = event.getProjectile();
        AbilityHooks.WeaponHooks.phoenixArrowHit(hitResult, projectile);
    }

    @SubscribeEvent
    public static void onDartHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        AbilityHooks.WeaponHooks.stickDart(livingEntity, damageSource);
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        LightningBolt lightningBolt = event.getLightning();
        AbilityHooks.WeaponHooks.lightningSwordTracking(event, entity, lightningBolt); //Has to take event due to the event being canceled within a lambda.
    }

    @SubscribeEvent
    public static void onHolystoneAbility(LivingHurtEvent event) {
        LivingEntity target = event.getEntityLiving();
        DamageSource source = event.getSource();
        HolystoneWeapon.dropAmbrosium(target, source);
    }
}
