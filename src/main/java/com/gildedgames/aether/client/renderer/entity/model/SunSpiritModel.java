package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SunSpiritModel extends BipedModel<MobEntity>
{
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer rightArm2;
    public ModelRenderer rightArm3;
    public ModelRenderer leftArm2;
    public ModelRenderer leftArm3;

    public SunSpiritModel() {
        this(0.0F);
    }

    public SunSpiritModel(float size) {
        this(size, 0.0F);
    }

    public SunSpiritModel(float size, float rotationPointY) {
        super(rotationPointY);
        leftArmPose = rightArmPose = ArmPose.EMPTY;
        crouching = false;

        head = new ModelRenderer(this, 0, 0);
        head.setPos(0.0F, rotationPointY, 0.0F);
        head.addBox(-4.0F, -8.0F, -3.0F, 8.0F, 5.0F, 7.0F, size);

        hat = new ModelRenderer(this, 32, 0);
        hat.setPos(0.0F, rotationPointY, 0.0F);
        hat.addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F, size);

        body = new ModelRenderer(this, 0, 12);
        body.setPos(0.0F, rotationPointY, 0.0F);
        body.addBox(-5.0F, 0.0F, -2.5F, 10.0F, 6.0F, 5.0F, size);

        body2 = new ModelRenderer(this, 0, 23);
        body2.setPos(0.0F, rotationPointY, 0.0F);
        body2.addBox(-4.5F, 6.0F, -2.0F, 9.0F, 5.0F, 4.0F, size);

        body3 = new ModelRenderer(this, 30, 27);
        body3.setPos(0.0F, rotationPointY, 0.0F);
        body3.addBox(-4.5F, 11.0F, -2.0F, 5.0F, 1.0F, 4.0F, size + 0.5F);

        body4 = new ModelRenderer(this, 30, 27);
        body4.setPos(0.0F, rotationPointY, 0.0F);
        body4.addBox(-0.5F, 11.0F, -2.0F, 5.0F, 1.0F, 4.0F, size + 0.5F);

        rightArm = new ModelRenderer(this, 30, 11);
        rightArm.setPos(-8.0F, 2.0F + rotationPointY, 0.0F);
        rightArm.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, size + 0.5F);
        
        rightArm2 = new ModelRenderer(this, 30, 11);
        rightArm2.setPos(-8.0F, 2.0F + rotationPointY, 0.0F);
        rightArm2.addBox(-2.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F, size);

        rightArm3 = new ModelRenderer(this, 30, 26);
        rightArm3.setPos(-8.0F, 2.0F + rotationPointY, 0.0F);
        rightArm3.addBox(-2.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, size + 0.25F);

        leftArm = new ModelRenderer(this, 30, 11);
        leftArm.mirror = true;
        leftArm.setPos(8.0F, 2.0F + rotationPointY, 0.0F);
        leftArm.addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, size + 0.5F);

        leftArm2 = new ModelRenderer(this, 30, 11);
        leftArm2.mirror = true;
        leftArm2.setPos(8.0F, 2.0F + rotationPointY, 0.0F);
        leftArm2.addBox(-2.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F, size);

        leftArm3 = new ModelRenderer(this, 30, 11);
        leftArm3.mirror = true;
        leftArm3.setPos(8.0F, 2.0F + rotationPointY, 0.0F);
        leftArm3.addBox(-2.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, size + 0.25F);
    }

    @Override
    public void setupAnim(MobEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = hat.xRot = headPitch * 0.017453292F;
        head.yRot = hat.yRot = netHeadYaw * 0.017453292F;

        leftArm.xRot = -(rightArm.xRot = MathHelper.sin(ageInTicks * 0.067F) * 0.05F);
        leftArm.yRot = rightArm.yRot = 0.0F;
        leftArm.zRot = -(rightArm.zRot = MathHelper.cos(ageInTicks * 0.09F) * 0.05F - 0.05F);

        body4.xRot = body3.xRot = body2.xRot = body.xRot;
        body4.yRot = body3.yRot = body2.yRot = body.yRot;

        leftArm3.xRot = leftArm2.xRot = -leftArm.xRot;
        leftArm3.yRot = leftArm2.yRot = -leftArm.yRot;
        leftArm3.zRot = leftArm2.zRot = -leftArm.zRot;

        rightArm3.xRot = rightArm2.xRot = -rightArm.xRot;
        rightArm3.yRot = rightArm2.yRot = -rightArm.yRot;
        rightArm3.zRot = rightArm2.zRot = -rightArm.zRot;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        hat.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        body2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        body3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        body4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightArm2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightArm3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leftArm2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leftArm3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
