package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.ZephyrEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
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
        texWidth = 128;
        texHeight = 32;

        LeftFace = new ModelRenderer(this);
        LeftFace.setPos(0.0F, 8.0F, 0.0F);
        LeftFace.texOffs(67, 11).addBox(3.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F, 0.0F, true);

        BodyRightSide2 = new ModelRenderer(this);
        BodyRightSide2.setPos(-5.5F, 9.0F, 2.0F);
        BodyRightSide2.texOffs(25, 11).addBox(-2.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F, 0.0F, true);

        Mouth = new ModelRenderer(this);
        Mouth.setPos(0.0F, 8.0F, 0.0F);
        Mouth.texOffs(66, 19).addBox(-3.0F, 1.0F, -8.0F, 6.0F, 3.0F, 1.0F, 0.0F, true);

        CloudButt = new ModelRenderer(this);
        CloudButt.setPos(2.0F, 8.0F, 7.0F);
        CloudButt.texOffs(0, 0).addBox(-6.0F, -3.0F, 0.0F, 8.0F, 6.0F, 2.0F, 0.0F, true);

        RightFace = new ModelRenderer(this);
        RightFace.setPos(0.0F, 8.0F, 0.0F);
        RightFace.texOffs(67, 11).addBox(-7.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F, 0.0F, false);

        BodyLeftSide1 = new ModelRenderer(this);
        BodyLeftSide1.setPos(6.0F, 8.0F, -4.0F);
        BodyLeftSide1.texOffs(0, 20).addBox(0.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);

        BodyLeftSide2 = new ModelRenderer(this);
        BodyLeftSide2.setPos(5.5F, 9.0F, 2.0F);
        BodyLeftSide2.texOffs(25, 11).addBox(0.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F, 0.0F, false);

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 8.0F, 0.0F);
        Body.texOffs(27, 9).addBox(-6.0F, -4.0F, -7.0F, 12.0F, 9.0F, 14.0F, 0.0F, false);

        BodyRightSide1 = new ModelRenderer(this);
        BodyRightSide1.setPos(-6.0F, 8.0F, -4.0F);
        BodyRightSide1.texOffs(0, 20).addBox(-2.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, 0.0F, true);

        Tail1 = new ModelRenderer(this);
        Tail1.setPos(0.0F, 0.0F, 12.4F);
        Tail1.texOffs(96, 22);
        Tail1.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F, false);

        Tail2 = new ModelRenderer(this);
        Tail2.setPos(0.0F, 0.0F, 6.0F);
        Tail1.addChild(Tail2);
        Tail2.texOffs(80, 24);
        Tail2.addBox(-2.0F, -2.0F, -1.966667F, 4, 4, 4, 0.0F, false);

        Tail3 = new ModelRenderer(this);
        Tail3.setPos(0.0F, 0.0F, 5.0F);
        Tail2.addChild(Tail3);
        Tail3.texOffs(84, 18);
        Tail3.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F, false);
    }

    @Override
    public void setupAnim(ZephyrEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float motion = (float)(Math.sin(limbSwing * 20 / 57.2957795) * limbSwingAmount * 0.5F);

        this.LeftFace.y = motion + 8;
        this.LeftFace.x = motion * 0.5F;

        this.BodyLeftSide1.y = 8 - motion * 0.5F;
        this.BodyLeftSide2.y = 9 + motion * 0.5F;

        this.RightFace.y = 8 - motion;
        this.RightFace.x = -motion * 0.5F;

        this.BodyRightSide1.y = 8 - motion * 0.5F;
        this.BodyRightSide2.y = 9 + motion * 0.5F;

        this.Tail1.x = (float)(Math.sin(limbSwing  * 20 / 57.2957795) * limbSwingAmount * 0.75F);
        this.Tail1.yRot = (float)(Math.sin(limbSwing * 0.5F / 57.2957795) * limbSwingAmount * 0.75F);
        this.Tail1.y =  8 - motion;

        this.Tail2.x = (float)(Math.sin(limbSwing * 15 / 57.2957795) * limbSwingAmount * 0.85F);
        this.Tail2.y =  motion * 1.25F;
        this.Tail2.yRot = this.Tail1.yRot + 0.25F;

        this.Tail3.x = (float)(Math.sin(limbSwing * 10 / 57.2957795) * limbSwingAmount * 0.95F);
        this.Tail3.y =- motion;
        this.Tail3.yRot = this.Tail2.yRot + 0.35F;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
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
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
