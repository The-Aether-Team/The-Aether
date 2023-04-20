package com.aetherteam.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class HaloModel<T extends Entity> extends EntityModel<T> {
    public ModelPart halo;

    public HaloModel(ModelPart root) {
        this.halo = root.getChild("halo");
    }

    public static LayerDefinition createLayer(float originY, float originZ, float offsetY, float offsetZ) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("halo", CubeListBuilder.create()
                .addBox("1", -2.0F, -10.0F + originY, 2.0F + originZ, 4, 1, 1, CubeDeformation.NONE, 0, 0)
                .addBox("2", -2.0F, -10.0F + originY, -3.0F + originZ, 4, 1, 1, CubeDeformation.NONE, 0, 0)
                .addBox("3", -3.0F, -10.0F + originY, -2.0F + originZ, 1, 1, 4, CubeDeformation.NONE, 0, 0)
                .addBox("4", 2.0F, -10.0F + originY, -2.0F + originZ, 1, 1, 4, CubeDeformation.NONE, 0, 0), PartPose.offset(0.0F, 0.0F + offsetY, 0.0F + offsetZ));
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void setupAnim(@Nonnull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) { }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.halo.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
