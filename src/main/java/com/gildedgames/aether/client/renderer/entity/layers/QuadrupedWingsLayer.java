package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.client.renderer.entity.model.QuadrupedWingsModel;
import com.gildedgames.aether.common.entity.passive.WingedEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class QuadrupedWingsLayer<T extends WingedEntity, M extends QuadrupedModel<T>> extends RenderLayer<T, M>
{
    private final ResourceLocation resourceLocation;
    private final QuadrupedWingsModel<T> wings;

    public QuadrupedWingsLayer(RenderLayerParent<T, M> entityRendererIn, QuadrupedWingsModel<T> model, ResourceLocation resourceLocation) {
        super(entityRendererIn);
        this.wings = model;
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void render(@Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int packedLightIn, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isBaby()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0F, 1.5F, 0.0F);
        }
        this.wings.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer vertexBuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(this.resourceLocation));
        this.wings.renderToBuffer(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
