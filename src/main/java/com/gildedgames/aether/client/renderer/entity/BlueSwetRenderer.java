package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.monster.Swet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BlueSwetRenderer extends SwetRenderer {
    private static final ResourceLocation BLUE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/swet/swet_blue.png");

    public BlueSwetRenderer(EntityRendererProvider.Context renderer) {
        super(renderer);
    }

    @Override
    public ResourceLocation getTextureLocation(Swet pEntity) {
        return BLUE_TEXTURE;
    }
}
