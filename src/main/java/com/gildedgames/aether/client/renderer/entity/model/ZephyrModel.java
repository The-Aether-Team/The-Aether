package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZephyrModel extends BaseZephyrModel
{
    public ModelPart leftFace;
    public ModelPart rightFace;
    public ModelPart mouth;
    public ModelPart body;
    public ModelPart bodyLeftSide1;
    public ModelPart bodyLeftSide2;
    public ModelPart bodyRightSide1;
    public ModelPart bodyRightSide2;
    public ModelPart cloudButt;
    public ModelPart tail1;
    public ModelPart tail2;
    public ModelPart tail3;

    public ZephyrModel(ModelPart model) {
        this.leftFace = model.getChild("left_face");
        this.rightFace = model.getChild("right_face");
        this.mouth = model.getChild("mouth");
        this.body = model.getChild("body");
        this.bodyLeftSide1 = model.getChild("body_left_side_1");
        this.bodyLeftSide2 = model.getChild("body_left_side_2");
        this.bodyRightSide1 = model.getChild("body_right_side_1");
        this.bodyRightSide2 = model.getChild("body_right_side_2");
        this.cloudButt = model.getChild("cloud_butt");
        this.tail1 = model.getChild("tail_1");
        this.tail2 = tail1.getChild("tail_2");
        this.tail3 = tail2.getChild("tail_3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("left_face", CubeListBuilder.create().texOffs(67, 11).addBox(3.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F).mirror(), PartPose.offset(0.0F, 8.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_face", CubeListBuilder.create().texOffs(67, 11).addBox(-7.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        partdefinition.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(66, 19).addBox(-3.0F, 1.0F, -8.0F, 6.0F, 3.0F, 1.0F).mirror(), PartPose.offset(0.0F, 8.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(27, 9).addBox(-6.0F, -4.0F, -7.0F, 12.0F, 9.0F, 14.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        partdefinition.addOrReplaceChild("body_left_side_1", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F), PartPose.offset(6.0F, 8.0F, -4.0F));
        partdefinition.addOrReplaceChild("body_left_side_2", CubeListBuilder.create().texOffs(25, 11).addBox(0.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F), PartPose.offset(5.5F, 9.0F, 2.0F));
        partdefinition.addOrReplaceChild("body_right_side_1", CubeListBuilder.create().texOffs(0, 20).addBox(-2.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F).mirror(), PartPose.offset(-6.0F, 8.0F, -4.0F));
        partdefinition.addOrReplaceChild("body_right_side_2", CubeListBuilder.create().texOffs(25, 11).addBox(-2.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F).mirror(), PartPose.offset(-5.5F, 9.0F, 2.0F));
        partdefinition.addOrReplaceChild("cloud_butt", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, 0.0F, 8.0F, 6.0F, 2.0F).mirror(), PartPose.offset(2.0F, 8.0F, 7.0F));
        PartDefinition tail1 = partdefinition.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(96, 22).addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5), PartPose.offset(0.0F, 0.0F, 12.4F));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(80, 24).addBox(-2.0F, -2.0F, -1.966667F, 4, 4, 4), PartPose.offset(0.0F, 0.0F, 6.0F));
        tail2.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(84, 18).addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3), PartPose.offset(0.0F, 0.0F, 5.0F));
        return LayerDefinition.create(meshdefinition, 128, 32);
    }

    @Override
    public void setupAnim(Zephyr entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
