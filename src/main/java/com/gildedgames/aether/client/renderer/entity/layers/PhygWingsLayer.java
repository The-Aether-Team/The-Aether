package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.PhygWingModel;
import com.gildedgames.aether.common.entity.passive.PhygEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PhygWingsLayer extends RenderLayer<PhygEntity, PigModel<PhygEntity>>
{
    private static final ResourceLocation PHYG_WINGS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg_wings.png");
    private final PhygWingModel wings;

    public PhygWingsLayer(RenderLayerParent<PhygEntity, PigModel<PhygEntity>> entityRendererIn, EntityModelSet modelSet) {
        super(entityRendererIn);
        wings = new PhygWingModel(modelSet.bakeLayer(AetherModelLayers.PHYG_WINGS));
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, PhygEntity phyg, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (phyg.isBaby()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0F, 1.5F, 0.0F);
        }
        this.wings.setupAnim(phyg, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer vertexBuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(PHYG_WINGS_TEXTURE));
        this.wings.renderToBuffer(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
