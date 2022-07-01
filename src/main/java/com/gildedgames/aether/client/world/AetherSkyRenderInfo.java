package com.gildedgames.aether.client.world;

import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nullable;

public class AetherSkyRenderInfo extends DimensionSpecialEffects
{
    private ISkyRenderHandler skyRenderer;
    public AetherSkyRenderInfo() {
        super(-5.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float p_230494_2_) {
        return color.multiply((p_230494_2_ * 0.94F + 0.06F), (p_230494_2_ * 0.94F + 0.06F), (p_230494_2_ * 0.91F + 0.09F));
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }

    @Nullable
    @Override
    public ISkyRenderHandler getSkyRenderHandler() {
        if (this.skyRenderer == null) skyRenderer = new AetherSkyRenderer();
       return AetherConfig.CLIENT.disable_aether_skybox.get() ? null : skyRenderer;
    }
}
