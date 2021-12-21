package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.MoaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//TODO: Will have to review this all when all the moa functionality is completed, of course.
public class MoaModel extends EntityModel<MoaEntity>
{
	private final ModelRenderer head;
	private final ModelRenderer jaw;
	private final ModelRenderer neck;
	private final ModelRenderer body;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;
	private final ModelRenderer rightWing;
	private final ModelRenderer leftWing;
	private final ModelRenderer rightTailFeather;
	private final ModelRenderer middleTailFeather;
	private final ModelRenderer leftTailFeather;
	private boolean renderLegs;

	public MoaModel(float scale) {
		this.head = new ModelRenderer(this, 0, 13);
		this.head.addBox(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 8.0F, 0.0F);
		this.head.setPos(0.0F, 8.0F, -4.0F);

		this.jaw = new ModelRenderer(this, 24, 13);
		this.jaw.addBox(-2.0F, -1.0F, -6.0F, 4.0F, 1.0F, 8.0F, -0.1F);
		this.head.addChild(this.jaw);

		this.neck = new ModelRenderer(this, 44, 0);
		this.neck.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F);
		this.head.addChild(this.neck);

		this.body = new ModelRenderer(this, 0, 0);
		this.body.addBox(-3.0F, -3.0F, 0.0F, 6.0F, 8.0F, 5.0F, scale);
		this.body.setPos(0.0F, 16.0F, 0.0F);

		this.rightLeg = new ModelRenderer(this, 22, 0);
		this.rightLeg.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F);
		this.rightLeg.setPos(-2.0F, 16.0F, 1.0F);

		this.leftLeg = new ModelRenderer(this, 22, 0);
		this.leftLeg.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F);
		this.leftLeg.setPos(2.0F, 16.0F, 1.0F);

		this.rightWing = new ModelRenderer(this, 52, 0);
		this.rightWing.addBox(-1.0F, 0.0F, -2.0F, 1.0F, 8.0F, 4.0F, 0.0F);
		this.rightWing.setPos(-3.001F, -3.0F, 3.0F);
		this.body.addChild(this.rightWing);

		this.leftWing = new ModelRenderer(this, 52, 0);
		this.leftWing.addBox(0.0F, 0.0F, -2.0F, 1.0F, 8.0F, 4.0F, 0.0F);
		this.leftWing.setPos(3.001F, -3.0F, 3.0F);
		this.body.addChild(this.leftWing);

		this.rightTailFeather = new ModelRenderer(this, 30, 0);
		this.rightTailFeather.addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, -0.3F);
		this.rightTailFeather.setPos(0.0F, 17.5F, 1.0F);

		this.middleTailFeather = new ModelRenderer(this, 30, 0);
		this.middleTailFeather.addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, -0.3F);
		this.middleTailFeather.setPos(0.0F, 17.5F, 1.0F);

		this.leftTailFeather = new ModelRenderer(this, 30, 0);
		this.leftTailFeather.addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, -0.3F);
		this.leftTailFeather.setPos(0.0F, 17.5F, 1.0F);
	}

	@Override
	public void prepareMobModel(MoaEntity moa, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
		super.prepareMobModel(moa, p_212843_2_, p_212843_3_, p_212843_4_);
		this.renderLegs = !moa.isSitting() || (!moa.isOnGround() && moa.isSitting());
	}

	@Override
	public void setupAnim(MoaEntity moa, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.neck.xRot = -this.head.xRot;
		this.body.xRot = (float) (Math.PI / 2.0F);

		if (!moa.isOnGround()) {
			this.rightWing.setPos(-3.001F, 0.0F, 4.0F);
			this.leftWing.setPos(3.001F, 0.0F, 4.0F);
			this.rightWing.xRot = (float) -(Math.PI / 2.0F);
			this.leftWing.xRot = this.rightWing.xRot;
			this.rightLeg.xRot = 0.6F;
			this.leftLeg.xRot = this.rightLeg.xRot;
		} else {
			this.rightWing.setPos(-3.001F, -3.0F, 3.0F);
			this.leftWing.setPos(3.001F, -3.0F, 3.0F);
			this.rightWing.xRot = 0.0F;
			this.leftWing.xRot = 0.0F;
			this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.leftLeg.xRot = MathHelper.cos((float) (limbSwing * 0.6662F + Math.PI)) * 1.4F * limbSwingAmount;
		}
		float rotVal = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation);
		float destVal = moa.prevDestPos + (moa.destPos - moa.prevDestPos);

		this.rightWing.yRot = (MathHelper.sin(rotVal * 0.225F) + 1.0F) * destVal;
		this.leftWing.yRot = -this.rightWing.yRot;

		if (moa.isSitting()) {
			this.jaw.xRot = 0.0F;
		} else {
			this.jaw.xRot = 0.35F;
		}

		this.rightTailFeather.xRot = 0.25F;
		this.rightTailFeather.yRot = -0.375F;
		this.middleTailFeather.xRot = 0.25F;
		this.middleTailFeather.yRot = 0.0F;
		this.leftTailFeather.xRot = 0.25F;
		this.leftTailFeather.yRot = 0.375F;
	}

	public void setupWingsAnimation(MoaEntity moa) {
		moa.prevWingRotation = moa.wingRotation;
		moa.prevDestPos = moa.destPos;
		if (!moa.isOnGround()) {
			moa.destPos += 0.2F;
			moa.destPos = Math.min(1.0F, Math.max(0.01F, moa.destPos));
		} else {
			moa.destPos = 0.0F;
			moa.wingRotation = 0.0F;
		}
		moa.wingRotation += 1.233F;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		if (this.renderLegs) {
			this.rightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
			this.leftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		}
		this.rightTailFeather.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.middleTailFeather.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.leftTailFeather.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}
