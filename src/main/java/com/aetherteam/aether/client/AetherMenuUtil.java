package com.aetherteam.aether.client;

import com.aetherteam.aether.api.AetherMenus;
import com.aetherteam.nitrogen.NitrogenConfig;
import com.aetherteam.nitrogen.api.menu.Menus;

public class AetherMenuUtil {
    public static boolean isAetherMenu() {
        return NitrogenConfig.CLIENT.active_menu.get().equals(AetherMenus.THE_AETHER.get().toString()) || NitrogenConfig.CLIENT.active_menu.get().equals(AetherMenus.THE_AETHER_LEFT.get().toString());
    }

    public static boolean isMinecraftMenu() {
        return NitrogenConfig.CLIENT.active_menu.get().equals(Menus.MINECRAFT.get().toString()) || NitrogenConfig.CLIENT.active_menu.get().equals(AetherMenus.MINECRAFT_LEFT.get().toString());
    }
}
