package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.client.renderer.entity.model.MoaModel;
import com.gildedgames.aether.common.entity.passive.MoaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class MoaSaddleLayer extends RenderLayer<MoaEntity, MoaModel> {
	private final MoaModel moaModel = new MoaModel(0.25F);
	
	public MoaSaddleLayer(RenderLayerParent<MoaEntity, MoaModel> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, MoaEntity moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (moa.isSaddled()) {
			this.getParentModel().copyPropertiesTo(this.moaModel);
			this.moaModel.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
			this.moaModel.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(moa.getMoaType().getSaddleTexture()));
			this.moaModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
}
