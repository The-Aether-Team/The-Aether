package com.gildedgames.aether.client.renderer.perks.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class PlayerHaloModel extends ListModel<Player>
{
    public ModelPart main;

    public PlayerHaloModel(ModelPart root) {
        this.main = root.getChild("main");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .addBox("1", -2.0F, -10.0F, 2.0F, 4, 1, 1, new CubeDeformation(0), 0, 0)
                .addBox("2", -2.0F, -10.0F, -3.0F, 4, 1, 1, new CubeDeformation(0), 0, 0)
                .addBox("3", -3.0F, -10.0F, -2.0F, 1, 1, 4, new CubeDeformation(0), 0, 0)
                .addBox("4", 2.0F, -10.0F, -2.0F, 1, 1, 4, new CubeDeformation(0), 0, 0), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.main);
    }

    @Override
    public void setupAnim(@Nonnull Player pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) { }
}
