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

public class FlyingCowWingsLayer extends LayerRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>>
{
    private static final ResourceLocation FLYING_COW_WINGS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_wings.png");
    private final FlyingCowWingModel wings = new FlyingCowWingModel();

    public FlyingCowWingsLayer(IEntityRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, FlyingCowEntity flyingCow, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (flyingCow.isBaby()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0F, 1.5F, 0.0F);
        }
        this.wings.setupAnim(flyingCow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(FLYING_COW_WINGS_TEXTURE));
        this.wings.renderToBuffer(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
