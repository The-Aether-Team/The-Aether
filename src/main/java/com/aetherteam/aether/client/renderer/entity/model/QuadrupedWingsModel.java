package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.passive.WingedAnimal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class QuadrupedWingsModel<T extends WingedAnimal> extends EntityModel<T> {
    public final ModelPart leftWingInner;
    public final ModelPart leftWingOuter;
    public final ModelPart rightWingInner;
    public final ModelPart rightWingOuter;

    public QuadrupedWingsModel(ModelPart root) {
        this.leftWingInner = root.getChild("left_wing_inner");
        this.leftWingOuter = this.leftWingInner.getChild("left_wing_outer");
        this.rightWingInner = root.getChild("right_wing_inner");
        this.rightWingOuter = this.rightWingInner.getChild("right_wing_outer");
    }

    public static LayerDefinition createMainLayer(float offset) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition leftWingInnerDef = partDefinition.addOrReplaceChild("left_wing_inner", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(-4.0F, 2.0F + offset, -4.0F));
        leftWingInnerDef.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-1.0F, -16.0F, 0.0005F, 2.0F, 16.0F, 7.999F), PartPose.offset(0.0F, -16.0F, 0.0F));
        PartDefinition rightWingInnerDef = partDefinition.addOrReplaceChild("right_wing_inner", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(4.0F, 2.0F + offset, -4.0F));
        rightWingInnerDef.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, -16.0F, 0.0005F, 2.0F, 16.0F, 7.999F), PartPose.offset(0.0F, -16.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!Minecraft.getInstance().isPaused()) {
            float aimingForFold;
            if (entity.isEntityOnGround()) {
                aimingForFold = 0.1F;
            } else {
                aimingForFold = 1.0F;
            }
            entity.setWingAngle(entity.getWingFold() * Mth.sin(ageInTicks / 15.9F));
            entity.setWingFold(entity.getWingFold() + ((aimingForFold - entity.getWingFold()) / 37.5F));
            float wingBend = -((float) Math.acos(entity.getWingFold()));
            this.leftWingInner.zRot = -(entity.getWingAngle() + wingBend + Mth.HALF_PI);
            this.leftWingOuter.zRot = -(entity.getWingAngle() - wingBend + Mth.HALF_PI) - this.leftWingInner.zRot;
            this.rightWingInner.zRot = -this.leftWingInner.zRot;
            this.rightWingOuter.zRot = -this.leftWingOuter.zRot;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.leftWingInner.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.rightWingInner.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
