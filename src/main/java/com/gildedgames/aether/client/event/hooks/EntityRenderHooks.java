package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.api.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;

public class EntityRenderHooks {
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
}
