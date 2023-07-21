package com.aetherteam.aether.client.renderer.accessory.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class GlovesModel extends HumanoidModel<LivingEntity> {
    public GlovesModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createLayer(CubeDeformation cube, boolean isSlim) {
        MeshDefinition meshDefinition = HumanoidModel.createMesh(cube, 0.0F);
        PartDefinition partDefinition = meshDefinition.getRoot();
        if (!isSlim) {
            partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(5.0F, 2.0F, 0.0F));
        } else {
            partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(5.0F, 2.0F, 0.0F));
        }
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.rightArm, this.leftArm);
    }
}
