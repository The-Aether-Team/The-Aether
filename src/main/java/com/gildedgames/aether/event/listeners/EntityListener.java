package com.gildedgames.aether.event.listeners;

import com.gildedgames.aether.event.hooks.EntityHooks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber
public class EntityListener {
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        EntityHooks.addGoals(entity);
    }

    @SubscribeEvent
    public static void onMountEntity(EntityMountEvent event) {
        Entity riderEntity = event.getEntityMounting();
        Entity mountEntity = event.getEntityBeingMounted();
        boolean isDismounting = event.isDismounting();
        event.setCanceled(EntityHooks.dismountPrevention(riderEntity, mountEntity, isDismounting));
    }

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

    @SubscribeEvent
    public static void onProjectileHitEntity(ProjectileImpactEvent event) {
        Entity projectileEntity = event.getEntity();
        HitResult rayTraceResult = event.getRayTraceResult();
        event.setCanceled(EntityHooks.preventSliderHooked(projectileEntity, rayTraceResult));
    }
}
