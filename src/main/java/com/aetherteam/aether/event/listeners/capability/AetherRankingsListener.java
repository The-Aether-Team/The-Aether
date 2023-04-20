package com.aetherteam.aether.event.listeners.capability;

import com.aetherteam.aether.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherRankingsListener {
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        CapabilityHooks.AetherRankingsHooks.join(entity);
    }

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        CapabilityHooks.AetherRankingsHooks.update(livingEntity);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        CapabilityHooks.AetherRankingsHooks.clone(originalPlayer, newPlayer);
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherRankingsHooks.changeDimension(player);
    }
}
