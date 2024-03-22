package com.aetherteam.aether.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherTimeAttachment;
import com.aetherteam.aether.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Listener for Forge events to handle syncing the data for {@link AetherTimeAttachment}.
 */
@Mod.EventBusSubscriber(modid = Aether.MODID)
public class AetherTimeListener {
    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherTimeHooks.login(player);
    }

    @SubscribeEvent
    public static void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherTimeHooks.changeDimension(player);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherTimeHooks.respawn(player);
    }
}
