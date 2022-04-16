package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.common.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherPlayerListener {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        CapabilityHooks.AetherPlayerHooks.login(player);
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();
        CapabilityHooks.AetherPlayerHooks.logout(player);
    }

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        CapabilityHooks.AetherPlayerHooks.update(livingEntity);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getPlayer();
        boolean wasDeath = event.isWasDeath();
        CapabilityHooks.AetherPlayerHooks.clone(originalPlayer, newPlayer, wasDeath);
    }
}
