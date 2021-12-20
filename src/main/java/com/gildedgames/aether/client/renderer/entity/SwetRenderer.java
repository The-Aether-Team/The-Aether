package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.monster.SwetEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeGelLayer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.util.ResourceLocation;

public class SwetRenderer extends MobRenderer<SwetEntity, SlimeModel<SwetEntity>> {
    private static final ResourceLocation BLUE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/swet/swet_blue.png");

    private static final ResourceLocation GOLDEN_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/swet/swet_golden.png");

    public SwetRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new SlimeModel<>(16), 0.3F);
        this.addLayer(new SlimeGelLayer<>(this));
    }

    @Override
    protected void scale(SwetEntity swet, MatrixStack matrixStackIn, float partialTickTime) {
        float height = swet.swetHeight;
        float width = swet.swetWidth;
        float scale = 1.5F;

        if(!swet.getPassengers().isEmpty()) {
            scale += (swet.getPassengers().get(0).getBbWidth() + swet.getPassengers().get(0).getBbHeight()) * 0.75F;
        }

        matrixStackIn.scale((width * scale) * swet.getScale(), (height * scale) * swet.getScale(), (width * scale) * swet.getScale());
    }

    @Override
    public ResourceLocation getTextureLocation(SwetEntity swet) {
        return swet.getSwetType() == 1 ? BLUE_TEXTURE : GOLDEN_TEXTURE;
    }
}
