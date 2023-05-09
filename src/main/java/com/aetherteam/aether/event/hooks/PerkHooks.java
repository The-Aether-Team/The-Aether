package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.perk.data.ServerDeveloperGlowPerkData;
import com.aetherteam.aether.perk.data.ServerHaloPerkData;
import com.aetherteam.aether.perk.data.ServerMoaSkinPerkData;
import com.aetherteam.nitrogen.api.users.UserData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PerkHooks {
    public static void refreshPerks(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (!UserData.Server.getStoredUsers().containsKey(serverPlayer.getGameProfile().getId())) {
                ServerMoaSkinPerkData.INSTANCE.removePerk(serverPlayer.getServer(), serverPlayer.getGameProfile().getId());
                ServerHaloPerkData.INSTANCE.removePerk(serverPlayer.getServer(), serverPlayer.getGameProfile().getId());
                ServerDeveloperGlowPerkData.INSTANCE.removePerk(serverPlayer.getServer(), serverPlayer.getGameProfile().getId());
            }
        }
    }
}
