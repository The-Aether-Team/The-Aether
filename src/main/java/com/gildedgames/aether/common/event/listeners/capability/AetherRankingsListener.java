package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.common.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherRankingsListener {
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        CapabilityHooks.AetherRankingsHooks.join(entity);
    }

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        CapabilityHooks.AetherRankingsHooks.update(livingEntity);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getPlayer();
        CapabilityHooks.AetherRankingsHooks.clone(originalPlayer, newPlayer);
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getPlayer();
        CapabilityHooks.AetherRankingsHooks.changeDimension(player);
    }
}
