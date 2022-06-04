package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.player.LocalPlayer;

public class LevelClientHooks {
    public static void renderMenuWithWorld(Minecraft minecraft) {
        if (AetherConfig.CLIENT.enable_world_preview.get()) {
            if (AetherWorldDisplayHelper.loadedSummary != null) {
                if (minecraft.screen == null) {
                    setupMenu(minecraft);
                } else {
                    LocalPlayer player = minecraft.player;
                    if (player != null) {
                        player.setXRot(0);
                        player.setYRot(player.getYRot() + 0.05F);
                    }
                    if (minecraft.screen instanceof PauseScreen) {
                        setupMenu(minecraft);
                    }
                }
            }
        } else {
            AetherWorldDisplayHelper.loadedLevel = null;
            AetherWorldDisplayHelper.loadedSummary = null;
        }
    }

    public static void setupMenu(Minecraft minecraft) {
        AetherWorldDisplayHelper.setupLevelForDisplay();
        minecraft.forceSetScreen(GuiHooks.getMenu());
    }
}
