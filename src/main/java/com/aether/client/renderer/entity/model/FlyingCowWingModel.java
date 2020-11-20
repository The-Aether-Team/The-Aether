package com.aether.client.renderer.entity.model;

import com.aether.entity.passive.FlyingCowEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlyingCowWingModel extends EntityModel<FlyingCowEntity> {
    private ModelRenderer leftWingInner = new ModelRenderer(this, 0, 0);
    private ModelRenderer leftWingOuter = new ModelRenderer(this, 20, 0);
    private ModelRenderer rightWingInner = new ModelRenderer(this, 0, 0);
    private ModelRenderer rightWingOuter = new ModelRenderer(this, 40, 0);

    public FlyingCowWingModel()
    {
        this.leftWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.leftWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        this.rightWingOuter.rotateAngleY = (float)Math.PI;
    }

    @Override
    public void setRotationAngles(FlyingCowEntity flyingCow, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float wingBend = -((float)Math.acos(flyingCow.wingFold));

        float x = 32.0F * flyingCow.wingFold / 4.0F;
        float y = -32.0F * (float)Math.sqrt(1.0F - flyingCow.wingFold * flyingCow.wingFold) / 4.0F;

        float x2 = x * (float)Math.cos(flyingCow.wingAngle) - y * (float)Math.sin(flyingCow.wingAngle);
        float y2 = x * (float)Math.sin(flyingCow.wingAngle) + y * (float)Math.cos(flyingCow.wingAngle);

        this.leftWingInner.setRotationPoint(4.0F + x2, y2 + 12.0F, 0.0F);
        this.rightWingInner.setRotationPoint(-4.0F - x2, y2 + 12.0F, 0.0F);

        x *= 3.0F;
        x2 = x * (float)Math.cos(flyingCow.wingAngle) - y * (float)Math.sin(flyingCow.wingAngle);
        y2 = x * (float)Math.sin(flyingCow.wingAngle) + y * (float)Math.cos(flyingCow.wingAngle);

        this.leftWingOuter.setRotationPoint(4.0F + x2, y2 + 12.0F, 0.0F);
        this.rightWingOuter.setRotationPoint(-4.0F - x2, y2 + 12.0F, 0.0F);

        this.leftWingInner.rotateAngleZ = flyingCow.wingAngle + wingBend + ((float)Math.PI / 2.0F);
        this.leftWingOuter.rotateAngleZ = flyingCow.wingAngle - wingBend + ((float)Math.PI / 2.0F);
        this.rightWingInner.rotateAngleZ = -(flyingCow.wingAngle + wingBend - ((float)Math.PI / 2.0F));
        this.rightWingOuter.rotateAngleZ = -(flyingCow.wingAngle - wingBend + ((float)Math.PI / 2.0F));
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();
//        matrixStackIn.translate(0.0F, -10.0F * scale, 0.0F);
        this.leftWingOuter.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightWingOuter.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }
}
