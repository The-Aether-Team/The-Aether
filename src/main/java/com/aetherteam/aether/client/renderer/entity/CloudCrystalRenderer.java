package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.CrystalModel;
import com.aetherteam.aether.client.renderer.entity.state.CrystalRenderState;
import com.aetherteam.aether.entity.projectile.crystal.AbstractCrystal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CloudCrystalRenderer<T extends AbstractCrystal> extends AbstractCrystalRenderer<T, CrystalRenderState> {
    private static final ResourceLocation ICE_CRYSTAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/projectile/crystals/ice_ball.png");

    public CloudCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalModel<>(context.bakeLayer(AetherModelLayers.CLOUD_CRYSTAL)));
    }

    @Override
    public CrystalRenderState createRenderState() {
        return new CrystalRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalRenderState crystal) {
        return ICE_CRYSTAL_TEXTURE;
    }
}
