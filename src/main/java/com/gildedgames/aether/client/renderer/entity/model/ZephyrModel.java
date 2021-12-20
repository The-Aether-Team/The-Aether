package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.ZephyrEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZephyrModel extends BaseZephyrModel
{
    public ModelPart leftFace;
    public ModelPart bodyRightSide2;
    public ModelPart mouth;
    public ModelPart cloudButt;
    public ModelPart rightFace;
    public ModelPart bodyLeftSide1;
    public ModelPart bodyLeftSide2;
    public ModelPart body;
    public ModelPart bodyRightSide1;
    public ModelPart tail1;
    public ModelPart tail2;
    public ModelPart tail3;

    public ZephyrModel() {
        texWidth = 128;
        texHeight = 32;

        leftFace = new ModelPart(this);
        leftFace.setPos(0.0F, 8.0F, 0.0F);
        leftFace.texOffs(67, 11).addBox(3.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F, 0.0F, true);

        bodyRightSide2 = new ModelPart(this);
        bodyRightSide2.setPos(-5.5F, 9.0F, 2.0F);
        bodyRightSide2.texOffs(25, 11).addBox(-2.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F, 0.0F, true);

        mouth = new ModelPart(this);
        mouth.setPos(0.0F, 8.0F, 0.0F);
        mouth.texOffs(66, 19).addBox(-3.0F, 1.0F, -8.0F, 6.0F, 3.0F, 1.0F, 0.0F, true);

        cloudButt = new ModelPart(this);
        cloudButt.setPos(2.0F, 8.0F, 7.0F);
        cloudButt.texOffs(0, 0).addBox(-6.0F, -3.0F, 0.0F, 8.0F, 6.0F, 2.0F, 0.0F, true);

        rightFace = new ModelPart(this);
        rightFace.setPos(0.0F, 8.0F, 0.0F);
        rightFace.texOffs(67, 11).addBox(-7.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F, 0.0F, false);

        bodyLeftSide1 = new ModelPart(this);
        bodyLeftSide1.setPos(6.0F, 8.0F, -4.0F);
        bodyLeftSide1.texOffs(0, 20).addBox(0.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);

        bodyLeftSide2 = new ModelPart(this);
        bodyLeftSide2.setPos(5.5F, 9.0F, 2.0F);
        bodyLeftSide2.texOffs(25, 11).addBox(0.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F, 0.0F, false);

        body = new ModelPart(this);
        body.setPos(0.0F, 8.0F, 0.0F);
        body.texOffs(27, 9).addBox(-6.0F, -4.0F, -7.0F, 12.0F, 9.0F, 14.0F, 0.0F, false);

        bodyRightSide1 = new ModelPart(this);
        bodyRightSide1.setPos(-6.0F, 8.0F, -4.0F);
        bodyRightSide1.texOffs(0, 20).addBox(-2.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, 0.0F, true);

        tail1 = new ModelPart(this);
        tail1.setPos(0.0F, 0.0F, 12.4F);
        tail1.texOffs(96, 22);
        tail1.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F, false);

        tail2 = new ModelPart(this);
        tail2.setPos(0.0F, 0.0F, 6.0F);
        tail1.addChild(tail2);
        tail2.texOffs(80, 24);
        tail2.addBox(-2.0F, -2.0F, -1.966667F, 4, 4, 4, 0.0F, false);

        tail3 = new ModelPart(this);
        tail3.setPos(0.0F, 0.0F, 5.0F);
        tail2.addChild(tail3);
        tail3.texOffs(84, 18);
        tail3.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F, false);
    }

    @Override
    public void setupAnim(ZephyrEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float motion = (float)(Math.sin(limbSwing * 20 / 57.2957795) * limbSwingAmount * 0.5F);

        this.leftFace.y = motion + 8;
        this.leftFace.x = motion * 0.5F;

        this.bodyLeftSide1.y = 8 - motion * 0.5F;
        this.bodyLeftSide2.y = 9 + motion * 0.5F;

        this.rightFace.y = 8 - motion;
        this.rightFace.x = -motion * 0.5F;

        this.bodyRightSide1.y = 8 - motion * 0.5F;
        this.bodyRightSide2.y = 9 + motion * 0.5F;

        this.tail1.x = (float)(Math.sin(limbSwing  * 20 / 57.2957795) * limbSwingAmount * 0.75F);
        this.tail1.yRot = (float)(Math.sin(limbSwing * 0.5F / 57.2957795) * limbSwingAmount * 0.75F);
        this.tail1.y =  8 - motion;

        this.tail2.x = (float)(Math.sin(limbSwing * 15 / 57.2957795) * limbSwingAmount * 0.85F);
        this.tail2.y =  motion * 1.25F;
        this.tail2.yRot = this.tail1.yRot + 0.25F;

        this.tail3.x = (float)(Math.sin(limbSwing * 10 / 57.2957795) * limbSwingAmount * 0.95F);
        this.tail3.y =- motion;
        this.tail3.yRot = this.tail2.yRot + 0.35F;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        leftFace.render(matrixStack, buffer, packedLight, packedOverlay);
        bodyRightSide2.render(matrixStack, buffer, packedLight, packedOverlay);
        mouth.render(matrixStack, buffer, packedLight, packedOverlay);
        cloudButt.render(matrixStack, buffer, packedLight, packedOverlay);
        rightFace.render(matrixStack, buffer, packedLight, packedOverlay);
        bodyLeftSide1.render(matrixStack, buffer, packedLight, packedOverlay);
        bodyLeftSide2.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        bodyRightSide1.render(matrixStack, buffer, packedLight, packedOverlay);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
