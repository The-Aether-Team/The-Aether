package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.client.renderer.entity.state.SwetRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.SlimeOuterLayer}.
 */
public class SwetOuterLayer extends RenderLayer<SwetRenderState, SlimeModel> {
    private final SlimeModel outer;
    private final ResourceLocation texture;

    public SwetOuterLayer(RenderLayerParent<SwetRenderState, SlimeModel> entityRenderer, SlimeModel outerModel, ResourceLocation texture) {
        super(entityRenderer);
        this.outer = outerModel;
        this.texture = texture;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, SwetRenderState swet, float v, float v1) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = swet.appearsGlowing && swet.isInvisible;
        if (!swet.isInvisible || flag) {
            VertexConsumer consumer;
            if (flag) {
                consumer = buffer.getBuffer(RenderType.outline(texture));
            } else {
                consumer = buffer.getBuffer(RenderType.entityTranslucent(texture));
            }
            this.outer.setupAnim(swet);
            this.outer.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(swet, 0.0F));
        }
    }
}
