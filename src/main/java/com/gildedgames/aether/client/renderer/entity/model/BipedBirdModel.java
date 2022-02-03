package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public abstract class BipedBirdModel<T extends Entity> extends EntityModel<T>
{
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
        this.jaw = head.getChild("jaw");
        this.neck = head.getChild("neck");
        this.body = root.getChild("body");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.rightTailFeather = root.getChild("right_tail_feather");
        this.middleTailFeather = root.getChild("middle_tail_feather");
        this.leftTailFeather = root.getChild("left_tail_feather");
    }

    public static LayerDefinition createBodyLayer(CubeDeformation cube) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 8.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(0.0F, 8.0F, -4.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(-0.1F), 0.5F, 0.5F), PartPose.ZERO);
        head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs( 44, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.ZERO);
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 8.0F, 5.0F, cube, 0.5F, 0.5F), PartPose.offset(0.0F, 16.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(-2.0F, 16.0F, 1.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(2.0F, 16.0F, 1.0F));
        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(52, 0).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 8.0F, 4.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(-3.001F, -3.0F, 3.0F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(52, 0).addBox(0.0F, 0.0F, -2.0F, 1.0F, 8.0F, 4.0F, CubeDeformation.NONE, 0.5F, 0.5F), PartPose.offset(3.001F, -3.0F, 3.0F));
        partdefinition.addOrReplaceChild("right_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(-0.3F), 0.5F, 0.5F), PartPose.offset(0.0F, 17.5F, 1.0F));
        partdefinition.addOrReplaceChild("middle_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(-0.3F), 0.5F, 0.5F), PartPose.offset(0.0F, 17.5F, 1.0F));
        partdefinition.addOrReplaceChild("left_tail_feather", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(-0.3F), 0.5F, 0.5F), PartPose.offset(0.0F, 17.5F, 1.0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }


    @Override
    public void setupAnim(@Nonnull T bipedBird, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.neck.xRot = -this.head.xRot;
        this.body.xRot = (float) (Math.PI / 2.0F);

        this.rightTailFeather.xRot = 0.25F;
        this.rightTailFeather.yRot = -0.375F;
        this.middleTailFeather.xRot = 0.25F;
        this.middleTailFeather.yRot = 0.0F;
        this.leftTailFeather.xRot = 0.25F;
        this.leftTailFeather.yRot = 0.375F;
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
