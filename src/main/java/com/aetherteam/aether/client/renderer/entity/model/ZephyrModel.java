package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ZephyrModel extends EntityModel<Zephyr> {
    public final ModelPart rightFace;
    public final ModelPart leftFace;
    public final ModelPart mouth;
    public final ModelPart body;
    public final ModelPart bodyRightSideFront;
    public final ModelPart bodyRightSideBack;
    public final ModelPart bodyLeftSideFront;
    public final ModelPart bodyLeftSideBack;
    public final ModelPart cloudButt;
    public final ModelPart tailBase;
    public final ModelPart tailMiddle;
    public final ModelPart tailEnd;

    public ZephyrModel(ModelPart model) {
        this.rightFace = model.getChild("right_face");
        this.leftFace = model.getChild("left_face");
        this.mouth = model.getChild("mouth");
        this.body = model.getChild("body");
        this.bodyRightSideFront = model.getChild("body_right_side_front");
        this.bodyRightSideBack = model.getChild("body_right_side_back");
        this.bodyLeftSideFront = model.getChild("body_left_side_front");
        this.bodyLeftSideBack = model.getChild("body_left_side_back");
        this.cloudButt = model.getChild("cloud_butt");
        this.tailBase = model.getChild("tail_base");
        this.tailMiddle = this.tailBase.getChild("tail_middle");
        this.tailEnd = this.tailMiddle.getChild("tail_end");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("right_face", CubeListBuilder.create().texOffs(67, 11).addBox(-7.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_face", CubeListBuilder.create().texOffs(67, 11).mirror().addBox(3.0F, -1.0F, -9.0F, 4.0F, 6.0F, 2.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        partDefinition.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(66, 19).mirror().addBox(-3.0F, 1.0F, -8.0F, 6.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(27, 9).addBox(-6.0F, -4.0F, -7.0F, 12.0F, 9.0F, 14.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        partDefinition.addOrReplaceChild("body_right_side_front", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-2.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F), PartPose.offset(-6.0F, 8.0F, -4.0F));
        partDefinition.addOrReplaceChild("body_right_side_back", CubeListBuilder.create().texOffs(25, 11).mirror().addBox(-2.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F), PartPose.offset(-5.5F, 9.0F, 2.0F));
        partDefinition.addOrReplaceChild("body_left_side_front", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F), PartPose.offset(6.0F, 8.0F, -4.0F));
        partDefinition.addOrReplaceChild("body_left_side_back", CubeListBuilder.create().texOffs(25, 11).addBox(0.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F), PartPose.offset(5.5F, 9.0F, 2.0F));
        partDefinition.addOrReplaceChild("cloud_butt", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-6.0F, -3.0F, 0.0F, 8.0F, 6.0F, 2.0F), PartPose.offset(2.0F, 8.0F, 7.0F));
        PartDefinition tailBase = partDefinition.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(96, 22).addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5), PartPose.offset(0.0F, 0.0F, 12.4F));
        PartDefinition tailMiddle = tailBase.addOrReplaceChild("tail_middle", CubeListBuilder.create().texOffs(80, 24).addBox(-2.0F, -2.0F, -1.966667F, 4, 4, 4), PartPose.offset(0.0F, 0.0F, 6.0F));
        tailMiddle.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(84, 18).addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3), PartPose.offset(0.0F, 0.0F, 5.0F));
        return LayerDefinition.create(meshDefinition, 128, 32);
    }

    @Override
    public void setupAnim(Zephyr zephyr, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float motion = Mth.sin((limbSwing * 20.0F) / Mth.RAD_TO_DEG) * limbSwingAmount * 0.5F;

        this.rightFace.y = 8 - motion;
        this.rightFace.x = -motion * 0.5F;

        this.leftFace.y = motion + 8;
        this.leftFace.x = motion * 0.5F;

        this.bodyRightSideFront.y = 8 - motion * 0.5F;
        this.bodyRightSideBack.y = 9 + motion * 0.5F;

        this.bodyLeftSideFront.y = this.bodyRightSideFront.y;
        this.bodyLeftSideBack.y = this.bodyRightSideBack.y;

        this.tailBase.x = Mth.sin((limbSwing * 20.0F) / Mth.RAD_TO_DEG) * limbSwingAmount * 0.75F;
        this.tailBase.y = 8 - motion;
        this.tailBase.yRot = Mth.sin(ageInTicks * 0.5F) * limbSwingAmount * 0.75F;

        this.tailMiddle.x = Mth.sin((limbSwing * 15.0F) / Mth.RAD_TO_DEG) * limbSwingAmount * 0.85F;
        this.tailMiddle.y = motion * 1.25F;
        this.tailMiddle.yRot = this.tailBase.yRot + 0.25F;

        this.tailEnd.x = Mth.sin((limbSwing * 10.0F) / Mth.RAD_TO_DEG) * limbSwingAmount * 0.95F;
        this.tailEnd.y = -motion;
        this.tailEnd.yRot = this.tailMiddle.yRot + 0.35F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.rightFace.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftFace.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.mouth.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.bodyRightSideFront.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.bodyRightSideBack.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.bodyLeftSideFront.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.bodyLeftSideBack.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.cloudButt.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailBase.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
