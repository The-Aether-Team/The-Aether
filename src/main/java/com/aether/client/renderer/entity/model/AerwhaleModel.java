/*
package com.aether.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class AerwhaleModel extends EntityModel<AerwhaleEntity> {
	private final ModelRenderer FrontBody;
	private final ModelRenderer RightFin;
	private final ModelRenderer BottomPartHead;
	private final ModelRenderer LeftFin;
	private final ModelRenderer BottomPartMiddlebody;
	private final ModelRenderer Head;
	private final ModelRenderer MiddleFin;
	private final ModelRenderer BackfinRight;
	private final ModelRenderer BackBody;
	private final ModelRenderer BackfinLeft;
	private final ModelRenderer Middlebody;

	public AerwhaleModel() {
		textureWidth = 512;
		textureHeight = 64;

		FrontBody = new ModelRenderer(this);
		FrontBody.setRotationPoint(2.0F, 6.0F, 38.0F);
		FrontBody.setTextureOffset(0, 0).addBox(-11.5F, -1.0F, -0.5F, 19.0F, 5.0F, 21.0F, 0.0F, true);

		RightFin = new ModelRenderer(this);
		RightFin.setRotationPoint(-10.0F, 4.0F, 10.0F);
		RightFin.setTextureOffset(446, 1).addBox(-20.0F, -2.0F, -6.0F, 19.0F, 3.0F, 14.0F, 0.0F, false);

		BottomPartHead = new ModelRenderer(this);
		BottomPartHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		BottomPartHead.setTextureOffset(116, 28).addBox(-13.0F, 4.0F, -15.0F, 26.0F, 6.0F, 30.0F, 0.0F, true);

		LeftFin = new ModelRenderer(this);
		LeftFin.setRotationPoint(10.0F, 4.0F, 10.0F);
		LeftFin.setTextureOffset(446, 1).addBox(1.0F, -2.0F, -6.0F, 19.0F, 3.0F, 14.0F, 0.0F, true);

		BottomPartMiddlebody = new ModelRenderer(this);
		BottomPartMiddlebody.setRotationPoint(0.0F, -1.0F, 14.0F);
		BottomPartMiddlebody.setTextureOffset(16, 32).addBox(-12.0F, 5.0F, -1.0F, 24.0F, 6.0F, 26.0F, 0.0F, true);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		Head.setTextureOffset(408, 18).addBox(-12.0F, -9.0F, -14.0F, 24.0F, 18.0F, 28.0F, 0.0F, true);

		MiddleFin = new ModelRenderer(this);
		MiddleFin.setRotationPoint(0.0F, -1.0F, 14.0F);
		MiddleFin.setTextureOffset(318, 35).addBox(-1.0F, -11.0F, 7.0F, 2.0F, 7.0F, 8.0F, 0.0F, true);

		BackfinRight = new ModelRenderer(this);
		BackfinRight.setRotationPoint(-4.0F, 5.0F, 59.0F);
		BackfinRight.setTextureOffset(261, 5).addBox(-11.0F, 0.0F, -6.0F, 15.0F, 3.0F, 24.0F, 0.0F, false);

		BackBody = new ModelRenderer(this);
		BackBody.setRotationPoint(2.0F, 5.0F, 38.0F);
		BackBody.setTextureOffset(228, 32).addBox(-10.5F, -9.0F, -2.0F, 17.0F, 10.0F, 22.0F, 0.0F, true);

		BackfinLeft = new ModelRenderer(this);
		BackfinLeft.setRotationPoint(5.0F, 5.0F, 59.0F);
		BackfinLeft.setTextureOffset(261, 5).addBox(-4.0F, 0.0F, -6.0F, 13.0F, 3.0F, 24.0F, 0.0F, true);

		Middlebody = new ModelRenderer(this);
		Middlebody.setRotationPoint(0.0F, -1.0F, 14.0F);
		Middlebody.setTextureOffset(314, 25).addBox(-11.0F, -5.0F, -1.0F, 22.0F, 14.0F, 25.0F, 0.0F, true);
	}

	@Override
	public void setRotationAngles(AerwhaleEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		FrontBody.render(matrixStack, buffer, packedLight, packedOverlay);
		RightFin.render(matrixStack, buffer, packedLight, packedOverlay);
		BottomPartHead.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftFin.render(matrixStack, buffer, packedLight, packedOverlay);
		BottomPartMiddlebody.render(matrixStack, buffer, packedLight, packedOverlay);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
		MiddleFin.render(matrixStack, buffer, packedLight, packedOverlay);
		BackfinRight.render(matrixStack, buffer, packedLight, packedOverlay);
		BackBody.render(matrixStack, buffer, packedLight, packedOverlay);
		BackfinLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		Middlebody.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}*/
