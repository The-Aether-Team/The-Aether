package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.EntityHooks;
import dev.emi.trinkets.api.event.TrinketDropCallback;
import io.github.fabricators_of_create.porting_lib.entity.events.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class EntityListener {
    /**
     * @see EntityHooks#addGoals(Entity)
     * @see EntityHooks#canMobSpawnWithAccessories(Entity)
     */
    public static boolean onEntityJoin(Entity entity, Level world, boolean loadedFromDisk) {
        EntityHooks.addGoals(entity);
        return false;
    }

    /**
     * @see EntityHooks#dismountPrevention(Entity, Entity, boolean)
     */
    public static boolean onMountEntity(Entity mountEntity, Entity riderEntity, boolean mounting) {
        return !EntityHooks.dismountPrevention(riderEntity, mountEntity, !mounting);
    }

    /**
     * @see EntityHooks#launchMount(Player)
     */
    public static void onRiderTick(Player player) {
        EntityHooks.launchMount(player);
    }

//    /** TODO: PORT
//     * @see EntityHooks#skyrootBucketMilking(Entity, Player, InteractionHand)
//     * @see EntityHooks#pickupBucketable(Entity, Player, InteractionHand)
//     * @see EntityHooks#interactWithArmorStand(Entity, Player, ItemStack, Vec3, InteractionHand)
//     */
//    @SubscribeEvent
//    public static void onInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event) {
//        Entity targetEntity = event.getTarget();
//        Player player = event.getEntity();
//        ItemStack itemStack = event.getItemStack();
//        Vec3 position = event.getLocalPos();
//        InteractionHand interactionHand = event.getHand();
//        EntityHooks.skyrootBucketMilking(targetEntity, player, interactionHand);
//        Optional<InteractionResult> result = EntityHooks.pickupBucketable(targetEntity, player, interactionHand);
//        if (result.isEmpty()) {
//            result = EntityHooks.interactWithArmorStand(targetEntity, player, itemStack, position, interactionHand);
//        }
//        result.ifPresent(event::setCancellationResult);
//        event.setCanceled(result.isPresent());
//    }

    /**
     * @see EntityHooks#preventEntityHooked(Entity, HitResult)
     */
    public static void onProjectileHitEntity(ProjectileImpactEvent event) {
        Entity projectileEntity = event.getEntity();
        HitResult rayTraceResult = event.getRayTraceResult();
        event.setCanceled(EntityHooks.preventEntityHooked(projectileEntity, rayTraceResult));
    }

    /**
     * @see EntityHooks#preventSliderShieldBlock(DamageSource)
     */
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (!event.isCanceled()) {
            event.setCanceled(EntityHooks.preventSliderShieldBlock(event.getDamageSource()));
        }
    }

    /**
     * @see EntityHooks#lightningHitKeys(Entity)
     */
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        event.setCanceled(EntityHooks.lightningHitKeys(entity));
    }

    /**
     * @see EntityHooks#trackDrops(LivingEntity, Collection)
     */
    public static boolean onPlayerDrops(LivingEntity entity, DamageSource source, Collection<ItemEntity> itemDrops, int lootingLevel, boolean recentlyHit) {
        EntityHooks.trackDrops(entity, itemDrops);
        return false;
    }

    /** TODO: PORT
     * @see EntityHooks#handleEntityCurioDrops(LivingEntity, Collection, boolean, int)
     */
//    @SubscribeEvent
//    public static void onCurioDrops(CurioDropsEvent event) {
//        LivingEntity entity = event.getEntity();
//        Collection<ItemEntity> itemDrops = event.getDrops();
//        Collection<ItemEntity> itemDropsCopy = new ArrayList<>(itemDrops);
//        boolean recentlyHit = event.isRecentlyHit();
//        int looting = event.getLootingLevel();
//        itemDrops.clear();
//        itemDrops.addAll(EntityHooks.handleEntityCurioDrops(entity, itemDropsCopy, recentlyHit, looting));
//    }

    /**
     * @see EntityHooks#modifyExperience(LivingEntity, int)
     */
    public static int onDropExperience(int experience, Player attackingPlayer, LivingEntity livingEntity) {
        int newExperience = EntityHooks.modifyExperience(livingEntity, experience);
        return newExperience;
    }

//    /** TODO: PORT
//     * @see EntityHooks#preventInebriation(LivingEntity, MobEffectInstance)
//     */
//    @SubscribeEvent
//    public static void onEffectApply(MobEffectEvent.Applicable event) {
//        LivingEntity livingEntity = event.getEntity();
//        MobEffectInstance effectInstance = event.getEffectInstance();
//        if (EntityHooks.preventInebriation(livingEntity, effectInstance)) {
//            event.setResult(Event.Result.DENY);
//        }
//    }

    public static void init() {
        EntityEvents.ON_JOIN_WORLD.register(EntityListener::onEntityJoin);
        EntityMountEvents.registerForBoth(EntityListener::onMountEntity);
        PlayerTickEvents.START.register(EntityListener::onRiderTick);
        PlayerTickEvents.END.register(EntityListener::onRiderTick);
        ProjectileImpactEvent.PROJECTILE_IMPACT.register(EntityListener::onProjectileHitEntity);
        ShieldBlockEvent.EVENT.register(EntityListener::onShieldBlock);
        EntityStruckByLightningEvent.ENTITY_STRUCK_BY_LIGHTING.register(EntityListener::onLightningStrike);
        LivingEntityEvents.DROPS.register(EntityListener::onPlayerDrops);
//        TrinketDropCallback.EVENT.register();
        LivingEntityEvents.EXPERIENCE_DROP.register(EntityListener::onDropExperience);
    }
}
