package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.gildedgames.aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class LevelClientHooks {
    public static void renderMenuWithWorld(RenderLevelStageEvent.Stage stage, Minecraft minecraft) {
        if (stage == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            if (AetherConfig.CLIENT.enable_world_preview.get()) {
                if (AetherWorldDisplayHelper.loadedSummary != null) {
                    if (minecraft.screen == null) {
                        setupMenu(minecraft);
                    } else {
                        LocalPlayer player = minecraft.player;
                        if (player != null) {
                            player.setXRot(0);
                            player.setYRot(player.getYRot() + 0.02F);
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
    }

    public static void setupMenu(Minecraft minecraft) {
        AetherWorldDisplayHelper.setupLevelForDisplay();
        minecraft.forceSetScreen(GuiHooks.getMenu());
    }

    public static boolean shouldRenderPlayer() {
        return AetherConfig.CLIENT.enable_world_preview.get() && AetherWorldDisplayHelper.loadedLevel != null && AetherWorldDisplayHelper.loadedSummary != null;
    }

    public static void adjustShadow(PlayerRenderer renderer) {
        if (shouldRenderPlayer()) {
            renderer.shadowRadius = 0.0F;
        } else {
            if (renderer.shadowRadius == 0.0F) {
                renderer.shadowRadius = 0.5F;
            }
        }
    }
}
