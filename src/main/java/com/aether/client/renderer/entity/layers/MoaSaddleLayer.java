package com.aether.client.renderer.entity.layers;

import com.aether.client.renderer.entity.model.MoaModel;
import com.aether.entity.passive.MoaEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoaSaddleLayer extends LayerRenderer<MoaEntity, MoaModel> {
	private final MoaModel moaModel = new MoaModel(0.25F);
	
	public MoaSaddleLayer(IEntityRenderer<MoaEntity, MoaModel> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoaEntity moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (moa.isSaddled()) {
			this.getEntityModel().copyModelAttributesTo(this.moaModel);
			this.moaModel.setLivingAnimations(moa, limbSwing, limbSwingAmount, partialTicks);
			this.moaModel.setRotationAngles(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(moa.getMoaType().getSaddleTexture()));
			this.moaModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
}
