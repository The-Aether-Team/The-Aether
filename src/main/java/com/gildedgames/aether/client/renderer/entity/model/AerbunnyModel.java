package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class AerbunnyModel extends EntityModel<AerbunnyEntity>
{
    public ModelRenderer head;
    public ModelRenderer rightEar;
    public ModelRenderer leftEar;
    public ModelRenderer rightWhiskers;
    public ModelRenderer leftWhiskers;
    public ModelRenderer body;
    public ModelRenderer puff;
    public ModelRenderer tail;
    public ModelRenderer rightFrontLeg;
    public ModelRenderer leftFrontLeg;
    public ModelRenderer rightBackLeg;
    public ModelRenderer leftBackLeg;
    public float puffiness;

    public AerbunnyModel() {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.0F, -1.0F, -4.0F, 4.0F, 4.0F, 6.0F, 0.0F);
        this.head.setPos(0.0F, 15.0F, -4.0F);

        this.rightEar = new ModelRenderer(this, 14, 0);
        this.rightEar.addBox(-2.0F, -5.0F, -3.0F, 1.0F, 4.0F, 2.0F, 0.0F);
        this.head.addChild(this.rightEar);

        this.leftEar = new ModelRenderer(this, 14, 0);
        this.leftEar.addBox(1.0F, -5.0F, -3.0F, 1.0F, 4.0F, 2.0F, 0.0F);
        this.head.addChild(this.leftEar);

        this.rightWhiskers = new ModelRenderer(this, 20, 0);
        this.rightWhiskers.addBox(-4.0F, 0.0F, -3.0F, 2.0F, 3.0F, 2.0F, 0.0F);
        this.head.addChild(this.rightWhiskers);

        this.leftWhiskers = new ModelRenderer(this, 20, 0);
        this.leftWhiskers.addBox(2.0F, 0.0F, -3.0F, 2.0F, 3.0F, 2.0F, 0.0F);
        this.head.addChild(this.leftWhiskers);

        this.body = new ModelRenderer(this, 0, 10);
        this.body.addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F);
        this.body.setPos(0.0F, 16.0F, 0.0F);

        this.puff = new ModelRenderer(this, 29, 0);
        this.puff.addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, 0.0F);

        this.tail = new ModelRenderer(this, 0, 24);
        this.tail.addBox(-2.0F, 4.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F);
        this.body.addChild(this.tail);

        this.rightFrontLeg = new ModelRenderer(this, 24, 16);
        this.rightFrontLeg.addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F);
        this.rightFrontLeg.setPos(-3.0F, -3.0F, -3.0F);
        this.body.addChild(this.rightFrontLeg);

        this.leftFrontLeg = new ModelRenderer(this, 24, 16);
        this.leftFrontLeg.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F);
        this.leftFrontLeg.setPos(3.0F, -3.0F, -3.0F);
        this.body.addChild(this.leftFrontLeg);

        this.rightBackLeg = new ModelRenderer(this, 16, 24);
        this.rightBackLeg.addBox(0.0F, 0.0F, -4.0F, 2.0F, 2.0F, 4.0F);
        this.rightBackLeg.setPos(-3.0F, 4.0F, -3.0F);
        this.body.addChild(this.rightBackLeg);

        this.leftBackLeg = new ModelRenderer(this, 16, 24);
        this.leftBackLeg.addBox(-2.0F, 0.0F, -4.0F, 2.0F, 2.0F, 4.0F);
        this.leftBackLeg.setPos(3.0F, 4.0F, -3.0F);
        this.body.addChild(this.leftBackLeg);
    }

    @Override
    public void prepareMobModel(AerbunnyEntity aerbunny, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        super.prepareMobModel(aerbunny, p_212843_2_, p_212843_3_, p_212843_4_);
        this.puffiness = (float) (aerbunny.getVehicle() != null ? aerbunny.getPuffiness() : aerbunny.getPuffiness()) / 10.0F;
    }

    @Override
    public void setupAnim(AerbunnyEntity aerbunny, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.body.xRot = (float) Math.PI / 2F;
        this.puff.xRot = (float) Math.PI / 2F;
        this.rightFrontLeg.xRot = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount) - this.body.xRot;
        this.leftFrontLeg.xRot = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount) - this.body.xRot;
        this.rightBackLeg.xRot = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount) - this.body.xRot;
        this.leftBackLeg.xRot = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount) - this.body.xRot;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.pushPose();
        float a = 1.0F + this.puffiness * 0.5F;
        matrixStack.translate(0.0F, 1.0F, 0.0F);
        matrixStack.scale(a, a, a);
        this.puff.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.popPose();
    }
}
