package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.entity.monster.Swet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class GoldenSwetRenderer extends SwetRenderer {
    private static final ResourceLocation GOLDEN_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/swet/swet_golden.png");

    public GoldenSwetRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Swet swet) {
        return GOLDEN_TEXTURE;
    }
}
