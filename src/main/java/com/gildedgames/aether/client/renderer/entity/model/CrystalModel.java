package com.gildedgames.aether.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class CrystalModel<T extends Entity> extends ListModel<T> {
    public final ModelPart crystal1;
    public final ModelPart crystal2;
    public final ModelPart crystal3;

    public CrystalModel(ModelPart root) {
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

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.crystal1, this.crystal2, this.crystal3);
    }

    @Override
    public void setupAnim(@Nonnull T crystal, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        for (ModelPart modelPart : this.parts()) {
            modelPart.xRot = headPitch * ((float) Math.PI / 180.0F);
            modelPart.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        }
    }
}
