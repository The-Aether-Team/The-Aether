package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.SheepuffEntity;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepuffModel extends QuadrupedModel<SheepuffEntity>
{
    private float headXRot;

    public SheepuffModel() {
        super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F, 0.0F);
        this.head.setPos(0.0F, 6.0F, -8.0F);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, 0.0F);
        this.body.setPos(0.0F, 5.0F, 2.0F);
    }

    public void prepareMobModel(SheepuffEntity sheepuff, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTick);
        this.head.y = 6.0F + sheepuff.getHeadRotationPointY(partialTick) * 9.0F;
        this.headXRot = sheepuff.getHeadEatAngleScale(partialTick);
    }

    @Override
    public void setupAnim(SheepuffEntity sheepuff, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = this.headXRot;
    }
}
