package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class SunSpiritModel<T extends Entity> extends EntityModel<T>
{
    public ModelPart head;
    public ModelPart jaw;
    public ModelPart chest;
    public ModelPart torso;
    public ModelPart rightBase;
    public ModelPart leftBase;
    public ModelPart rightShoulder;
    public ModelPart rightArm;
    public ModelPart rightBracelet;
    public ModelPart leftShoulder;
    public ModelPart leftArm;
    public ModelPart leftBracelet;

    public SunSpiritModel(ModelPart root) {
        this.head = root.getChild("head");
        this.jaw = root.getChild("jaw");
        this.chest = root.getChild("chest");
        this.torso = root.getChild("torso");
        this.rightBase = root.getChild("right_base");
        this.leftBase = root.getChild("left_base");
        this.rightShoulder = root.getChild("right_shoulder");
        this.rightArm = root.getChild("right_arm");
        this.rightBracelet = root.getChild("right_bracelet");
        this.leftShoulder = root.getChild("left_shoulder");
        this.leftArm = root.getChild("left_arm");
        this.leftBracelet = root.getChild("left_bracelet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -3.0F, 8.0F, 5.0F, 7.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, 0.0F, -2.5F, 10.0F, 6.0F, 5.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 23).addBox(-4.5F, 6.0F, -2.0F, 9.0F, 5.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_base", CubeListBuilder.create().texOffs(30, 27).addBox(-4.5F, 11.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_base", CubeListBuilder.create().texOffs(30, 27).addBox(-0.5F, 11.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(-8.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F), PartPose.offset(-8.0F, 2.0F + 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_bracelet", CubeListBuilder.create().texOffs(30, 26).addBox(-2.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(-8.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)).mirror(true), PartPose.offset(8.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F).mirror(true), PartPose.offset(8.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_bracelet", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)).mirror(true), PartPose.offset(8.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.jaw.xRot = this.head.xRot;
        this.jaw.yRot = this.head.yRot;

        this.rightArm.xRot = -(Mth.sin(ageInTicks * 0.067F) * 0.05F);
        this.rightArm.yRot = 0.0F;
        this.rightArm.zRot = -(Mth.cos(ageInTicks * 0.09F) * 0.05F - 0.05F);
        this.leftArm.xRot = -(this.rightArm.xRot);
        this.leftArm.yRot = this.rightArm.yRot;
        this.leftArm.zRot = -(this.rightArm.zRot);

        this.rightShoulder.xRot = this.rightArm.xRot;
        this.rightShoulder.yRot = this.rightArm.yRot;
        this.rightShoulder.zRot = this.rightArm.zRot;
        this.leftShoulder.xRot = this.leftArm.xRot;
        this.leftShoulder.yRot = this.leftArm.yRot;
        this.leftShoulder.zRot = this.leftArm.zRot;

        this.rightBracelet.xRot = this.rightArm.xRot;
        this.rightBracelet.yRot = this.rightArm.yRot;
        this.rightBracelet.zRot = this.rightArm.zRot;
        this.leftBracelet.xRot = this.leftArm.xRot;
        this.leftBracelet.yRot = this.leftArm.yRot;
        this.leftBracelet.zRot = this.leftArm.zRot;
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.jaw.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.chest.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.torso.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightBase.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftBase.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightShoulder.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightArm.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightBracelet.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftShoulder.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftArm.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftBracelet.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
