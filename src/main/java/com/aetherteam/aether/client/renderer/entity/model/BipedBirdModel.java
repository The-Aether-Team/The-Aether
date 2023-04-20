package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.NotGrounded;
import com.aetherteam.aether.entity.WingedBird;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public abstract class BipedBirdModel<T extends Entity & WingedBird & NotGrounded> extends EntityModel<T> {
    public ModelPart head;
    public ModelPart jaw;
    public ModelPart neck;
    public ModelPart body;
    public ModelPart rightLeg;
    public ModelPart leftLeg;
    public ModelPart rightWing;
    public ModelPart leftWing;
    public ModelPart rightTailFeather;
    public ModelPart middleTailFeather;
    public ModelPart leftTailFeather;

    public BipedBirdModel(ModelPart root) {
        this.head = root.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.neck = this.head.getChild("neck");
        this.body = root.getChild("body");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.rightWing = this.body.getChild("right_wing");
        this.leftWing = this.body.getChild("left_wing");
        this.rightTailFeather = root.getChild("right_tail_feather");
        this.middleTailFeather = root.getChild("middle_tail_feather");
        this.leftTailFeather = root.getChild("left_tail_feather");
    }

    public static LayerDefinition createBodyLayer(CubeDeformation cube) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 8.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(0.0F, 8.0F, -4.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(-0.1F), 0.5F, 0.5F), PartPose.ZERO);
        head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs( 44, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.ZERO);
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 8.0F, 5.0F, cube, 0.5F, 0.5F), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, (float) (Math.PI / 2.0F), 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(-2.0F, 16.0F, 1.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(2.0F, 16.0F, 1.0F));
        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(52, 0).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 8.0F, 4.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(-3.001F, -3.0F, 3.0F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(52, 0).addBox(0.0F, 0.0F, -2.0F, 1.0F, 8.0F, 4.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(3.001F, -3.0F, 3.0F));
        partDefinition.addOrReplaceChild("right_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(-0.3F), 0.5F, 0.5F), PartPose.offsetAndRotation(0.0F, 17.5F, 1.0F, 0.25F, -0.375F, 0.0F));
        partDefinition.addOrReplaceChild("middle_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(-0.3F), 0.5F, 0.5F), PartPose.offsetAndRotation(0.0F, 17.5F, 1.0F, 0.25F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(-0.3F), 0.5F, 0.5F), PartPose.offsetAndRotation(0.0F, 17.5F, 1.0F, 0.25F, 0.375F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public void setupAnim(@Nonnull T bipedBird, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * (float) (Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0F);
        this.neck.xRot = -this.head.xRot;

        if (!bipedBird.isEntityOnGround()) {
            this.rightWing.setPos(-3.001F, 0.0F, 4.0F);
            this.leftWing.setPos(3.001F, 0.0F, 4.0F);
            this.rightWing.xRot = (float) -(Math.PI / 2.0F);
            this.leftWing.xRot = this.rightWing.xRot;
            this.rightLeg.xRot = 0.6F;
            this.leftLeg.xRot = this.rightLeg.xRot;
            this.rightWing.yRot = ageInTicks;
        } else {
            this.rightWing.setPos(-3.001F, -3.0F, 3.0F);
            this.leftWing.setPos(3.001F, -3.0F, 3.0F);
            this.rightWing.xRot = 0.0F;
            this.leftWing.xRot = 0.0F;
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftLeg.xRot = Mth.cos((float) (limbSwing * 0.6662F + Math.PI)) * 1.4F * limbSwingAmount;
            this.rightWing.yRot = 0.0F;
        }

        this.leftWing.yRot = -this.rightWing.yRot;
    }

    public float setupWingsAnimation(T bipedBird, float partialTicks) {
        float rotVal = Mth.lerp(partialTicks, bipedBird.getPrevWingRotation(), bipedBird.getWingRotation());
        float destVal = Mth.lerp(partialTicks, bipedBird.getPrevDestPos(), bipedBird.getDestPos());
        return (Mth.sin(rotVal * 0.225F) + 1.0F) * destVal;
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, consumer, packedLight, packedOverlay);
        this.body.render(poseStack, consumer, packedLight, packedOverlay);
        this.rightTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
        this.middleTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
        this.leftTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
    }
}
