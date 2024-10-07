package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
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

public class MimicModel extends EntityModel<Mimic> {
    public final ModelPart upperBody;
    public final ModelPart lowerBody;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart knob;

    public MimicModel(ModelPart root) {
        this.upperBody = root.getChild("upper_body");
        this.lowerBody = root.getChild("lower_body");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.knob = this.upperBody.getChild("knob");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition upperBody = partDefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, 0.0F, 0.0F, 16, 6, 16), PartPose.offsetAndRotation(-8.0F, 0.0F, 8.0F, Mth.PI, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("lower_body", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, 0.0F, 0.0F, 16, 10, 16), PartPose.offset(-8.0F, 0.0F, -8.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(64, 0).addBox(0.0F, 0.0F, -3.0F, 6, 15, 6), PartPose.offset(1.5F, 9.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-5.1F, 0.0F, -3.0F, 6, 15, 6), PartPose.offset(-2.5F, 9.0F, 0.0F));
        upperBody.addOrReplaceChild("knob", CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -2.0F, 16.0F, 2, 4, 1), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color) {
        this.upperBody.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.lowerBody.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
    }

    @Override
    public void setupAnim(Mimic mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.upperBody.xRot = Mth.PI - 0.6F * (1.0F + Mth.cos(ageInTicks / 10.0F * Mth.PI));
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount;
    }
}
