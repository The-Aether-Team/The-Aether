package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.MoaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//TODO: Will have to review this all when all the moa functionality is completed, of course.
public class MoaModel extends EntityModel<MoaEntity>
{
	public ModelPart head;
	public ModelPart jaw;
	public ModelPart neck;
	public ModelPart body;
	public ModelPart rightLeg;
	public ModelPart leftLeg;
	public ModelPart rightWing;
	public ModelPart leftWing;
	public ModelPart rightTailFeather;
	public ModelPart middleTailFeather;
	public ModelPart leftTailFeather;
	public boolean renderLegs;

	public MoaModel(ModelPart root) {
		this.head = root.getChild("head");
		this.jaw = head.getChild("jaw");
		this.neck = head.getChild("neck");
		this.body = root.getChild("body");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
		this.rightWing = body.getChild("right_wing");
		this.leftWing = body.getChild("left_wing");
		this.rightTailFeather = root.getChild("right_tail_feather");
		this.middleTailFeather = root.getChild("middle_tail_feather");
		this.leftTailFeather = root.getChild("left_tail_feather");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 8.0F), PartPose.offset(0.0F, 8.0F, -4.0F));
		head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 1.0F, 8.0F), PartPose.ZERO); //TODO: Scale to -0.1F
		head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs( 44, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 8.0F, 5.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F), PartPose.offset(-2.0F, 16.0F, 1.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F), PartPose.offset(2.0F, 16.0F, 1.0F));
		body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(52, 0).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 8.0F, 4.0F), PartPose.offset(-3.001F, -3.0F, 3.0F));
		body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(52, 0).addBox(0.0F, 0.0F, -2.0F, 1.0F, 8.0F, 4.0F), PartPose.offset(3.001F, -3.0F, 3.0F));
		partdefinition.addOrReplaceChild("right_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F), PartPose.offset(0.0F, 17.5F, 1.0F)); //TODO: Scale to -0.3F
		partdefinition.addOrReplaceChild("middle_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F), PartPose.offset(0.0F, 17.5F, 1.0F)); //TODO: Scale to -0.3F
		partdefinition.addOrReplaceChild("left_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F), PartPose.offset(0.0F, 17.5F, 1.0F)); //TODO: Scale to -0.3F
		return LayerDefinition.create(meshdefinition, 128, 64);
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
			this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.leftLeg.xRot = Mth.cos((float) (limbSwing * 0.6662F + Math.PI)) * 1.4F * limbSwingAmount;
		}
		float rotVal = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation);
		float destVal = moa.prevDestPos + (moa.destPos - moa.prevDestPos);

		this.rightWing.yRot = (Mth.sin(rotVal * 0.225F) + 1.0F) * destVal;
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
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrixStack, buffer, packedLight, packedOverlay);
		this.body.render(matrixStack, buffer, packedLight, packedOverlay);
		if (this.renderLegs) {
			this.rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			this.leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		}
		this.rightTailFeather.render(matrixStack, buffer, packedLight, packedOverlay);
		this.middleTailFeather.render(matrixStack, buffer, packedLight, packedOverlay);
		this.leftTailFeather.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}
