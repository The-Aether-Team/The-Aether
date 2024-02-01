package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.perk.data.ServerPerkData;
import com.aetherteam.nitrogen.api.users.UserData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PerkHooks {
    /**
     * Removes perks from the player if they don't have a User stored with the server.
     * @param player The {@link Player}.
     * @see com.aetherteam.aether.event.listeners.PerkListener#playerLoggedIn(PlayerEvent.PlayerLoggedInEvent)
     */
    public static void refreshPerks(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (!UserData.Server.getStoredUsers().containsKey(serverPlayer.getGameProfile().getId())) {
                ServerPerkData.MOA_SKIN_INSTANCE.removePerk(serverPlayer.getServer(), serverPlayer.getGameProfile().getId());
                ServerPerkData.HALO_INSTANCE.removePerk(serverPlayer.getServer(), serverPlayer.getGameProfile().getId());
                ServerPerkData.DEVELOPER_GLOW_INSTANCE.removePerk(serverPlayer.getServer(), serverPlayer.getGameProfile().getId());
            }
        }
    }
}
