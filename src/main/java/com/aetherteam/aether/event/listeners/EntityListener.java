package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.EntityHooks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class EntityListener {
    /**
     * @see EntityHooks#addGoals(Entity)
     */
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        EntityHooks.addGoals(entity);
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
     */
    @SubscribeEvent
    public static void onInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity targetEntity = event.getTarget();
        Player player = event.getEntity();
        InteractionHand interactionHand = event.getHand();
        EntityHooks.skyrootBucketMilking(targetEntity, player, interactionHand);
        Optional<InteractionResult> result = EntityHooks.pickupBucketable(targetEntity, player, interactionHand);
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
}
