package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.EntityHooks;
import com.aetherteam.aetherfabric.events.*;
import io.wispforest.accessories.api.events.OnDeathCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class EntityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> onEntityJoin(entity));
        EntityEvents.ENTITY_MOUNT.register(EntityListener::onMountEntity);
        PlayerTickEvents.AFTER.register(EntityListener::onRiderTick);
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> onInteractWithEntity(entity, player, hand, hitResult));
        ProjectileEvents.ON_IMPACT.register(EntityListener::onProjectileHitEntity);
        LivingEntityEvents.ON_SHIELD_BLOCK.register(EntityListener::onShieldBlock);
        EntityEvents.STRUCK_BY_LIGHTNING.register(EntityListener::onLightningStrike);
        LivingEntityEvents.ON_EXPERIENCE_DROP.register((entity, attackingPlayer, helper) -> onDropExperience(entity, helper));
        LivingEntityEvents.ON_EFFECT.register((entity, instance, result) -> EntityListener.onEffectApply(entity, instance));
        // SlimeMixin.aetherFabric$dontSplitForSwets -> EntityHooks.preventSplit
        //bus.addListener(EntityListener::onLoadPlayerFile);

        OnDeathCallback.EVENT.register((currentState, entity, capability, damageSource, droppedStacks) -> {
            List<ItemStack> droppedStacksCopy = new ArrayList<>(droppedStacks);
            boolean recentlyHit = entity.hurtMarked;
            int looting = EnchantmentHelper.getEnchantmentLevel(entity.level().registryAccess().aetherFabric$holderOrThrow(Enchantments.LOOTING), entity);
            droppedStacks.clear();
            droppedStacks.addAll(EntityHooks.handleEntityAccessoryDrops(entity, droppedStacksCopy, recentlyHit, looting));
            return TriState.DEFAULT;
        });
    }

    /**
     * @see EntityHooks#addGoals(Entity)
     * @see EntityHooks#canMobSpawnWithAccessories(Entity)
     */
    public static void onEntityJoin(Entity entity) {
        EntityHooks.addGoals(entity);
    }

    /**
     * @see EntityHooks#dismountPrevention(Entity, Entity, boolean)
     */
    public static void onMountEntity(Entity riderEntity, Entity mountEntity, boolean isDismounting, CancellableCallback callback) {
        callback.setCanceled(EntityHooks.dismountPrevention(riderEntity, mountEntity, isDismounting));
    }

    /**
     * @see EntityHooks#launchMount(Player)
     */
    public static void onRiderTick(Player player) {
        EntityHooks.launchMount(player);
    }

    /**
     * @see EntityHooks#skyrootBucketMilking(Entity, Player, InteractionHand)
     * @see EntityHooks#pickupBucketable(Entity, Player, InteractionHand)
     * @see EntityHooks#interactWithArmorStand(Entity, Player, ItemStack, Vec3, InteractionHand)
     */
    public static InteractionResult onInteractWithEntity(Entity targetEntity, Player player, InteractionHand interactionHand, @Nullable EntityHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Vec3 position;

        if (hitResult == null) {
            WorldBorder border = player.level().getWorldBorder();
            AABB bounds = player.getBoundingBox().expandTowards(targetEntity.position()).inflate(1.0);
            AABB clampedBounds = AABB.encapsulatingFullBlocks(border.clampToBounds(bounds.getMinPosition()), border.clampToBounds(bounds.getMaxPosition()));
            hitResult = ProjectileUtil.getEntityHitResult(player, player.getEyePosition(), targetEntity.position(), clampedBounds, entity -> entity == targetEntity, player.position().distanceTo(targetEntity.position()) + 1);
        }

        if (hitResult != null) {
            position = hitResult.getLocation().subtract(targetEntity.position());
        } else {
            position = targetEntity.position();
        }

        EntityHooks.skyrootBucketMilking(targetEntity, player, interactionHand);
        Optional<InteractionResult> result = EntityHooks.pickupBucketable(targetEntity, player, interactionHand);
        if (result.isEmpty()) {
            result = EntityHooks.interactWithArmorStand(targetEntity, player, itemStack, position, interactionHand);
        }
        return result.orElse(InteractionResult.PASS);
    }

    /**
     * @see EntityHooks#preventEntityHooked(Entity, HitResult)
     */
    public static void onProjectileHitEntity(Entity projectileEntity, HitResult rayTraceResult, CancellableCallback callback) {
        if (EntityHooks.preventEntityHooked(projectileEntity, rayTraceResult)) callback.setCanceled(true);
    }

    /**
     * @see EntityHooks#preventSliderShieldBlock(DamageSource)
     */
    public static void onShieldBlock(DamageSource damageSource, CancellableCallback callback) {
        if (!callback.isCanceled()) {
            callback.setCanceled(EntityHooks.preventSliderShieldBlock(damageSource));
        }
    }

    /**
     * @see EntityHooks#lightningHitKeys(Entity)
     */
    public static void onLightningStrike(Entity entity, LightningBolt lightningBolt, CancellableCallback callback) {
        if (EntityHooks.lightningHitKeys(entity) || EntityHooks.thunderCrystalHitItems(entity, lightningBolt)) {
            callback.setCanceled(true);
        }
    }

    /**
     * @see EntityHooks#modifyExperience(LivingEntity, int)
     */
    public static void onDropExperience(LivingEntity livingEntity, ExperienceDropHelper event) {
        int experience = event.getDroppedExperience();
        int newExperience = EntityHooks.modifyExperience(livingEntity, experience);
        event.setDroppedExperience(newExperience);
    }

    /**
     * @see EntityHooks#preventInebriation(LivingEntity, MobEffectInstance)
     */
    public static TriState onEffectApply(LivingEntity livingEntity, MobEffectInstance effectInstance) {
        if (EntityHooks.preventInebriation(livingEntity, effectInstance)) {
            return TriState.FALSE;
        }

        return null;
    }

//    /**
//     * @see EntityHooks#preventSplit(Mob)
//     */
//    public static void onEntitySplit(Mob mob, CancellableCallback callback) {
//        if (EntityHooks.preventSplit(mob)) {
//            callback.setCanceled(true);
//        }
//    }
}
