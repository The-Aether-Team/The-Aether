package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CloudMinionModel extends ListModel<CloudMinionEntity>
{
    public ModelPart main;

    public CloudMinionModel(ModelPart root) {
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .addBox("1", -4.5F, 14.0F, -4.5F, 9, 9, 9, new CubeDeformation(0), 0, 0)
                .addBox("2", -3.5F, 15.0F, -5.5F, 7, 7, 1, new CubeDeformation(0), 36, 0)
                .addBox("3", -3.5F, 15.0F, 4.5F, 7, 7, 1, new CubeDeformation(0), 36, 0)
                .addBox("4", -5.5F, 15.0F, -3.5F, 1, 7, 7, new CubeDeformation(0), 36, 8)
                .addBox("5", 4.5F, 15.0F, -3.5F, 1, 7, 7, new CubeDeformation(0), 36, 8), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.main);
    }

    @Override
    public void setupAnim(CloudMinionEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.main.yRot = 0F;
        this.main.xRot = 0F;
    }
}
