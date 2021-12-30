package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.SheepuffEntity;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepuffModel extends QuadrupedModel<SheepuffEntity>
{
    private float headXRot;

    public SheepuffModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = QuadrupedModel.createBodyMesh(12, CubeDeformation.NONE);
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F), PartPose.offset(0.0F, 6.0F, -8.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 8).addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F), PartPose.offset(0.0F, 5.0F, 2.0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void prepareMobModel(SheepuffEntity sheepuff, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTick);
        this.head.y = 6.0F + sheepuff.getHeadRotationPointY(partialTick) * 9.0F;
        this.headXRot = sheepuff.getHeadEatAngleScale(partialTick);
        this.body.xRot = ((float) Math.PI / 2F);
    }

    @Override
    public void setupAnim(SheepuffEntity sheepuff, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = this.headXRot;
    }
}
