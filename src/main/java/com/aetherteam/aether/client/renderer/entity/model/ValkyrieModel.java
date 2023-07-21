package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.monster.dungeon.AbstractValkyrie;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ValkyrieModel<T extends AbstractValkyrie> extends HumanoidModel<T> {
    public ModelPart upperBody;
    public ModelPart rightShoulder;
    public ModelPart leftShoulder;
    public ModelPart rightFrontSkirt;
    public ModelPart leftFrontSkirt;
    public ModelPart rightBackSkirt;
    public ModelPart leftBackSkirt;
    public ModelPart rightSideSkirt;
    public ModelPart leftSideSkirt;
    public ModelPart swordHandle;
    public ModelPart swordHilt;
    public ModelPart swordBladeBase;
    public ModelPart swordBladeMiddle;
    public ModelPart swordBladeEnd;
    public ModelPart hair1;
    public ModelPart hair2;
    public ModelPart hair3;
    public ModelPart hair4;
    public ModelPart hair5;
    public ModelPart hair6;
    public ModelPart hair7;
    public ModelPart hair8;
    public ModelPart hair9;
    public ModelPart hair10;
    public ModelPart hair11;
    public ModelPart hair12;
    public ModelPart hair13;
    public ModelPart hair14;
    public ModelPart hair15;
    public ModelPart hair16;
    public ModelPart hair17;
    public ModelPart hair18;
    public ModelPart hair19;
    public ModelPart hair20;
    public ModelPart hair21;
    public ModelPart hair22;

    public ValkyrieModel(ModelPart root) {
        super(root);
        this.upperBody = this.body.getChild("upper_body");
        this.rightShoulder = this.rightArm.getChild("right_shoulder");
        this.leftShoulder = this.leftArm.getChild("left_shoulder");
        this.rightFrontSkirt = root.getChild("right_front_skirt");
        this.leftFrontSkirt = root.getChild("left_front_skirt");
        this.rightBackSkirt = root.getChild("right_back_skirt");
        this.leftBackSkirt = root.getChild("left_back_skirt");
        this.rightSideSkirt = root.getChild("right_side_skirt");
        this.leftSideSkirt = root.getChild("left_side_skirt");
        this.swordHandle = this.rightArm.getChild("sword_handle");
        this.swordHilt = this.swordHandle.getChild("sword_hilt");
        this.swordBladeBase = this.swordHandle.getChild("sword_blade_base");
        this.swordBladeMiddle = this.swordHandle.getChild("sword_blade_middle");
        this.swordBladeEnd = this.swordHandle.getChild("sword_blade_end");
        this.hair1 = this.head.getChild("hair_1");
        this.hair2 = this.head.getChild("hair_2");
        this.hair3 = this.head.getChild("hair_3");
        this.hair4 = this.head.getChild("hair_4");
        this.hair5 = this.head.getChild("hair_5");
        this.hair6 = this.head.getChild("hair_6");
        this.hair7 = this.head.getChild("hair_7");
        this.hair8 = this.head.getChild("hair_8");
        this.hair9 = this.head.getChild("hair_9");
        this.hair10 = this.head.getChild("hair_10");
        this.hair11 = this.head.getChild("hair_11");
        this.hair12 = this.head.getChild("hair_12");
        this.hair13 = this.head.getChild("hair_13");
        this.hair14 = this.head.getChild("hair_14");
        this.hair15 = this.head.getChild("hair_15");
        this.hair16 = this.head.getChild("hair_16");
        this.hair17 = this.head.getChild("hair_17");
        this.hair18 = this.head.getChild("hair_18");
        this.hair19 = this.head.getChild("hair_19");
        this.hair20 = this.head.getChild("hair_20");
        this.hair21 = this.head.getChild("hair_21");
        this.hair22 = this.head.getChild("hair_22");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 16).addBox(-3.0F, 0.0F, -1.5F, 6.0F, 12.0F, 3.0F), PartPose.ZERO);
        body.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(12, 16).addBox(-3.0F, 0.5F, -1.25F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.75F)), PartPose.ZERO);
        PartDefinition rightArm = partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(30, 16).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offsetAndRotation(-4.0F, 1.5F, 0.0F, 0.0F, 0.0F, 0.05F));
        rightArm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(30, 16).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.75F)), PartPose.ZERO);
        PartDefinition leftArm = partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(30, 16).mirror().addBox(-1.0F, -1.5F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offsetAndRotation(5.0F, 1.5F, 0.0F, 0.0F, 0.0F, -0.05F));
        leftArm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(30, 16).mirror().addBox(-1.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.75F)), PartPose.ZERO);
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(-1.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_front_skirt", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 3.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(-3.0F, 9.0F, -1.5F, -0.2F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_front_skirt", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 3.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 9.0F, -1.5F, -0.2F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_back_skirt", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(-3.0F, 9.0F, 1.5F, 0.2F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_back_skirt", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 9.0F, 1.5F, 0.2F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_side_skirt", CubeListBuilder.create().texOffs(55, 19).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 3.01F), PartPose.offsetAndRotation(-3.0F, 9.0F, -1.505F, 0.0F, 0.0F, 0.2F));
        partDefinition.addOrReplaceChild("left_side_skirt", CubeListBuilder.create().texOffs(55, 19).addBox(0.0F, 0.0F, 0.0F, 1.0F, 6.0F, 3.01F), PartPose.offsetAndRotation(3.0F, 9.0F, -1.505F, 0.0F, 0.0F, -0.2F));
        PartDefinition swordHandle = rightArm.addOrReplaceChild("sword_handle", CubeListBuilder.create().texOffs(9, 16).addBox(-2.5F, 8.0F, 1.5F, 2.0F, 2.0F, 1.0F), PartPose.ZERO);
        swordHandle.addOrReplaceChild("sword_hilt", CubeListBuilder.create().texOffs(32, 10).addBox(-3.0F, 6.5F, -2.75F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        swordHandle.addOrReplaceChild("sword_blade_base", CubeListBuilder.create().texOffs(42, 18).addBox(-2.0F, 7.5F, -12.5F, 1.0F, 3.0F, 10.0F), PartPose.ZERO);
        swordHandle.addOrReplaceChild("sword_blade_middle", CubeListBuilder.create().texOffs(42, 18).addBox(-2.0F, 7.5F, -22.5F, 1.0F, 3.0F, 10.0F), PartPose.ZERO);
        swordHandle.addOrReplaceChild("sword_blade_end", CubeListBuilder.create().texOffs(28, 17).addBox(-2.0F, 8.5F, -23.5F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_1", CubeListBuilder.create().texOffs(42, 17).addBox(-5.0F, -7.0F, -4.0F, 1.0F, 3.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_2", CubeListBuilder.create().texOffs(43, 17).addBox(4.0F, -7.0F, -4.0F, 1.0F, 3.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_3", CubeListBuilder.create().texOffs(44, 17).addBox(-5.0F, -7.0F, -3.0F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_4", CubeListBuilder.create().texOffs(45, 17).addBox(4.0F, -7.0F, -3.0F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_5", CubeListBuilder.create().texOffs(46, 17).addBox(-5.0F, -7.0F, -2.0F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_6", CubeListBuilder.create().texOffs(47, 17).addBox(4.0F, -7.0F, -2.0F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_7", CubeListBuilder.create().texOffs(48, 17).addBox(-5.0F, -7.0F, -1.0F, 1.0F, 5.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_8", CubeListBuilder.create().texOffs(42, 17).addBox(4.0F, -7.0F, -1.0F, 1.0F, 5.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_9", CubeListBuilder.create().texOffs(43, 17).addBox(-5.0F, -7.0F, 0.0F, 1.0F, 5.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_10", CubeListBuilder.create().texOffs(44, 17).addBox(4.0F, -7.0F, 0.0F, 1.0F, 5.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_11", CubeListBuilder.create().texOffs(45, 17).addBox(-5.0F, -7.0F, 1.0F, 1.0F, 6.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_12", CubeListBuilder.create().texOffs(46, 17).addBox(4.0F, -7.0F, 1.0F, 1.0F, 6.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_13", CubeListBuilder.create().texOffs(47, 17).addBox(-5.0F, -7.0F, 2.0F, 1.0F, 7.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_14", CubeListBuilder.create().texOffs(48, 17).addBox(4.0F, -7.0F, 2.0F, 1.0F, 7.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_15", CubeListBuilder.create().texOffs(42, 17).addBox(-5.0F, -7.0F, 3.0F, 1.0F, 8.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_16", CubeListBuilder.create().texOffs(43, 17).addBox(4.0F, -7.0F, 3.0F, 1.0F, 8.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_17", CubeListBuilder.create().texOffs(44, 17).addBox(-4.0F, -7.0F, 4.0F, 1.0F, 9.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_18", CubeListBuilder.create().texOffs(45, 17).addBox(3.0F, -7.0F, 4.0F, 1.0F, 9.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_19", CubeListBuilder.create().texOffs(42, 17).addBox(-3.0F, -7.0F, 4.0F, 3.0F, 10.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_20", CubeListBuilder.create().texOffs(42, 17).addBox(0.0F, -7.0F, 4.0F, 3.0F, 10.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_21", CubeListBuilder.create().texOffs(48, 17).addBox(-1.0F, -7.0F, -5.0F, 1.0F, 2.0F, 1.0F), PartPose.ZERO);
        head.addOrReplaceChild("hair_22", CubeListBuilder.create().texOffs(42, 17).addBox(0.0F, -7.0F, -5.0F, 1.0F, 3.0F, 1.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(T valkyrie, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);

        this.rightArm.x = -4.0F;
        this.rightArm.z = 0.0F;
        this.leftArm.x = 5.0F;
        this.leftArm.z = 0.0F;

        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;

        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        this.rightArm.yRot = 0.0F;
        this.leftArm.yRot = 0.0F;

        this.setupAttackAnimation(valkyrie, ageInTicks);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightFrontSkirt.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftFrontSkirt.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightBackSkirt.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftBackSkirt.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightSideSkirt.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftSideSkirt.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
