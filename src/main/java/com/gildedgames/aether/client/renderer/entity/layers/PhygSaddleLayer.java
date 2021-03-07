package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.common.entity.passive.PhygEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class PhygSaddleLayer extends LayerRenderer<PhygEntity, PigModel<PhygEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private final PigModel<PhygEntity> pigModel = new PigModel<>(0.5F);
    public PhygSaddleLayer(IEntityRenderer<PhygEntity, PigModel<PhygEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, PhygEntity phyg, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (phyg.isSaddled()) {
            this.getEntityModel().copyModelAttributesTo(this.pigModel);
            this.pigModel.setLivingAnimations(phyg, limbSwing, limbSwingAmount, partialTicks);
            this.pigModel.setRotationAngles(phyg, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(TEXTURE));
            this.pigModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
