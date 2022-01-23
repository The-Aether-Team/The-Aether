package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.monster.Swet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GoldenSwetRenderer extends SwetRenderer {
    private static final ResourceLocation GOLDEN_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/swet/swet_golden.png");

    public GoldenSwetRenderer(EntityRendererProvider.Context renderer) {
        super(renderer);
    }

    @Override
    public ResourceLocation getTextureLocation(Swet pEntity) {
        return GOLDEN_TEXTURE;
    }
}
