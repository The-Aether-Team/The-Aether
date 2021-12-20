package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MimicModel extends EntityModel<MimicEntity>
{
	public ModelPart upperBody;
	public ModelPart lowerBody;
	public ModelPart leftLeg;
	public ModelPart rightLeg;
	public ModelPart knob;
	
	public MimicModel() {
		this.texWidth = 128;
		this.texHeight = 64;
		
		this.upperBody = new ModelPart(this, 0, 10);
		this.upperBody.setPos(-8.0F, 0.0F, 8.0F);
		this.upperBody.addBox(0.0F, 0.0F, 0.0F, 16, 6, 16);
		this.upperBody.xRot = (float) Math.PI;
		
		this.lowerBody = new ModelPart(this, 0, 38);
		this.lowerBody.setPos(-8.0F, 0.0F, -8.0F);
		this.lowerBody.addBox(0.0F, 0.0F, 0.0F, 16, 10, 16);
		
		this.leftLeg = new ModelPart(this, 64, 0);
		this.leftLeg.setPos(1.5F, 9.0F, 0.0F);
		this.leftLeg.addBox(0.0F, 0.0F, -3.0F, 6, 15, 6);
		
		this.rightLeg = new ModelPart(this, 64, 0);
		this.rightLeg.mirror = true;
		this.rightLeg.setPos(-2.5F, 9.0F, 0.0F);
		this.rightLeg.addBox(-5.1F, 0.0F, -3.0F, 6, 15, 6);
		
		this.knob = new ModelPart(this, 0, 0);
		this.knob.setPos(0.0F, 0.0F, 0.0F);
		this.knob.addBox(7.0F, -2.0F, 16.0F, 2, 4, 1);
		this.upperBody.addChild(knob);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//		this.knob.offsetX = 0.5F;
//		this.knob.offsetY = 0.0F;
		
		this.upperBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.lowerBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.leftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.rightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
	
	@Override
	public void setupAnim(MimicEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.upperBody.xRot = (float) Math.PI - 0.6F * (1.0F + Mth.cos(ageInTicks / 10.0F * (float) Math.PI));
		
		if (this.riding) {
			this.rightLeg.xRot = -1.4137167F;
			this.rightLeg.yRot = (float)Math.PI / 10.0F;
			this.rightLeg.zRot = 0.07853982F;
			this.leftLeg.xRot = -1.4137167F;
			this.leftLeg.yRot = -(float)Math.PI / 10.0F;
			this.leftLeg.zRot = -0.07853982F;
		}
		else {		
			boolean isFlyingWithElytra = entityIn.getFallFlyingTicks() > 4;
			
			float limbSwingLimiter = 1.0F;
			if (isFlyingWithElytra) {
				limbSwingLimiter = (float) entityIn.getDeltaMovement().lengthSqr();
				limbSwingLimiter /= 0.2F;
				limbSwingLimiter = limbSwingLimiter * limbSwingLimiter * limbSwingLimiter;
				
				if (limbSwingLimiter < 1.0F) {
					limbSwingLimiter = 1.0F;
				}
			}
			
			this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / limbSwingLimiter;
			this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / limbSwingLimiter;
			this.rightLeg.yRot = 0.0F;
			this.leftLeg.yRot = 0.0F;
			this.rightLeg.zRot = 0.0F;
			this.leftLeg.zRot = 0.0F;
		}
	}
	
}
