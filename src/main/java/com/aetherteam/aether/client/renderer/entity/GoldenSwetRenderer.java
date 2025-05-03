package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.SwetOuterLayer;
import com.aetherteam.aether.client.renderer.entity.state.SwetRenderState;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GoldenSwetRenderer extends SwetRenderer {
    private static final ResourceLocation GOLDEN_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/swet/swet_golden.png");

    public GoldenSwetRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new SwetOuterLayer(this, new SlimeModel(context.bakeLayer(AetherModelLayers.SWET_OUTER)), GOLDEN_TEXTURE));
    }

    @Override
    public ResourceLocation getTextureLocation(SwetRenderState swet) {
        return GOLDEN_TEXTURE;
    }
}
