package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.entity.projectile.PoisonNeedle;
import com.gildedgames.aether.common.entity.projectile.dart.EnchantedDart;
import com.gildedgames.aether.common.entity.projectile.dart.GoldenDart;
import com.gildedgames.aether.common.entity.projectile.dart.PoisonDart;
import com.gildedgames.aether.common.item.combat.abilities.HolystoneWeapon;
import com.gildedgames.aether.common.item.combat.abilities.SkyrootWeapon;
import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.capability.lightning.LightningTracker;
import com.gildedgames.aether.core.capability.arrow.PhoenixArrow;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
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
        if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY && event.getProjectile() instanceof AbstractArrow) {
            Entity impactedEntity = ((EntityHitResult) event.getRayTraceResult()).getEntity();
            PhoenixArrow.get((AbstractArrow) event.getProjectile()).ifPresent(phoenixArrow -> {
                if (phoenixArrow.isPhoenixArrow() && phoenixArrow.getFireTime() > 0) {
                    impactedEntity.setSecondsOnFire(phoenixArrow.getFireTime());
                }
            });
        }
    }

    @SubscribeEvent
    public static void onDartHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player playerEntity = (Player) event.getEntityLiving();
            Entity source = event.getSource().getDirectEntity();
            if (source instanceof GoldenDart) {
                AetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setGoldenDartCount(aetherPlayer.getGoldenDartCount() + 1));
            } else if (source instanceof PoisonDart || source instanceof PoisonNeedle) {
                AetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setPoisonDartCount(aetherPlayer.getPoisonDartCount() + 1));
            } else if (source instanceof EnchantedDart) {
                AetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setEnchantedDartCount(aetherPlayer.getEnchantedDartCount() + 1));
            }
        }
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            LightningTracker.get(event.getLightning()).ifPresent(lightningTracker -> {
                if (lightningTracker.getOwner() != null) {
                    if (entity == lightningTracker.getOwner() || entity == lightningTracker.getOwner().getVehicle()) {
                        event.setCanceled(true);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onHolystoneAbility(LivingHurtEvent event) {
        LivingEntity target = event.getEntityLiving();
        DamageSource source = event.getSource();
        HolystoneWeapon.dropAmbrosium(target, source);
    }
}
