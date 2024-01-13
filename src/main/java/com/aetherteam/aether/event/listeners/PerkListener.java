package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.event.hooks.PerkHooks;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.minecraft.world.entity.player.Player;

public class PerkListener {
    /**
     * @see PerkHooks#refreshPerks(Player)
     */
    public static void playerLoggedIn(Player player) {
        PerkHooks.refreshPerks(player);
    }

    public static void init() {
        PlayerEvents.LOGGED_IN.register(PerkListener::playerLoggedIn);
    }
}
