package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.SwetOuterLayer;
import com.aetherteam.aether.client.renderer.entity.state.SwetRenderState;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BlueSwetRenderer extends SwetRenderer {
    private static final ResourceLocation BLUE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/swet/swet_blue.png");

    public BlueSwetRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new SwetOuterLayer(this, new SlimeModel(context.bakeLayer(AetherModelLayers.SWET_OUTER)), BLUE_TEXTURE));

    }

    @Override
    public ResourceLocation getTextureLocation(SwetRenderState swet) {
        return BLUE_TEXTURE;
    }
}
