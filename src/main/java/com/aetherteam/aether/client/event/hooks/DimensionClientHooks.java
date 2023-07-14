package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.capability.time.AetherTime;
import com.aetherteam.aether.client.renderer.level.AetherSkyRenderEffects;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionClientHooks {
    public static Float renderNearFog(Camera camera, FogRenderer.FogMode mode, float far) {
        if (camera.getEntity().level instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof AetherSkyRenderEffects) {
                FogType fluidState = camera.getFluidInCamera();
                if (mode == FogRenderer.FogMode.FOG_TERRAIN && fluidState == FogType.NONE) {
                    return far / 2.0F;
                }
            }
        }
        return null;
    }

    public static Float reduceLavaFog(Camera camera, float nearDistance) {
        if (camera.getEntity().level instanceof ClientLevel) {
            if (camera.getEntity() instanceof LivingEntity livingEntity && EquipmentUtil.hasFullPhoenixSet(livingEntity)) {
                FogType fluidState = camera.getFluidInCamera();
                if (fluidState == FogType.LAVA) {
                    return nearDistance * 5.0F;
                }
            }
        }
        return null;
    }

    public static Triple<Float, Float, Float> renderFogColors(Camera camera, float red, float green, float blue) {
        if (camera.getEntity().level instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof AetherSkyRenderEffects) {
                ClientLevel.ClientLevelData worldInfo = clientLevel.getLevelData();
                double d0 = (camera.getPosition().y - (double) clientLevel.getMinBuildHeight()) * worldInfo.getClearColorScale();
                FogType fluidState = camera.getFluidInCamera();
                if (d0 < 1.0 && fluidState != FogType.LAVA) {
                    if (d0 < 0.0) {
                        d0 = 0.0;
                    }
                    d0 = d0 * d0;
                    if (d0 != 0.0) {
                        return Triple.of((float) ((double) red / d0), (float) ((double) green / d0), (float) ((double) blue / d0));
                    }
                }
            }
        }
        return Triple.of(null, null, null);
    }

    public static Triple<Float, Float, Float> adjustWeatherFogColors(Camera camera, float red, float green, float blue) {
        if (camera.getEntity().level instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof AetherSkyRenderEffects) {
                FogType fluidState = camera.getFluidInCamera();
                if (fluidState == FogType.NONE) {
                    Vec3 defaultSky = Vec3.fromRGB24(clientLevel.getBiome(camera.getBlockPosition()).get().getModifiedSpecialEffects().getFogColor());
                    if (clientLevel.rainLevel > 0.0) {
                        float f14 = 1.0F + clientLevel.rainLevel * 0.8F;
                        float f17 = 1.0F + clientLevel.rainLevel * 0.56F;
                        red *= f14;
                        green *= f14;
                        blue *= f17;
                    }
                    if (clientLevel.thunderLevel > 0.0) {
                        float f18 = 1.0F + clientLevel.thunderLevel * 0.66F;
                        float f19 = 1.0F + clientLevel.thunderLevel * 0.76F;
                        red *= f18;
                        green *= f18;
                        blue *= f19;
                    }
                    red = (float) Math.min(red, defaultSky.x);
                    green = (float) Math.min(green, defaultSky.y);
                    blue = (float) Math.min(blue, defaultSky.z);
                    return Triple.of(red, green, blue);
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
            LevelAccessor levelAccessor = (LevelAccessor) level;
            if (levelAccessor.aether$getLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                AetherTime.get(level).ifPresent(cap -> level.setDayTime(cap.tickTime(level) - 1)); // The client always increments time by 1 every tick.
            }
        }
    }
}
