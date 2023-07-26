package com.aetherteam.aether.client;

import com.aetherteam.aether.api.AetherMenus;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.Menus;

public class AetherMenuUtil {
    public static boolean isAetherMenu() {
        return CumulusConfig.CLIENT.active_menu.get().equals(AetherMenus.THE_AETHER.get().toString()) || CumulusConfig.CLIENT.active_menu.get().equals(AetherMenus.THE_AETHER_LEFT.get().toString());
    }

    public static boolean isMinecraftMenu() {
        return CumulusConfig.CLIENT.active_menu.get().equals(Menus.MINECRAFT.get().toString()) || CumulusConfig.CLIENT.active_menu.get().equals(AetherMenus.MINECRAFT_LEFT.get().toString());
    }
}
