package com.aetherteam.aether.client.renderer.accessory.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class PendantModel extends HumanoidModel<LivingEntity> {
    public PendantModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createLayer() {
        CubeDeformation cube = new CubeDeformation(0.6F);
        MeshDefinition meshDefinition = HumanoidModel.createMesh(cube, 0.0F);
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.5F, -2.0F, 8.0F, 12.5F, 4.0F, cube), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 24, 16);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }
}
