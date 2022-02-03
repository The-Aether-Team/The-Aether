package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.common.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.util.Mth;

public abstract class SwetRenderer extends MobRenderer<Swet, SlimeModel<Swet>> {

    public SwetRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new SlimeModel<>(renderer.bakeLayer(AetherModelLayers.SWET)), 0.3F);
        this.addLayer(new SlimeOuterLayer<>(this, renderer.getModelSet()));
    }

    @Override
    protected void scale(Swet swet, PoseStack poseStack, float partialTickTime) {
        float scale = 1.5F;

        if(!swet.getPassengers().isEmpty()) {
            scale += (swet.getPassengers().get(0).getBbWidth() + swet.getPassengers().get(0).getBbHeight()) * 0.75F;
        }

        float height = Mth.lerp(partialTickTime, swet.oSwetHeight, swet.swetHeight);
        float width = Mth.lerp(partialTickTime, swet.oSwetWidth, swet.swetWidth);

        poseStack.scale(width * scale, height * scale, width * scale);
        poseStack.scale(swet.getScale(), swet.getScale(), swet.getScale());
        this.shadowRadius = 0.3F * width;
    }
}