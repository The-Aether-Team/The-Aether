package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SheepuffModel extends QuadrupedModel<Sheepuff> {
    private float headXRot;

    public SheepuffModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = QuadrupedModel.createBodyMesh(12, CubeDeformation.NONE);
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F), PartPose.offset(0.0F, 6.0F, -8.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 8).addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, (float) (Math.PI / 2.0F), 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    public void prepareMobModel(Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTicks) {
        super.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTicks);
        this.head.y = 6.0F + sheepuff.getHeadEatPositionScale(partialTicks) * 9.0F;
        this.headXRot = sheepuff.getHeadEatAngleScale(partialTicks);
    }

    @Override
    public void setupAnim(Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = this.headXRot;
    }
}
