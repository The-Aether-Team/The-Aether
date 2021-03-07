package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.MimicEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MimicModel extends EntityModel<MimicEntity> {
	private final ModelRenderer upperBody;
	private final ModelRenderer lowerBody;
	private final ModelRenderer leftLeg;
	private final ModelRenderer rightLeg;
	private final ModelRenderer knob;
	
	public MimicModel() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		
		this.upperBody = new ModelRenderer(this, 0, 10);
		this.upperBody.setRotationPoint(-8.0F, 0.0F, 8.0F);
		this.upperBody.addBox(0.0F, 0.0F, 0.0F, 16, 6, 16);
		this.upperBody.rotateAngleX = (float) Math.PI;
		
		this.lowerBody = new ModelRenderer(this, 0, 38);
		this.lowerBody.setRotationPoint(-8.0F, 0.0F, -8.0F);
		this.lowerBody.addBox(0.0F, 0.0F, 0.0F, 16, 10, 16);
		
		this.leftLeg = new ModelRenderer(this, 64, 0);
		this.leftLeg.setRotationPoint(1.5F, 9.0F, 0.0F);
		this.leftLeg.addBox(0.0F, 0.0F, -3.0F, 6, 15, 6);
		
		this.rightLeg = new ModelRenderer(this, 64, 0);
		this.rightLeg.mirror = true;
		this.rightLeg.setRotationPoint(-2.5F, 9.0F, 0.0F);
		this.rightLeg.addBox(-5.1F, 0.0F, -3.0F, 6, 15, 6);
		
		this.knob = new ModelRenderer(this, 0, 0);
		this.knob.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.knob.addBox(7.0F, -2.0F, 16.0F, 2, 4, 1);
		this.upperBody.addChild(knob);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//		this.knob.offsetX = 0.5F;
//		this.knob.offsetY = 0.0F;
		
		this.upperBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.lowerBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.leftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.rightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
	
	@Override
	public void setRotationAngles(MimicEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.upperBody.rotateAngleX = (float) Math.PI - 0.6F * (1.0F + MathHelper.cos(ageInTicks / 10.0F * (float) Math.PI));
		
		if (this.isSitting) {
			this.rightLeg.rotateAngleX = -1.4137167F;
			this.rightLeg.rotateAngleY = (float)Math.PI / 10.0F;
			this.rightLeg.rotateAngleZ = 0.07853982F;
			this.leftLeg.rotateAngleX = -1.4137167F;
			this.leftLeg.rotateAngleY = -(float)Math.PI / 10.0F;
			this.leftLeg.rotateAngleZ = -0.07853982F;
		}
		else {		
			boolean isFlyingWithElytra = entityIn.getTicksElytraFlying() > 4;
			
			float limbSwingLimiter = 1.0F;
			if (isFlyingWithElytra) {
				limbSwingLimiter = (float) entityIn.getMotion().lengthSquared();
				limbSwingLimiter /= 0.2F;
				limbSwingLimiter = limbSwingLimiter * limbSwingLimiter * limbSwingLimiter;
				
				if (limbSwingLimiter < 1.0F) {
					limbSwingLimiter = 1.0F;
				}
			}
			
			this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / limbSwingLimiter;
			this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / limbSwingLimiter;
			this.rightLeg.rotateAngleY = 0.0F;
			this.leftLeg.rotateAngleY = 0.0F;
			this.rightLeg.rotateAngleZ = 0.0F;
			this.leftLeg.rotateAngleZ = 0.0F;
		}
	}
	
}
