package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.FlyingCowWingModel;
import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class FlyingCowWingsLayer extends LayerRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>> {
    private static final ResourceLocation WINGS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/flying_cow/wings.png");

    private FlyingCowWingModel wingsModel = new FlyingCowWingModel();

    public FlyingCowWingsLayer(IEntityRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, FlyingCowEntity cow, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        wingsModel.setupAnim(cow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(WINGS_TEXTURE));
        wingsModel.renderToBuffer(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
