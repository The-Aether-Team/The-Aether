package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;

import javax.annotation.Nonnull;

public class MoaModel extends BipedBirdModel<Moa> {
	public boolean renderLegs;

	public MoaModel(ModelPart root) {
		super(root);
	}

	@Override
	public void prepareMobModel(@Nonnull Moa moa, float limbSwing, float limbSwingAmount, float partialTicks) {
		super.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
		this.renderLegs = !moa.isSitting() || (!moa.isEntityOnGround() && moa.isSitting());
	}

	@Override
	public void setupAnim(@Nonnull Moa moa, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if (moa.isSitting()) {
			this.jaw.xRot = 0.0F;
		} else {
			this.jaw.xRot = 0.35F;
		}
	}

	@Override
	public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
		if (this.renderLegs) {
			this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
			this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
}
