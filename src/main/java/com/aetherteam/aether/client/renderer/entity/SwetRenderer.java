package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.state.SwetRenderState;
import com.aetherteam.aether.entity.monster.AechorPlant;
import com.aetherteam.aether.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.Mth;

public abstract class SwetRenderer extends MobRenderer<Swet, SwetRenderState, SlimeModel> {
    public SwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(AetherModelLayers.SWET)), 0.3F);
    }


    @Override
    public void extractRenderState(Swet entity, SwetRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        if (!entity.getPassengers().isEmpty()) {
            reusedState.extraWidth = (entity.getPassengers().getFirst().getBbWidth() + entity.getPassengers().getFirst().getBbHeight()) * 0.75F;
        }
        reusedState.swetWidth = Mth.lerp(partialTick, entity.getSwetWidthO(), entity.getSwetWidth());
        reusedState.swetHeight = Mth.lerp(partialTick, entity.getSwetHeightO(), entity.getSwetHeight());
    }

    @Override
    public SwetRenderState createRenderState() {
        return new SwetRenderState();
    }

    /**
     * Scales the Swet according to its size.
     *
     * @param swet         The {@link AechorPlant} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     */
    @Override
    protected void scale(SwetRenderState swet, PoseStack poseStack) {
        float scale = 1.5F;
        scale += swet.extraWidth;


        float height = swet.swetHeight;
        float width = swet.swetWidth;

        poseStack.scale(width * scale, height * scale, width * scale);
        poseStack.scale(swet.scale, swet.scale, swet.scale);
        this.shadowRadius = 0.3F * width;
    }
}
