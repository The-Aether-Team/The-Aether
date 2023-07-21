package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.client.renderer.entity.model.QuadrupedWingsModel;
import com.aetherteam.aether.entity.passive.WingedAnimal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class QuadrupedWingsLayer<T extends WingedAnimal, M extends QuadrupedModel<T>> extends RenderLayer<T, M> {
    private final ResourceLocation resourceLocation;
    private final QuadrupedWingsModel<T> wings;

    public QuadrupedWingsLayer(RenderLayerParent<T, M> entityRenderer, QuadrupedWingsModel<T> wingsModel, ResourceLocation resourceLocation) {
        super(entityRenderer);
        this.wings = wingsModel;
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0.0F, 1.5F, 0.0F);
        }
        this.wings.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.resourceLocation));
        this.wings.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
