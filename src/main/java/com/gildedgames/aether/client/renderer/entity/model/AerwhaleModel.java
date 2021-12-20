package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AerwhaleModel extends BaseAerwhaleModel
{
	public ModelPart frontBody;
	public ModelPart rightFin;
	public ModelPart leftFin;
	public ModelPart bottomPartHead;
	public ModelPart bottomPartMiddlebody;
	public ModelPart head;
	public ModelPart middleFin;
	public ModelPart backFinRight;
	public ModelPart backFinLeft;
	public ModelPart backBody;
	public ModelPart middleBody;

	public AerwhaleModel(ModelPart root) {
		this.frontBody = root.getChild("front_body");
		this.rightFin = root.getChild("right_fin");
		this.leftFin = root.getChild("left_fin");
		this.bottomPartHead = root.getChild("bottom_part_head");
		this.bottomPartMiddlebody = root.getChild("bottom_part_middle_body");
		this.head = root.getChild("head");
		this.middleFin = root.getChild("middle_fin");
		this.backFinRight = root.getChild("back_fin_right");
		this.backFinLeft = root.getChild("back_fin_left");
		this.backBody = root.getChild("back_body");
		this.middleBody = root.getChild("middle_body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("front_body", CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, -1.0F, -0.5F, 19.0F, 5.0F,21.0F).mirror(), PartPose.offsetAndRotation(2.0F, 6.0F, 38.0F, -0.1047198F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(446, 1).addBox(-20.0F, -2.0F, -6.0F, 19.0F, 3.0F, 14.0F), PartPose.offsetAndRotation(-10.0F, 4.0F, 10.0F, -0.148353F, 0.2094395F, 0.0F));
		partdefinition.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(446, 1).addBox(1.0F, -2.0F, -6.0F, 19.0F, 3.0F, 14.0F).mirror(), PartPose.offsetAndRotation(10.0F, 4.0F, 10.0F, -0.148353F, -0.2094395F, 0.0F));
		partdefinition.addOrReplaceChild("bottom_part_head", CubeListBuilder.create().texOffs(116, 28).addBox(-13.0F, 4.0F, -15.0F, 26.0F, 6.0F, 30.0F).mirror(), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("bottom_part_middle_body", CubeListBuilder.create().texOffs(16, 32).addBox(-12.0F, 5.0F, -1.0F, 24.0F, 6.0F, 26.0F).mirror(), PartPose.offset(0.0F, -1.0F, 14.0F));
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(408, 18).addBox(-12.0F, -9.0F, -14.0F, 24.0F, 18.0F, 28.0F).mirror(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("middle_fin", CubeListBuilder.create().texOffs(318, 35).addBox(-1.0F, -11.0F, 7.0F, 2.0F, 7.0F, 8.0F).mirror(), PartPose.offsetAndRotation(0.0F, -1.0F, 14.0F, -0.1441704F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("back_fin_right", CubeListBuilder.create().texOffs(261, 5).addBox(-11.0F, 0.0F, -6.0F, 15.0F, 3.0F, 24.0F), PartPose.offsetAndRotation(-4.0F, 5.0F, 59.0F, -0.1047198F, -0.7330383F, 0.0F));
		partdefinition.addOrReplaceChild("back_fin_left", CubeListBuilder.create().texOffs(261, 5).addBox(-4.0F, 0.0F, -6.0F, 13.0F, 3.0F, 24.0F).mirror(), PartPose.offsetAndRotation(5.0F, 5.0F, 59.0F, -0.1047198F, 0.7330383F, 0.0F));
		partdefinition.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(228, 32).addBox(-10.5F, -9.0F, -2.0F, 17.0F, 10.0F, 22.0F).mirror(), PartPose.offsetAndRotation(2.0F, 5.0F, 38.0F, -0.1047198F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("middle_body", CubeListBuilder.create().texOffs(314, 25).addBox(-11.0F, -5.0F, -1.0F, 22.0F, 14.0F, 25.0F).mirror(), PartPose.offsetAndRotation(0.0F, -1.0F, 14.0F, -0.0698132F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 512, 64);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.frontBody.render(matrixStack, buffer, packedLight, packedOverlay);
		this.rightFin.render(matrixStack, buffer, packedLight, packedOverlay);
		this.bottomPartHead.render(matrixStack, buffer, packedLight, packedOverlay);
		this.leftFin.render(matrixStack, buffer, packedLight, packedOverlay);
		this.bottomPartMiddlebody.render(matrixStack, buffer, packedLight, packedOverlay);
		this.head.render(matrixStack, buffer, packedLight, packedOverlay);
		this.middleFin.render(matrixStack, buffer, packedLight, packedOverlay);
		this.backFinRight.render(matrixStack, buffer, packedLight, packedOverlay);
		this.backBody.render(matrixStack, buffer, packedLight, packedOverlay);
		this.backFinLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		this.middleBody.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	
}
