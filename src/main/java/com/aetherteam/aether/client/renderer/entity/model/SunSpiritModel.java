package com.aetherteam.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SunSpiritModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart base;
    public final ModelPart torso;
    public final ModelPart head;
    public final ModelPart rightArm;
    public final ModelPart leftArm;

    public SunSpiritModel(ModelPart root) {
        this.base = root.getChild("base");
        this.torso = this.base.getChild("torso");
        this.head = this.torso.getChild("head");
        this.rightArm = this.torso.getChild("right_arm");
        this.leftArm = this.torso.getChild("left_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition base = partDefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition torso = base.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(34, 0).addBox(-5.0F, -5.75F, -2.5F, 10.0F, 6.0F, 5.0F, CubeDeformation.NONE).texOffs(34, 11).addBox(-4.5F, 0.25F, -2.0F, 9.0F, 5.0F, 4.0F, CubeDeformation.NONE).texOffs(0, 54).addBox(-4.5F, 5.25F, -2.5F, 9.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 5.0F, 7.0F, CubeDeformation.NONE).texOffs(0, 12).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 3.0F, 8.0F, CubeDeformation.NONE), PartPose.offset(0.0F, -5.75F, 0.5F));
        torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(20, 33).mirror().addBox(0.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F, CubeDeformation.NONE).mirror(false).texOffs(20, 23).mirror().addBox(0.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false).texOffs(20, 48).addBox(0.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, -3.75F, 0.0F));
        torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 23).addBox(-5.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)).texOffs(0, 33).addBox(-5.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F, CubeDeformation.NONE).texOffs(0, 48).mirror().addBox(-5.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-5.0F, -3.75F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.base.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
