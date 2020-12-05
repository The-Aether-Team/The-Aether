package com.aether.client.renderer.entity.model;

import com.aether.entity.passive.SheepuffEntity;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SheepuffedModel extends QuadrupedModel<SheepuffEntity> {
    private float headRotationAngleX;
    public SheepuffedModel() {
        super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        this.headModel = new ModelRenderer(this, 0, 0);
        this.headModel.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
        this.headModel.setRotationPoint(0.0F, 6.0F, -8.0F);

        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-4.0F, -8.0F, -7.0F, 8, 16, 6, 3.75F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);

        this.legBackRight = new ModelRenderer(this, 0, 16);
        this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.legBackRight.setRotationPoint(-3.0F, 12.0F, 7.0F);

        this.legBackLeft = new ModelRenderer(this, 0, 16);
        this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.legBackLeft.setRotationPoint(3.0F, 12.0F, 7.0F);

        this.legFrontRight = new ModelRenderer(this, 0, 16);
        this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.legFrontRight.setRotationPoint(-3.0F, 12.0F, -5.0F);

        this.legFrontLeft = new ModelRenderer(this, 0, 16);
        this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.legFrontLeft.setRotationPoint(3.0F, 12.0F, -5.0F);


    }

    public void setLivingAnimations(SheepuffEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.headModel.rotationPointY = 6.0F + entityIn.getHeadRotationPointY(partialTick) * 9.0F;
        this.headRotationAngleX = entityIn.getHeadRotationAngleX(partialTick);
    }

    @Override
    public void setRotationAngles(SheepuffEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.headModel.rotateAngleX = this.headRotationAngleX;
    }
}
