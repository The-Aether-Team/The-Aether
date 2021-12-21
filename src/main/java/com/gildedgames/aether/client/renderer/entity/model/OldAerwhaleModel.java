package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OldAerwhaleModel extends BaseAerwhaleModel
{
	public ModelPart middleBody;
	public ModelPart leftFin;
	public ModelPart head;
	public ModelPart backFinLeft;
	public ModelPart backBody;
	public ModelPart backFinRight;
	public ModelPart rightFin;
	
	public OldAerwhaleModel(ModelPart root) {
		this.middleBody = root.getChild("middle_body"); // 0 , 0
//		this.middleBody.setPos(0.0F, -1.0F, 14.0F);
//		this.middleBody.addBox(-9.0F, -6.0F, 1.0F, 15.0F, 15.0F, 15.0F);
		
		this.head = root.getChild("head");
		this.backBody = root.getChild("back_body");
		this.backFinRight = root.getChild("back_fin_right");
		this.backFinLeft = root.getChild("back_fin_left");
		this.rightFin = root.getChild("fin_right");
		this.leftFin = root.getChild("fin_left");
	}

	public static LayerDefinition createMainLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("middle_body", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-9.0F, -6.0F, 1.0F, 15.0F, 15.0F, 15.0F), PartPose.ZERO);

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(60, 0)
				.addBox(-12.0F, -9.0F, -14.0F, 21.0F, 18.0F, 30.0F), PartPose.ZERO);

		partdefinition.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(0, 30)
				.addBox(-6.0F, -9.0F, -8.5F, 9.0F, 9.0F, 12.0F), PartPose.offset(3.0F, 2.2F, 38.0F));

		partdefinition.addOrReplaceChild("back_fin_right", CubeListBuilder.create().texOffs(0, 51)
				.addBox(-4.0F, 0.0F, -6.0F, 24.0F, 3.0F, 12.0F), PartPose.offsetAndRotation(-5.0F, 2.2F, 38.0F,  -0.10471975511965977F,  -2.5497515042385164F, 0.0F));

		partdefinition.addOrReplaceChild("back_fin_left", CubeListBuilder.create().texOffs(0, 51)
				.addBox(-4.0F, 0.0F, -6.0F, 24.0F, 3.0F, 12.0F), PartPose.offsetAndRotation(3.0F, 2.2F, 38.0F,  0.10471975511965977F,  -0.593411945678072F, 0.0F));

		partdefinition.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(0, 66)
				.addBox(-12.0F, 1.4F, -6.0F, 12.0F, 3.0F, 6.0F), PartPose.offsetAndRotation(-10.0F, 4.0F, 10.0F,  0.0F, 0.17453292519943295F, 0.0F));

		partdefinition.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(0, 66)
				.addBox(0.0F, 1.4F, -6.0F, 12.0F, 3.0F, 6.0F), PartPose.offsetAndRotation(7.0F, 4.0F, 10.0F,  0.0F, -0.17453292519943295F, 0.0F));

		return LayerDefinition.create(meshdefinition, 192, 96);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.middleBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.backBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.backFinRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.backFinLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.rightFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.leftFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
	
}
