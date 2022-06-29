package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.client.AetherSkyRenderInfo;
import com.gildedgames.aether.world.AetherDimensions;
import com.gildedgames.aether.capability.time.AetherTime;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.material.FogType;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionClientHooks {
    public static Float renderNearFog(Camera camera, FogRenderer.FogMode mode, float far) {
        if (camera.getEntity().level instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof AetherSkyRenderInfo) {
                FogType fluidState = camera.getFluidInCamera();
                if (mode == FogRenderer.FogMode.FOG_TERRAIN && fluidState == FogType.NONE) {
                    return far / 2.0F;
                }
            }
        }
        return null;
    }

    public static Triple<Float, Float, Float> renderFogColors(Camera camera, float red, float green, float blue) {
        if (camera.getEntity().level instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof AetherSkyRenderInfo) {
                ClientLevel.ClientLevelData worldInfo = clientLevel.getLevelData();
                double d0 = (camera.getPosition().y - (double) clientLevel.getMinBuildHeight()) * worldInfo.getClearColorScale();
                FogType fluidState = camera.getFluidInCamera();
                if (d0 < 1.0D && fluidState != FogType.LAVA) {
                    if (d0 < 0.0D) {
                        d0 = 0.0D;
                    }
                    d0 = d0 * d0;
                    if (d0 != 0.0D) {
                        return Triple.of((float) ((double) red / d0), (float) ((double) green / d0), (float) ((double) blue / d0));
                    }
                }
            }
        }
        return Triple.of(null, null, null);
    }

    /**
     * Ticks time in clientside Aether levels.
     */
    public static void tickTime() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && !Minecraft.getInstance().isPaused() && level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            if (level.levelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                AetherTime.get(level).ifPresent(cap -> level.setDayTime(cap.tickTime(level) - 1)); // The client always increments time by 1 every tick.
            }
        }
    }
}
