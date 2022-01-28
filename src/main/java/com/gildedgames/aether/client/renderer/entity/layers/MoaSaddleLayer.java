package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.client.renderer.entity.model.MoaModel;
import com.gildedgames.aether.common.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

import javax.annotation.Nonnull;

public class MoaSaddleLayer extends RenderLayer<Moa, MoaModel>
{
	private final MoaModel model;
	
	public MoaSaddleLayer(RenderLayerParent<Moa, MoaModel> entityRendererIn, MoaModel model) {
		super(entityRendererIn);
		this.model = model;
	}

	@Override
	public void render(@Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int packedLightIn, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (moa.isSaddled()) {
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
			this.model.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			VertexConsumer vertexConsumer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(moa.getMoaType().getSaddleTexture()));
			this.model.renderToBuffer(matrixStackIn, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
