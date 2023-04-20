package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

import javax.annotation.Nonnull;

public class MoaSaddleLayer extends RenderLayer<Moa, MoaModel> {
	private final MoaModel saddle;
	
	public MoaSaddleLayer(RenderLayerParent<Moa, MoaModel> entityRenderer, MoaModel saddleModel) {
		super(entityRenderer);
		this.saddle = saddleModel;
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (moa.isSaddled()) {
			this.getParentModel().copyPropertiesTo(this.saddle);
			this.saddle.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
			this.saddle.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(moa.getMoaType().getSaddleTexture()));
			this.saddle.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
