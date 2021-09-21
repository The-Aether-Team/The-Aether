package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.MoaEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MoaModel extends EntityModel<MoaEntity>
{
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer legs;
	private final ModelRenderer legs2;
	private final ModelRenderer wings;
	private final ModelRenderer wings2;
	private final ModelRenderer jaw;
	private final ModelRenderer neck;
	private final ModelRenderer feather1;
	private final ModelRenderer feather2;
	private final ModelRenderer feather3;
	private boolean renderLegs;

	public MoaModel(float scale) {
		this.head = new ModelRenderer(this, 0, 13);
		this.head.addBox(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 8.0F, 0.0F);
		this.head.setPos(0.0F, 8.0F, -4.0F);

		this.jaw = new ModelRenderer(this, 24, 13);
		this.jaw.addBox(-2.0F, -1.0F, -6.0F, 4.0F, 1.0F, 8.0F, -0.1F);
		this.jaw.setPos(0.0F, 8.0F, -4.0F);

		this.body = new ModelRenderer(this, 0, 0);
		this.body.addBox(-3.0F, -3.0F, 0.0F, 6.0F, 8.0F, 5.0F, scale);
		this.body.setPos(0.0F, 16.0F, 0.0F);

		this.legs = new ModelRenderer(this, 22, 0);
		this.legs.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F);
		this.legs.setPos(-2.0F, 16.0F, 1.0F);

		this.legs2 = new ModelRenderer(this, 22, 0);
		this.legs2.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F);
		this.legs2.setPos(2.0F, 16.0F, 1.0F);

		this.wings = new ModelRenderer(this, 52, 0);
		this.wings.addBox(-1.01F, -0.0F, -1.0F, 1.0F, 8.0F, 4.0F, 0.0F);
		this.wings.setPos(-3.0F, 16.0F, 2.0F);

		this.wings2 = new ModelRenderer(this, 52, 0);
		this.wings2.addBox(0.01F, -0.0F, -1.0F, 1.0F, 8.0F, 4.0F, 0.0F);
		this.wings2.setPos(3.0F, 12.0F, 0.0F);

		this.neck = new ModelRenderer(this, 44, 0);
		this.neck.addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F);
		this.neck.setPos(0.0F, 14.0F, -4.0F);

		this.feather1 = new ModelRenderer(this, 30, 0);
		this.feather1.addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, -0.3F);
		this.feather1.setPos(0.0F, 15.0F, 1.0F);

		this.feather2 = new ModelRenderer(this, 30, 0);
		this.feather2.addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, -0.3F);
		this.feather2.setPos(0.0F, 15.0F, 1.0F);

		this.feather3 = new ModelRenderer(this, 30, 0);
		this.feather3.addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, -0.3F);
		this.feather3.setPos(0.0F, 15.0F, 1.0F);

		this.feather1.y += 0.5F;
		this.feather2.y += 0.5F;
		this.feather3.y += 0.5F;
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
		this.jaw.xRot = head.xRot;
		this.jaw.yRot = head.yRot;

		this.body.xRot = (float) (Math.PI / 2.0F);

		this.legs.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.legs2.xRot = MathHelper.cos((float) (limbSwing * 0.6662F + Math.PI)) * 1.4F * limbSwingAmount;

		if (ageInTicks > 0.001F) {
			this.wings.z = -1.0F;
			this.wings2.z = -1.0F;
			this.wings.y = 12.0F;
			this.wings2.y = 12.0F;
			this.wings.xRot = 0.0F;
			this.wings2.xRot = 0.0F;
			this.wings.zRot = ageInTicks;
			this.wings2.zRot = -ageInTicks;
			this.legs.xRot = 0.6F;
			this.legs2.xRot = 0.6F;
		} else {
			this.wings.z = -3.0F;
			this.wings2.z = -3.0F;
			this.wings.y = 14.0F;
			this.wings2.y = 14.0F;
			this.wings.xRot = (float) (Math.PI / 2.0F);
			this.wings2.xRot = (float) (Math.PI / 2.0F);
			this.wings.zRot = 0.0F;
			this.wings2.zRot = 0.0F;
		}

		if (moa.isSitting()) {
			this.head.setPos(0.0F, 16.0F, -4.0F);
			this.jaw.setPos(0.0F, 16.0F, -4.0F);
			this.body.setPos(0.0F, 24.0F, 0.0F);
			this.legs.setPos(-2.0F, 24.0F, 1.0F);
			this.legs2.setPos(2.0F, 24.0F, 1.0F);
			this.neck.setPos(0.0F, 22.0F, -4.0F);
			this.feather1.setPos(0.0F, 23.0F, 1.0F);
			this.feather2.setPos(0.0F, 23.0F, 1.0F);
			this.feather3.setPos(0.0F, 23.0F, 1.0F);

			this.jaw.xRot = -0.3F;
			this.head.xRot = 0.0F;

			this.wings.y = 22.0F;
			this.wings2.y = 22.0F;
		} else {
			this.head.setPos(0.0F, 8.0F, -4.0F);
			this.jaw.setPos(0.0F, 8.0F, -4.0F);
			this.body.setPos(0.0F, 16.0F, 0.0F);
			this.legs.setPos(-2.0F, 16.0F, 1.0F);
			this.legs2.setPos(2.0F, 16.0F, 1.0F);
			this.neck.setPos(0.0F, 14.0F, -4.0F);
			this.feather1.setPos(0.0F, 15.0F, 1.0F);
			this.feather2.setPos(0.0F, 15.0F, 1.0F);
			this.feather3.setPos(0.0F, 15.0F, 1.0F);
		}

		this.feather1.xRot = 0.25F;
		this.feather1.yRot = -0.375F;

		this.feather2.xRot = 0.25F;
		this.feather2.yRot = 0.0F;

		this.feather3.xRot = 0.25F;
		this.feather3.yRot = 0.375F;

		this.neck.xRot = 0.0F;
		this.neck.yRot = head.yRot;
		this.jaw.xRot += 0.35F;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (this.renderLegs) {
    		this.legs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    		this.legs2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    	}
        this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.jaw.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wings.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wings2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}
