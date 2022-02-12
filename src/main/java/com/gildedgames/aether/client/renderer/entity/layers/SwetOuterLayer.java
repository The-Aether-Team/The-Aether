package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.common.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import javax.annotation.Nonnull;

public class SwetOuterLayer extends RenderLayer<Swet, SlimeModel<Swet>>
{
    private final SlimeModel<Swet> model;

    public SwetOuterLayer(RenderLayerParent<Swet, SlimeModel<Swet>> entityRenderer, SlimeModel<Swet> model) {
        super(entityRenderer);
        this.model = model;
    }

    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull Swet swet, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = minecraft.shouldEntityAppearGlowing(swet) && swet.isInvisible();
        if (!swet.isInvisible() || flag) {
            VertexConsumer consumer;
            if (flag) {
                consumer = buffer.getBuffer(RenderType.outline(this.getTextureLocation(swet)));
            } else {
                consumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(swet)));
            }
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(swet, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(swet, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(swet, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
