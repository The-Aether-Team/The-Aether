package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.dungeon.Mimic;

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

public class MimicModel extends EntityModel<Mimic> {
	private final ModelPart upperBody;
	private final ModelPart lowerBody;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart knob;

	public MimicModel(ModelPart root) {
		this.upperBody = root.getChild("upper_body");
		this.lowerBody = root.getChild("lower_body");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.knob = this.upperBody.getChild("knob");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition upperBody = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, 0.0F, 0.0F, 16, 6, 16), PartPose.offsetAndRotation(-8.0F, 0.0F, 8.0F, (float)Math.PI, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("lower_body", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, 0.0F, 0.0F, 16, 10, 16), PartPose.offset(-8.0F, 0.0F, -8.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(64, 0).addBox(0.0F, 0.0F, -3.0F, 6, 15, 6), PartPose.offset(1.5F, 9.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(64, 0).addBox(-5.1F, 0.0F, -3.0F, 6, 15, 6).mirror(), PartPose.offset(-2.5F, 9.0F, 0.0F));
		upperBody.addOrReplaceChild("knob", CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -2.0F, 16.0F, 2, 4, 1), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 128, 64);
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
	public void setupAnim(Mimic entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
