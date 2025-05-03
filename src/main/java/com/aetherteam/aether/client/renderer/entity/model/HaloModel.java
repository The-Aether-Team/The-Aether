package com.aetherteam.aether.client.renderer.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class HaloModel<T extends EntityRenderState> extends EntityModel<T> {
    public final ModelPart halo;

    public HaloModel(ModelPart root) {
        super(root);
        this.halo = root.getChild("halo");
    }

    public static LayerDefinition createLayer(float originY, float originZ, float offsetY, float offsetZ) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("halo", CubeListBuilder.create()
                        .addBox("1", -2.0F, -10.0F + originY, 2.0F + originZ, 4, 1, 1, CubeDeformation.NONE, 0, 0)
                        .addBox("2", -2.0F, -10.0F + originY, -3.0F + originZ, 4, 1, 1, CubeDeformation.NONE, 0, 0)
                        .addBox("3", -3.0F, -10.0F + originY, -2.0F + originZ, 1, 1, 4, CubeDeformation.NONE, 0, 0)
                        .addBox("4", 2.0F, -10.0F + originY, -2.0F + originZ, 1, 1, 4, CubeDeformation.NONE, 0, 0),
                PartPose.offset(0.0F, 0.0F + offsetY, 0.0F + offsetZ));
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

}
