package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CloudMinionModel extends ListModel<CloudMinion> {
    public final ModelPart cloudMinion;

    public CloudMinionModel(ModelPart root) {
        this.cloudMinion = root.getChild("cloudMinion");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("cloudMinion", CubeListBuilder.create()
                .addBox("1", -4.5F, 14.0F, -4.5F, 9, 9, 9, CubeDeformation.NONE, 0, 0)
                .addBox("2", -3.5F, 15.0F, -5.5F, 7, 7, 1, CubeDeformation.NONE, 36, 0)
                .addBox("3", -3.5F, 15.0F, 4.5F, 7, 7, 1, CubeDeformation.NONE, 36, 0)
                .addBox("4", -5.5F, 15.0F, -3.5F, 1, 7, 7, CubeDeformation.NONE, 36, 8)
                .addBox("5", 4.5F, 15.0F, -3.5F, 1, 7, 7, CubeDeformation.NONE, 36, 8), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.cloudMinion);
    }

    @Override
    public void setupAnim(CloudMinion cloudMinion, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
