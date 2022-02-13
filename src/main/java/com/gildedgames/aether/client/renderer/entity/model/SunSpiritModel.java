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

public class SunSpiritModel<T extends Entity> extends EntityModel<T> {
    public ModelPart head;
    public ModelPart jaw;
    public ModelPart torso;
    public ModelPart chest;
    public ModelPart rightBase;
    public ModelPart leftBase;
    public ModelPart rightArm;
    public ModelPart rightShoulder;
    public ModelPart rightBracelet;
    public ModelPart leftArm;
    public ModelPart leftShoulder;
    public ModelPart leftBracelet;

    public SunSpiritModel(ModelPart root) {
        this.head = root.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.torso = root.getChild("torso");
        this.chest = this.torso.getChild("chest");
        this.rightBase = this.torso.getChild("right_base");
        this.leftBase = this.torso.getChild("left_base");
        this.rightArm = root.getChild("right_arm");
        this.rightShoulder = this.rightArm.getChild("right_shoulder");
        this.rightBracelet = this.rightArm.getChild("right_bracelet");
        this.leftArm = root.getChild("left_arm");
        this.leftShoulder = this.leftArm.getChild("left_shoulder");
        this.leftBracelet = this.leftArm.getChild("left_bracelet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -3.0F, 8.0F, 5.0F, 7.0F), PartPose.ZERO);
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F), PartPose.ZERO);
        PartDefinition torso = partDefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 23).addBox(-4.5F, 6.0F, -2.0F, 9.0F, 5.0F, 4.0F), PartPose.ZERO);
        torso.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, 0.0F, -2.5F, 10.0F, 6.0F, 5.0F), PartPose.ZERO);
        torso.addOrReplaceChild("right_base", CubeListBuilder.create().texOffs(30, 27).addBox(-4.5F, 11.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        torso.addOrReplaceChild("left_base", CubeListBuilder.create().texOffs(30, 27).addBox(-0.5F, 11.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition rightArm = partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F), PartPose.offset(-8.0F, 2.0F, 0.0F));
        rightArm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        rightArm.addOrReplaceChild("right_bracelet", CubeListBuilder.create().texOffs(30, 26).addBox(-2.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        PartDefinition leftArm = partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F).mirror(true), PartPose.offset(8.0F, 2.0F, 0.0F));
        leftArm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)).mirror(true), PartPose.ZERO);
        leftArm.addOrReplaceChild("left_bracelet", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)).mirror(true), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;

        this.rightArm.xRot = -(Mth.sin(ageInTicks * 0.067F) * 0.05F);
        this.rightArm.yRot = 0.0F;
        this.rightArm.zRot = -(Mth.cos(ageInTicks * 0.09F) * 0.05F - 0.05F);
        this.leftArm.xRot = -(this.rightArm.xRot);
        this.leftArm.yRot = this.rightArm.yRot;
        this.leftArm.zRot = -(this.rightArm.zRot);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.torso.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightArm.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftArm.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
