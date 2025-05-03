package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.client.renderer.entity.state.CrystalRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class CrystalModel<T extends CrystalRenderState> extends EntityModel<T> {
    public final ModelPart crystal1;
    public final ModelPart crystal2;
    public final ModelPart crystal3;

    public CrystalModel(ModelPart root) {
        super(root);
        this.crystal1 = root.getChild("crystal_1");
        this.crystal2 = root.getChild("crystal_2");
        this.crystal3 = root.getChild("crystal_3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("crystal_1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8), PartPose.ZERO);
        partDefinition.addOrReplaceChild("crystal_2", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8), PartPose.ZERO);
        partDefinition.addOrReplaceChild("crystal_3", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(T crystal) {
        for (ModelPart modelPart : this.root.getAllParts().toList()) {
            modelPart.xRot = crystal.xRot * Mth.DEG_TO_RAD;
            modelPart.yRot = crystal.yRot * Mth.DEG_TO_RAD;
        }
    }
}
