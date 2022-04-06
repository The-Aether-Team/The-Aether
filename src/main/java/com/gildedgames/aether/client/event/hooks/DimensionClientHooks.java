package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.client.world.AetherSkyRenderInfo;
import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionClientHooks {
    public static boolean playerLeavingAether;
    public static boolean displayAetherTravel;

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

    public static void dimensionTravel(Entity entity, ResourceKey<Level> dimension) {
        // The level passed into shouldReturnPlayerToOverworld() is the dimension the player is leaving
        //  Meaning: We display the Descending GUI text to the player if they're about to leave a dimension that returns them to the OW
        if (entity.level.dimension() == AetherDimensions.AETHER_LEVEL && dimension == Level.OVERWORLD) {
            playerLeavingAether = true;
            displayAetherTravel = true;
        } else if (entity.level.dimension() == Level.OVERWORLD && dimension == AetherDimensions.AETHER_LEVEL) {
            playerLeavingAether = false;
            displayAetherTravel = true;
        } else {
            displayAetherTravel = false;
        }
    }

    public static void disableAetherTravelDisplay() {
        displayAetherTravel = false;
    }
}
