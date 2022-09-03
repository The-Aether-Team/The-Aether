package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.api.WorldDisplayHelper;
import com.gildedgames.aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class LevelClientHooks {
    public static void renderMenuWithWorld(RenderLevelStageEvent.Stage stage, Minecraft minecraft) {
        if (stage == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            if (AetherConfig.CLIENT.enable_world_preview.get()) {
                if (WorldDisplayHelper.loadedSummary != null) {
                    if (minecraft.screen == null || minecraft.screen instanceof PauseScreen) {
                        setupMenu(minecraft);
                    }
                }
            } else {
                WorldDisplayHelper.loadedLevel = null;
                WorldDisplayHelper.loadedSummary = null;
            }
        }
    }

    public static void setupMenu(Minecraft minecraft) {
        WorldDisplayHelper.setupLevelForDisplay();
        minecraft.forceSetScreen(GuiHooks.getMenu());
    }

    public static boolean shouldHidePlayer() {
        return AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null;
    }

    public static boolean shouldHideEntity(Entity entity) {
        return AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null
                && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle().is(entity);
    }

    public static void adjustShadow(EntityRenderer<?> renderer, boolean flag) {
        if (flag) {
            renderer.shadowRadius = 0.0F;
        } else {
            if (renderer.shadowRadius == 0.0F) {
                renderer.shadowRadius = 0.5F;
            }
        }
    }

    private static Float prevRotation = null;

    public static Float angleCamera(float prevYaw) {
        if (AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null && Minecraft.getInstance().player != null) {
            if (prevRotation == null) {
                prevRotation = prevYaw;
            }
            float newYaw = prevRotation + 0.01F;
            prevRotation = newYaw;
            return newYaw;
        }
        prevRotation = null;
        return null;
    }
}
