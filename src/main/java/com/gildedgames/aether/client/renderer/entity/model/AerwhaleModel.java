package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AerwhaleModel extends BaseAerwhaleModel
{
	public ModelPart frontBody;
	public ModelPart rightFin;
	public ModelPart bottomPartHead;
	public ModelPart leftFin;
	public ModelPart bottomPartMiddlebody;
	public ModelPart head;
	public ModelPart middleFin;
	public ModelPart backFinRight;
	public ModelPart backBody;
	public ModelPart backFinLeft;
	public ModelPart middleBody;

	public AerwhaleModel() {
		this.texWidth = 512;
		this.texHeight = 64;

		this.frontBody = new ModelPart(this, 0, 0);
		this.frontBody.setPos(2.0F, 6.0F, 38.0F);
		this.frontBody.addBox(-11.5F, -1.0F, -0.5F, 19.0F, 5.0F, 21.0F, true);
		this.frontBody.xRot = -0.1047198F;

		this.rightFin = new ModelPart(this, 446, 1);
		this.rightFin.setPos(-10.0F, 4.0F, 10.0F);
		this.rightFin.addBox(-20.0F, -2.0F, -6.0F, 19.0F, 3.0F, 14.0F, false);
		this.rightFin.xRot = -0.148353F;
		this.rightFin.yRot = 0.2094395F;		

		this.bottomPartHead = new ModelPart(this, 116, 28);
		this.bottomPartHead.setPos(0.0F, 0.0F, 0.0F);
		this.bottomPartHead.addBox(-13.0F, 4.0F, -15.0F, 26.0F, 6.0F, 30.0F, true);

		this.leftFin = new ModelPart(this, 446, 1);
		this.leftFin.setPos(10.0F, 4.0F, 10.0F);
		this.leftFin.addBox(1.0F, -2.0F, -6.0F, 19.0F, 3.0F, 14.0F, true);
		this.leftFin.xRot = -0.148353F;
		this.leftFin.yRot = -0.2094395F;

		this.bottomPartMiddlebody = new ModelPart(this, 16, 32);
		this.bottomPartMiddlebody.setPos(0.0F, -1.0F, 14.0F);
		this.bottomPartMiddlebody.addBox(-12.0F, 5.0F, -1.0F, 24.0F, 6.0F, 26.0F, true);

		this.head = new ModelPart(this, 408, 18);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.head.addBox(-12.0F, -9.0F, -14.0F, 24.0F, 18.0F, 28.0F, true);

		this.middleFin = new ModelPart(this, 318, 35);
		this.middleFin.setPos(0.0F, -1.0F, 14.0F);
		this.middleFin.addBox(-1.0F, -11.0F, 7.0F, 2.0F, 7.0F, 8.0F, true);
		this.middleFin.xRot = -0.1441704F;

		this.backFinRight = new ModelPart(this, 261, 5);
		this.backFinRight.setPos(-4.0F, 5.0F, 59.0F);
		this.backFinRight.addBox(-11.0F, 0.0F, -6.0F, 15.0F, 3.0F, 24.0F, false);
		this.backFinRight.xRot = -0.1047198F;
		this.backFinRight.yRot = -0.7330383F;

		this.backBody = new ModelPart(this, 228, 32);
		this.backBody.setPos(2.0F, 5.0F, 38.0F);
		this.backBody.addBox(-10.5F, -9.0F, -2.0F, 17.0F, 10.0F, 22.0F, true);
		this.backBody.xRot = -0.1047198F;

		this.backFinLeft = new ModelPart(this, 261, 5);
		this.backFinLeft.setPos(5.0F, 5.0F, 59.0F);
		this.backFinLeft.addBox(-4.0F, 0.0F, -6.0F, 13.0F, 3.0F, 24.0F, true);
		this.backFinLeft.xRot = -0.1047198F;
		this.backFinLeft.yRot = 0.7330383F;
		
		this.middleBody = new ModelPart(this, 314, 25);
		this.middleBody.setPos(0.0F, -1.0F, 14.0F);
		this.middleBody.addBox(-11.0F, -5.0F, -1.0F, 22.0F, 14.0F, 25.0F, true);
		this.middleBody.xRot = -0.0698132F;
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		//System.out.println("Aerwhale render");
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
