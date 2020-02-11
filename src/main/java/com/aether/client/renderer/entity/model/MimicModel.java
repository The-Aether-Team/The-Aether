package com.aether.client.renderer.entity.model;

import com.aether.entity.monster.MimicEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class MimicModel extends EntityModel<MimicEntity> {
	private final RendererModel upperBody;
	private final RendererModel lowerBody;
	private final RendererModel leftLeg;
	private final RendererModel rightLeg;
	private final RendererModel knob;
	
	public MimicModel() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		
		this.upperBody = new RendererModel(this, 0, 10);
		this.upperBody.setRotationPoint(-8.0f, 0.0f, 8.0f);
		this.upperBody.addBox(0.0f, 0.0f, 0.0f, 16, 6, 16);
		this.upperBody.rotateAngleX = (float) Math.PI;
		
		this.lowerBody = new RendererModel(this, 0, 38);
		this.lowerBody.setRotationPoint(-8.0f, 0.0f, -8.0f);
		this.lowerBody.addBox(0.0f, 0.0f, 0.0f, 16, 10, 16);
		
		this.leftLeg = new RendererModel(this, 64, 0);
		this.leftLeg.setRotationPoint(1.5f, 9.0f, 0.0f);
		this.leftLeg.addBox(0.0f, 0.0f, -3.0f, 6, 15, 6);
		
		this.rightLeg = new RendererModel(this, 64, 0);
		this.rightLeg.mirror = true;
		this.rightLeg.setRotationPoint(-2.5f, 9.0f, 0.0f);
		this.rightLeg.addBox(-5.1f, 0.0f, -3.0f, 6, 15, 6);
		
		this.knob = new RendererModel(this, 0, 0);
		this.	knob.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.	knob.addBox(-1.0f, -2.0f, 16.0f, 2, 4, 1);
		this.upperBody.addChild(knob);
	}
	
	@Override
	public void render(MimicEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.knob.offsetX = 0.5f;
		this.knob.offsetY = 0.0f;
		
		this.upperBody.render(scale);
		this.lowerBody.render(scale);
		this.leftLeg.render(scale);
		this.rightLeg.render(scale);
	}
	
	@Override
	public void setRotationAngles(MimicEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.upperBody.rotateAngleX = (float) Math.PI - 0.6f * (1.0f + MathHelper.cos(ageInTicks / 10.0f * (float) Math.PI));
		
		if (this.isSitting) {
			this.rightLeg.rotateAngleX = -1.4137167f;
			this.rightLeg.rotateAngleY = (float)Math.PI / 10.0f;
			this.rightLeg.rotateAngleZ = 0.07853982f;
			this.leftLeg.rotateAngleX = -1.4137167f;
			this.leftLeg.rotateAngleY = -(float)Math.PI / 10.0f;
			this.leftLeg.rotateAngleZ = -0.07853982f;
		}
		else {		
			boolean isFlyingWithElytra = entityIn.getTicksElytraFlying() > 4;
			
			float limbSwingLimiter = 1.0f;
			if (isFlyingWithElytra) {
				limbSwingLimiter = (float) entityIn.getMotion().lengthSquared();
				limbSwingLimiter /= 0.2f;
				limbSwingLimiter = limbSwingLimiter * limbSwingLimiter * limbSwingLimiter;
				
				if (limbSwingLimiter < 1.0f) {
					limbSwingLimiter = 1.0f;
				}
			}
			
			this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / limbSwingLimiter;
			this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount / limbSwingLimiter;
			this.rightLeg.rotateAngleY = 0.0f;
			this.leftLeg.rotateAngleY = 0.0f;
			this.rightLeg.rotateAngleZ = 0.0f;
			this.leftLeg.rotateAngleZ = 0.0f;
		}
	}
	
}
