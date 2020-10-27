package com.aether.client.renderer.entity.model;

import com.aether.entity.monster.ZephyrEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ZephyrModel extends EntityModel<ZephyrEntity> {
    private final ModelRenderer LeftFace;
    private final ModelRenderer BodyRightSide2;
    private final ModelRenderer Mouth;
    private final ModelRenderer CloudButt;
    private final ModelRenderer RightFace;
    private final ModelRenderer BodyLeftSide1;
    private final ModelRenderer BodyLeftSide2;
    private final ModelRenderer Body;
    private final ModelRenderer BodyRightSide1;
    private final ModelRenderer Tail1;
    private final ModelRenderer Tail2;
    private final ModelRenderer Tail3;

    public ZephyrModel() {
        textureWidth = 128;
        textureHeight = 32;

        LeftFace = new ModelRenderer(this);
        LeftFace.setRotationPoint(0.0F, 8.0F, 0.0F);
        LeftFace.setTextureOffset(67, 11).addBox(3.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F, 0.0F, true);

        BodyRightSide2 = new ModelRenderer(this);
        BodyRightSide2.setRotationPoint(-5.5F, 9.0F, 2.0F);
        BodyRightSide2.setTextureOffset(25, 11).addBox(-2.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F, 0.0F, false);

        Mouth = new ModelRenderer(this);
        Mouth.setRotationPoint(0.0F, 8.0F, 0.0F);
        Mouth.setTextureOffset(66, 19).addBox(-3.0F, 1.0F, -8.0F, 6.0F, 3.0F, 1.0F, 0.0F, true);

        CloudButt = new ModelRenderer(this);
        CloudButt.setRotationPoint(2.0F, 8.0F, 7.0F);
        CloudButt.setTextureOffset(0, 0).addBox(-6.0F, -3.0F, 0.0F, 8.0F, 6.0F, 2.0F, 0.0F, true);

        RightFace = new ModelRenderer(this);
        RightFace.setRotationPoint(0.0F, 8.0F, 0.0F);
        RightFace.setTextureOffset(67, 11).addBox(-7.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F, 0.0F, false);

        BodyLeftSide1 = new ModelRenderer(this);
        BodyLeftSide1.setRotationPoint(6.0F, 8.0F, -4.0F);
        BodyLeftSide1.setTextureOffset(0, 20).addBox(0.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, 0.0F, true);

        BodyLeftSide2 = new ModelRenderer(this);
        BodyLeftSide2.setRotationPoint(5.5F, 9.0F, 2.0F);
        BodyLeftSide2.setTextureOffset(25, 11).addBox(0.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F, 0.0F, true);

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 8.0F, 0.0F);
        Body.setTextureOffset(27, 9).addBox(-6.0F, -4.0F, -7.0F, 12.0F, 9.0F, 14.0F, 0.0F, false);

        BodyRightSide1 = new ModelRenderer(this);
        BodyRightSide1.setRotationPoint(-6.0F, 8.0F, -4.0F);
        BodyRightSide1.setTextureOffset(0, 20).addBox(-2.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);

        Tail1 = new ModelRenderer(this);
        Tail1.setRotationPoint(0.0F, 0.0F, 12.4F);
        Tail1.setTextureOffset(96, 22).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, false);

        Tail2 = new ModelRenderer(this);
        Tail2.setRotationPoint(0.0F, 0.0F, 0.0F);
        Tail1.addChild(Tail2);
        Tail2.setTextureOffset(80, 24).addBox(-2.0F, -2.0F, 4.0333F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        Tail3 = new ModelRenderer(this);
        Tail3.setRotationPoint(0.0F, 0.0F, 0.0F);
        Tail2.addChild(Tail3);
        Tail3.setTextureOffset(84, 18).addBox(-1.5F, -1.5F, 9.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(ZephyrEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float motion = (float)(Math.sin(limbSwing * 20 / 57.2957795) * limbSwingAmount * .5F);

        this.LeftFace.rotationPointY = motion + 8;
        this.LeftFace.rotationPointX = motion * 0.5F;

        this.BodyLeftSide1.rotationPointY = 8 - motion * 0.5F;
        this.BodyLeftSide2.rotationPointY = 9 + motion * 0.5F;

        this.RightFace.rotationPointY= 8 - motion;
        this.RightFace.rotationPointX = -motion * 0.5F;

        this.BodyRightSide1.rotationPointY = 8 - motion * 0.5F;
        this.BodyRightSide2.rotationPointY = 9 + motion * 0.5F;

        this.Tail1.rotationPointX = (float)(Math.sin(limbSwing * 20 / 57.2957795) * limbSwingAmount * 0.75F);
        this.Tail1.rotationPointY =  8 - motion;
        this.Tail1.rotateAngleY = (float)(Math.sin(limbSwing * 0.5F / 57.2957795) * limbSwingAmount * 0.75F);

        this.Tail2.rotationPointX = (float)(Math.sin(limbSwing * 15 / 57.2957795) * limbSwingAmount * 0.85F);
        this.Tail2.rotationPointY =  motion * 1.25F;
        this.Tail2.rotateAngleY = this.Tail1.rotateAngleY + 0.25F;

        this.Tail3.rotationPointX = (float)(Math.sin(limbSwing * 10 / 57.2957795) * limbSwingAmount * 0.95F);
        this.Tail3.rotationPointY = -motion;
        this.Tail3.rotateAngleY = this.Tail2.rotateAngleY + 0.35F;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        LeftFace.render(matrixStack, buffer, packedLight, packedOverlay);
        BodyRightSide2.render(matrixStack, buffer, packedLight, packedOverlay);
        Mouth.render(matrixStack, buffer, packedLight, packedOverlay);
        CloudButt.render(matrixStack, buffer, packedLight, packedOverlay);
        RightFace.render(matrixStack, buffer, packedLight, packedOverlay);
        BodyLeftSide1.render(matrixStack, buffer, packedLight, packedOverlay);
        BodyLeftSide2.render(matrixStack, buffer, packedLight, packedOverlay);
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        BodyRightSide1.render(matrixStack, buffer, packedLight, packedOverlay);
        Tail1.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
