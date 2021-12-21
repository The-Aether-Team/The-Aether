package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
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
	
	public OldAerwhaleModel() {
		this.texWidth = 192;
		this.texHeight = 96;
		
		this.middleBody = new ModelPart(this, 0, 0);
		this.middleBody.setPos(0.0F, -1.0F, 14.0F);
		this.middleBody.addBox(-9.0F, -6.0F, 1.0F, 15.0F, 15.0F, 15.0F);
		
		this.head = new ModelPart(this, 60, 0);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.head.addBox(-12.0F, -9.0F, -14.0F, 21.0F, 18.0F, 30.0F);
		
		this.backBody = new ModelPart(this, 0, 30);
		this.backBody.setPos(0.0F, 5.0F, 38.0F);
		this.backBody.addBox(-6.0F, -9.0F, -8.5F, 9.0F, 9.0F, 12.0F);
		
		this.backFinRight = new ModelPart(this, 0, 51);
		this.backFinRight.setPos(-5.0F, 2.2F, 38.0F);
		this.backFinRight.addBox(-4.0F, 0.0F, -6.0F, 24.0F, 3.0F, 12.0F);
		this.backFinRight.xRot = 0.10471975511965977F;
		this.backFinRight.yRot = -2.5497515042385164F;
		
		this.backFinLeft = new ModelPart(this, 0, 51);
		this.backFinLeft.setPos(3.0F, 2.2F, 38.0F);
		this.backFinLeft.addBox(-4.0F, 0.0F, -6.0F, 24.0F, 3.0F, 12.0F);
		this.backFinLeft.xRot = -0.10471975511965977F;
		this.backFinLeft.yRot = -0.593411945678072F;
		
		this.rightFin = new ModelPart(this, 0, 66);
		this.rightFin.setPos(-10.0F, 4.0F, 10.0F);
		this.rightFin.addBox(-12.0F, 1.4F, -6.0F, 12.0F, 3.0F, 6.0F);
		this.rightFin.yRot = 0.17453292519943295F;
		
		this.leftFin = new ModelPart(this, 0, 66);
		this.leftFin.setPos(7.0F, 4.0F, 10.0F);
		this.leftFin.addBox(0.0F, 1.4F, -6.0F, 12.0F, 3.0F, 6.0F);
		this.leftFin.yRot = -0.17453292519943295F;
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
