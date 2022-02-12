package com.gildedgames.aether.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class CrystalModel<T extends Entity> extends ListModel<T>
{
    public final ModelPart crystal_0;
    public final ModelPart crystal_1;
    public final ModelPart crystal_2;

    public CrystalModel(ModelPart root) {
        this.crystal_0 = root.getChild("crystal_0");
        this.crystal_1 = root.getChild("crystal_1");
        this.crystal_2 = root.getChild("crystal_2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("crystal_0", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8), PartPose.ZERO);
        partDefinition.addOrReplaceChild("crystal_1", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8), PartPose.ZERO);
        partDefinition.addOrReplaceChild("crystal_2", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.crystal_0, this.crystal_1, this.crystal_2);
    }

    @Override
    public void setupAnim(@Nonnull T crystal, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        for (ModelPart modelPart : this.parts()) {
            modelPart.xRot = headPitch * ((float) Math.PI / 180.0F);
            modelPart.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        }
    }
}
