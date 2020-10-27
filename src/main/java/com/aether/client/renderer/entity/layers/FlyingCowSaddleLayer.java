package com.aether.client.renderer.entity.layers;

import com.aether.Aether;
import com.aether.entity.passive.FlyingCowEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class FlyingCowSaddleLayer extends LayerRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/flying_cow/saddle.png");
    private final CowModel<FlyingCowEntity> cowModel = new CowModel<>();
    public FlyingCowSaddleLayer(IEntityRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, FlyingCowEntity cow, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (cow.isSaddled()) {
            this.getEntityModel().copyModelAttributesTo(this.cowModel);
            this.cowModel.setLivingAnimations(cow, limbSwing, limbSwingAmount, partialTicks);
            this.cowModel.setRotationAngles(cow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(TEXTURE));
            this.cowModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
