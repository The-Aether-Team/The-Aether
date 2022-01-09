package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.SheepuffEntity;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepuffWoolModel extends QuadrupedModel<SheepuffEntity>
{
    private float headXRot;

    public SheepuffWoolModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }


    public static LayerDefinition createFurLayer() {
        MeshDefinition meshDef = new MeshDefinition();
        PartDefinition partDef = meshDef.getRoot();
        partDef.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 6.0F, -8.0F));
        partDef.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 8).addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, new CubeDeformation(1.75F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder cubeBuilder = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F));
        partDef.addOrReplaceChild("right_hind_leg", cubeBuilder, PartPose.offset(-3.0F, 12.0F, 7.0F));
        partDef.addOrReplaceChild("left_hind_leg", cubeBuilder, PartPose.offset(3.0F, 12.0F, 7.0F));
        partDef.addOrReplaceChild("right_front_leg", cubeBuilder, PartPose.offset(-3.0F, 12.0F, -5.0F));
        partDef.addOrReplaceChild("left_front_leg", cubeBuilder, PartPose.offset(3.0F, 12.0F, -5.0F));
        return LayerDefinition.create(meshDef, 64, 32);
    }

    public void prepareMobModel(SheepuffEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.head.y = 6.0F + entityIn.getHeadRotationPointY(partialTick) * 9.0F;
        this.headXRot = entityIn.getHeadEatAngleScale(partialTick);
    }

    @Override
    public void setupAnim(SheepuffEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = this.headXRot;
    }
}
