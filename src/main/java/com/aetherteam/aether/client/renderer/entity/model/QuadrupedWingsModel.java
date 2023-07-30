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
    private final ModelPart leftWingInner;
    private final ModelPart leftWingOuter;
    private final ModelPart rightWingInner;
    private final ModelPart rightWingOuter;

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
        leftWingInnerDef.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(0.0F, -16.0F, 0.0F));
        PartDefinition rightWingInnerDef = partDefinition.addOrReplaceChild("right_wing_inner", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(4.0F, 2.0F + offset, -4.0F));
        rightWingInnerDef.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(0.0F, -16.0F, 0.0F));
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
            entity.setWingAngle(entity.getWingFold() * Mth.sin(ageInTicks / 31.83098862F));
            entity.setWingFold(entity.getWingFold() + ((aimingForFold - entity.getWingFold()) / 75.0F));
            float wingBend = -((float) Math.acos(entity.getWingFold()));
            this.leftWingInner.zRot = -(entity.getWingAngle() + wingBend + (float) (Math.PI / 2.0F));
            this.leftWingOuter.zRot = -(entity.getWingAngle() - wingBend + (float) (Math.PI / 2.0F)) - this.leftWingInner.zRot;
            this.rightWingInner.zRot = -this.leftWingInner.zRot;
            this.rightWingOuter.zRot = -this.leftWingOuter.zRot;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.leftWingInner.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightWingInner.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
