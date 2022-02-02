package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class MoaModel extends BipedBirdModel<Moa>
{
	public boolean renderLegs;

	public MoaModel(ModelPart root) {
		super(root);
	}

	@Override
	public void prepareMobModel(@Nonnull Moa moa, float limbSwing, float limbSwingAmount, float partialTicks) {
		super.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
		this.renderLegs = !moa.isSitting() || (!moa.isOnGround() && moa.isSitting());
	}

	@Override
	public void setupAnim(@Nonnull Moa moa, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		float rotVal = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation);
		float destVal = moa.prevDestPos + (moa.destPos - moa.prevDestPos);

		this.rightWing.yRot = (Mth.sin(rotVal * 0.225F) + 1.0F) * destVal;
		this.leftWing.yRot = -this.rightWing.yRot;

		if (moa.isSitting()) {
			this.jaw.xRot = 0.0F;
		} else {
			this.jaw.xRot = 0.35F;
		}
	}

	public void setupWingsAnimation(Moa moa) {
		moa.prevWingRotation = moa.wingRotation;
		moa.prevDestPos = moa.destPos;
		if (!moa.isOnGround() && moa.hasImpulse) {
			moa.destPos += 0.2F;
			moa.destPos = Math.min(1.0F, Math.max(0.01F, moa.destPos));
		} else {
			moa.destPos = 0.0F;
			moa.wingRotation = 0.0F;
		}
		moa.wingRotation += 1.233F;
	}

	@Override
	public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
		if (this.renderLegs) {
			this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay);
			this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay);
		}
	}
}
