package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.SwetOuterLayer;
import com.aetherteam.aether.entity.monster.AechorPlant;
import com.aetherteam.aether.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.Mth;

public abstract class SwetRenderer extends MobRenderer<Swet, SlimeModel<Swet>> {
    public SwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(AetherModelLayers.SWET)), 0.3F);
        this.addLayer(new SwetOuterLayer(this, new SlimeModel<>(context.bakeLayer(AetherModelLayers.SWET_OUTER))));
    }

    /**
     * Scales the Swet according to its size.
     *
     * @param swet         The {@link AechorPlant} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     */
    @Override
    protected void scale(Swet swet, PoseStack poseStack, float partialTicks) {
        float scale = 1.5F;
        if (!swet.getPassengers().isEmpty()) {
            scale += (swet.getPassengers().get(0).getBbWidth() + swet.getPassengers().get(0).getBbHeight()) * 0.75F;
        }

        float height = Mth.lerp(partialTicks, swet.getSwetHeightO(), swet.getSwetHeight());
        float width = Mth.lerp(partialTicks, swet.getSwetWidthO(), swet.getSwetWidth());

        poseStack.scale(width * scale, height * scale, width * scale);
        poseStack.scale(swet.getScale(), swet.getScale(), swet.getScale());
        this.shadowRadius = 0.3F * width;
    }
}