package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.SwetOuterLayer;
import com.gildedgames.aether.common.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public abstract class SwetRenderer extends MobRenderer<Swet, SlimeModel<Swet>>
{
    public SwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(AetherModelLayers.SWET)), 0.3F);
        this.addLayer(new SwetOuterLayer(this, new SlimeModel<>(context.bakeLayer(AetherModelLayers.SWET_OUTER))));
    }

    @Override
    protected void scale(Swet swet, @Nonnull PoseStack poseStack, float partialTickTime) {
        float scale = 1.5F;
        if (!swet.getPassengers().isEmpty()) {
            scale += (swet.getPassengers().get(0).getBbWidth() + swet.getPassengers().get(0).getBbHeight()) * 0.75F;
        }

        float height = Mth.lerp(partialTickTime, swet.oSwetHeight, swet.swetHeight);
        float width = Mth.lerp(partialTickTime, swet.oSwetWidth, swet.swetWidth);

        poseStack.scale(width * scale, height * scale, width * scale);
        poseStack.scale(swet.getScale(), swet.getScale(), swet.getScale());
        this.shadowRadius = 0.3F * width;
    }
}