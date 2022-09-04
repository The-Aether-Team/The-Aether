package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.api.WorldDisplayHelper;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public class EntityRenderHooks {
    public static boolean shouldRenderPlayer() {
        return AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null;
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
