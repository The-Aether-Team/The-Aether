package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.client.world.AetherSkyRenderInfo;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.material.FogType;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionClientHooks {
    public static Triple<Float, Float, Float> renderFog(Camera camera, float red, float green, float blue) {
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
}
