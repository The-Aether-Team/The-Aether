package com.gildedgames.aether.client.renderer.player.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class HaloModel<T extends Entity> extends ListModel<T>
{
    public ModelPart main;

    public HaloModel(ModelPart root) {
        this.main = root.getChild("main");
    }

    public static LayerDefinition createLayer(float originY, float originZ, float offsetY, float offsetZ) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .addBox("1", -2.0F, -10.0F + originY, 2.0F + originZ, 4, 1, 1, new CubeDeformation(0), 0, 0)
                .addBox("2", -2.0F, -10.0F + originY, -3.0F + originZ, 4, 1, 1, new CubeDeformation(0), 0, 0)
                .addBox("3", -3.0F, -10.0F + originY, -2.0F + originZ, 1, 1, 4, new CubeDeformation(0), 0, 0)
                .addBox("4", 2.0F, -10.0F + originY, -2.0F + originZ, 1, 1, 4, new CubeDeformation(0), 0, 0), PartPose.offset(0.0F, 0.0F + offsetY, 0.0F + offsetZ));
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.main);
    }

    @Override
    public void setupAnim(@Nonnull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) { }
}
