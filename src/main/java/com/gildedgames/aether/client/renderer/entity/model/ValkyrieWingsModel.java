package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.NotGrounded;
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

import javax.annotation.Nonnull;

public class ValkyrieWingsModel<T extends Entity & NotGrounded> extends EntityModel<T> {
    public ModelPart rightWing;
    public ModelPart leftWing;

    public ValkyrieWingsModel(ModelPart root) {
        this.rightWing = root.getChild("right_wing");
        this.leftWing = root.getChild("left_wing");
    }

    public static LayerDefinition createMainLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 31).mirror().addBox(-19.0F, -4.5F, 0.0F, 19.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(-0.5F, 4.5F, 2.625F, 0.0F, 0.2F, 0.125F));
        partDefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 31).addBox(0.0F, -4.5F, 0.0F, 19.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(0.5F, 4.5F, 2.625F, 0.0F, -0.2F, -0.125F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) { }

    public void setupWingRotation(@Nonnull T entity, float sinage) {
        this.rightWing.yRot = -((float) Math.sin(sinage) / 6.0F);
        this.rightWing.zRot = -((float) Math.sin(sinage) / (entity.isEntityOnGround() ? 8.0F : 3.0F));
        this.leftWing.yRot = (float) Math.sin(sinage) / 6.0F;
        this.leftWing.zRot = (float) Math.sin(sinage) / (entity.isEntityOnGround() ? 8.0F : 3.0F);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.rightWing.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftWing.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
