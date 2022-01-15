package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.core.capability.interfaces.IAetherRankings;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherRankingsListener
{
    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            IAetherRankings.get(player).ifPresent(IAetherRankings::onUpdate);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        IAetherRankings original = IAetherRankings.get(event.getOriginal()).orElseThrow(
                () -> new IllegalStateException("Player " + event.getOriginal().getName().getContents() + " has no AetherRankings capability!"));
        IAetherRankings newPlayer = IAetherRankings.get(event.getPlayer()).orElseThrow(
                () -> new IllegalStateException("Player " + event.getPlayer().getName().getContents() + " has no AetherRankings capability!"));
        newPlayer.copyFrom(original);
        event.getOriginal().invalidateCaps();
    }
}
