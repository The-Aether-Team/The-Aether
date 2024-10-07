package com.aetherteam.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class ValkyrieWingsModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart rightWing;
    public final ModelPart leftWing;

    public ValkyrieWingsModel(ModelPart root) {
        this.rightWing = root.getChild("right_wing");
        this.leftWing = root.getChild("left_wing");
    }

    public static LayerDefinition createMainLayer(float offsetY, float offsetZ) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 31).mirror().addBox(-19.0F, -4.5F, 0.0F, 19.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(-0.5F, offsetY, offsetZ, 0.0F, 0.2F, 0.125F));
        partDefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 31).addBox(0.0F, -4.5F, 0.0F, 19.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(0.5F, offsetY, offsetZ, 0.0F, -0.2F, -0.125F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color) {
        this.rightWing.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.leftWing.render(poseStack, consumer, packedLight, packedOverlay, color);
    }
}
