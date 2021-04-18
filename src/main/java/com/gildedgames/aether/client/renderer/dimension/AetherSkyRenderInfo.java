package com.gildedgames.aether.client.renderer.dimension;

import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nullable;

public class AetherSkyRenderInfo extends DimensionRenderInfo
{
    private ISkyRenderHandler skyRenderer = new AetherSkyRenderer();
    public AetherSkyRenderInfo() {
        super(-5.0F, true, DimensionRenderInfo.FogType.NORMAL, false, false);
    }

    @Override
    public Vector3d getBrightnessDependentFogColor(Vector3d color, float p_230494_2_) {
        return color.multiply((p_230494_2_ * 0.94F + 0.06F), (p_230494_2_ * 0.94F + 0.06F), (p_230494_2_ * 0.91F + 0.09F));
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }

    @Nullable
    @Override
    public ISkyRenderHandler getSkyRenderHandler() {
       return AetherConfig.CLIENT.disable_aether_skybox.get() ? null : skyRenderer;
    }
}
