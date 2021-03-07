package com.gildedgames.aether.common.event.handlers;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.player.IAetherPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherCapabilityHandler
{
    //TODO: Figure out how to @SuppressWarnings away the NullPointerException issue since it isn't actually an issue I believe, considering the .orElseThrow().
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        IAetherPlayer original = event.getOriginal().getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY).orElseThrow(() -> new IllegalStateException("Player " + event.getOriginal().getName().getUnformattedComponentText() + " has no AetherPlayer capability!"));
        IAetherPlayer newPlayer = event.getPlayer().getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY).orElseThrow(() -> new IllegalStateException("Player " + event.getOriginal().getName().getUnformattedComponentText() + " has no AetherPlayer capability!"));

        newPlayer.copyFrom(original);
    }
}
