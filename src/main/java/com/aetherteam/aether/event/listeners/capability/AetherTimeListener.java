package com.aetherteam.aether.event.listeners.capability;

import com.aetherteam.aether.event.hooks.CapabilityHooks;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * Listener for events to handle syncing the data for {@link com.aetherteam.aether.capability.time.AetherTimeCapability}.
 */
public class AetherTimeListener {
    public static void onLogin(Player player) {
        CapabilityHooks.AetherTimeHooks.login(player);
    }

    public static void onChangeDimension(ServerPlayer player, ServerLevel origin, ServerLevel destination) {
        CapabilityHooks.AetherTimeHooks.changeDimension(player);
    }

    public static void onPlayerRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
        CapabilityHooks.AetherTimeHooks.respawn(newPlayer);
    }

    public static void init() {
        PlayerEvents.LOGGED_IN.register(AetherTimeListener::onLogin);
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(AetherTimeListener::onChangeDimension);
        ServerPlayerEvents.AFTER_RESPAWN.register(AetherTimeListener::onPlayerRespawn);
    }
}
