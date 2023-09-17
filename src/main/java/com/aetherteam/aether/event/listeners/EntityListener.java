package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.EntityHooks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.CurioDropsEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class EntityListener {
    /**
     * @see EntityHooks#addGoals(Entity)
     * @see EntityHooks#canMobSpawnWithAccessories(Entity)
     */
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        EntityHooks.addGoals(entity);
    }

    /**
     * @see EntityHooks#dismountPrevention(Entity, Entity, boolean)
     */
    @SubscribeEvent
    public static void onMountEntity(EntityMountEvent event) {
        Entity riderEntity = event.getEntityMounting();
        Entity mountEntity = event.getEntityBeingMounted();
        boolean isDismounting = event.isDismounting();
        event.setCanceled(EntityHooks.dismountPrevention(riderEntity, mountEntity, isDismounting));
    }

    /**
     * @see EntityHooks#launchMount(Player)
     */
    @SubscribeEvent
    public static void onRiderTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        EntityHooks.launchMount(player);
    }

    /**
     * @see EntityHooks#skyrootBucketMilking(Entity, Player, InteractionHand)
     * @see EntityHooks#pickupBucketable(Entity, Player, InteractionHand)
     * @see EntityHooks#interactWithArmorStand(Entity, Player, ItemStack, Vec3, InteractionHand)
     */
    @SubscribeEvent
    public static void onInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity targetEntity = event.getTarget();
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        Vec3 position = event.getLocalPos();
        InteractionHand interactionHand = event.getHand();
        EntityHooks.skyrootBucketMilking(targetEntity, player, interactionHand);
        Optional<InteractionResult> result = EntityHooks.pickupBucketable(targetEntity, player, interactionHand);
        if (result.isEmpty()) {
            result = EntityHooks.interactWithArmorStand(targetEntity, player, itemStack, position, interactionHand);
        }
        result.ifPresent(event::setCancellationResult);
        event.setCanceled(result.isPresent());
    }

    /**
     * @see EntityHooks#preventEntityHooked(Entity, HitResult)
     */
    @SubscribeEvent
    public static void onProjectileHitEntity(ProjectileImpactEvent event) {
        Entity projectileEntity = event.getEntity();
        HitResult rayTraceResult = event.getRayTraceResult();
        event.setCanceled(EntityHooks.preventEntityHooked(projectileEntity, rayTraceResult));
    }

    /**
     * @see EntityHooks#preventSliderShieldBlock(DamageSource)
     */
    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (!event.isCanceled()) {
            event.setCanceled(EntityHooks.preventSliderShieldBlock(event.getDamageSource()));
        }
    }

    /**
     * @see EntityHooks#lightningHitKeys(Entity)
     */
    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        event.setCanceled(EntityHooks.lightningHitKeys(entity));
    }

    /**
     * @see EntityHooks#trackDrops(LivingEntity, Collection)
     */
    @SubscribeEvent
    public static void onPlayerDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Collection<ItemEntity> itemDrops = event.getDrops();
        EntityHooks.trackDrops(entity, itemDrops);
    }

    /**
     * @see EntityHooks#handleEntityCurioDrops(LivingEntity, Collection, boolean, int)
     */
    @SubscribeEvent
    public static void onCurioDrops(CurioDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Collection<ItemEntity> itemDrops = event.getDrops();
        Collection<ItemEntity> itemDropsCopy = new ArrayList<>(itemDrops);
        boolean recentlyHit = event.isRecentlyHit();
        int looting = event.getLootingLevel();
        itemDrops.clear();
        itemDrops.addAll(EntityHooks.handleEntityCurioDrops(entity, itemDropsCopy, recentlyHit, looting));
    }

    /**
     * @see EntityHooks#modifyExperience(LivingEntity, int)
     */
    @SubscribeEvent
    public static void onDropExperience(LivingExperienceDropEvent event) {
        LivingEntity livingEntity = event.getEntity();
        int experience = event.getDroppedExperience();
        int newExperience = EntityHooks.modifyExperience(livingEntity, experience);
        event.setDroppedExperience(newExperience);
    }

    /**
     * @see EntityHooks#preventInebriation(LivingEntity, MobEffectInstance)
     */
    @SubscribeEvent
    public static void onEffectApply(MobEffectEvent.Applicable event) {
        LivingEntity livingEntity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (EntityHooks.preventInebriation(livingEntity, effectInstance)) {
            event.setResult(Event.Result.DENY);
        }
    }
}
