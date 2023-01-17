package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.api.WorldDisplayHelper;
import com.gildedgames.aether.mixin.mixins.client.accessor.EntityRendererAccessor;
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
        EntityRendererAccessor entityRendererAccessor = (EntityRendererAccessor) renderer;
        if (flag) {
            entityRendererAccessor.aether$setShadowRadius(0.0F);
        } else {
            if (entityRendererAccessor.aether$getShadowRadius() == 0.0F) {
                entityRendererAccessor.aether$setShadowRadius(0.5F);
            }
        }
    }
}
