package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.FlyingCowWingModel;
import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FlyingCowWingsLayer extends RenderLayer<FlyingCowEntity, CowModel<FlyingCowEntity>>
{
    private static final ResourceLocation FLYING_COW_WINGS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_wings.png");
    private final FlyingCowWingModel wings;

    public FlyingCowWingsLayer(RenderLayerParent<FlyingCowEntity, CowModel<FlyingCowEntity>> entityRendererIn, EntityModelSet modelSet) {
        super(entityRendererIn);
        wings = new FlyingCowWingModel(modelSet.bakeLayer(AetherModelLayers.FLYING_COW_WINGS));
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, FlyingCowEntity flyingCow, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (flyingCow.isBaby()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0F, 1.5F, 0.0F);
        }
        this.wings.setupAnim(flyingCow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer vertexBuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(FLYING_COW_WINGS_TEXTURE));
        this.wings.renderToBuffer(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
