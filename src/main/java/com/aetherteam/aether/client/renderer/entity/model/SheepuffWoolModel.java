package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import javax.annotation.Nonnull;

public class SheepuffWoolModel extends QuadrupedModel<Sheepuff> {
    private float headXRot;

    public SheepuffWoolModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    public static LayerDefinition createFurLayer(CubeDeformation cube, float height) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 6.0F, -8.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 8).addBox(-4.0F, -10.0F + height, -7.0F, 8.0F, 16.0F, 6.0F, cube), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder cubeBuilder = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F));
        partDefinition.addOrReplaceChild("right_hind_leg", cubeBuilder, PartPose.offset(-3.0F, 12.0F, 7.0F));
        partDefinition.addOrReplaceChild("left_hind_leg", cubeBuilder, PartPose.offset(3.0F, 12.0F, 7.0F));
        partDefinition.addOrReplaceChild("right_front_leg", cubeBuilder, PartPose.offset(-3.0F, 12.0F, -5.0F));
        partDefinition.addOrReplaceChild("left_front_leg", cubeBuilder, PartPose.offset(3.0F, 12.0F, -5.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    public void prepareMobModel(@Nonnull Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTicks) {
        super.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTicks);
        this.head.y = 6.0F + sheepuff.getHeadEatPositionScale(partialTicks) * 9.0F;
        this.headXRot = sheepuff.getHeadEatAngleScale(partialTicks);
    }

    @Override
    public void setupAnim(@Nonnull Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = this.headXRot;
    }
}
