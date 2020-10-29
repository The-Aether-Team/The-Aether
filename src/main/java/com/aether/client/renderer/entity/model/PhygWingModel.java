package com.aether.client.renderer.entity.model;

import com.aether.entity.passive.PhygEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class PhygWingModel extends EntityModel<PhygEntity> {
    private ModelRenderer leftWingInner = new ModelRenderer(this, 0, 0);
    private ModelRenderer leftWingOuter = new ModelRenderer(this, 20, 0);
    private ModelRenderer rightWingInner = new ModelRenderer(this, 0, 0);
    private ModelRenderer rightWingOuter = new ModelRenderer(this, 40, 0);
    private boolean isChild = false;

    public PhygWingModel()
    {
        this.leftWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.leftWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        this.rightWingOuter.rotateAngleY = (float)Math.PI;
    }

    @Override
    public void setRotationAngles(PhygEntity phyg, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        isChild = phyg.isChild();
        float wingBend;
        float x;
        float y;
        float z;
        float x2;
        float y2;
        if(isChild) {
            wingBend = -((float) Math.acos((double) phyg.wingFold));
            x = -((float) Math.acos((double) phyg.wingFold));
            y = 32.0F * phyg.wingFold / 4.0F;
            z = -32.0F * (float) Math.sqrt((double) (1.0F - phyg.wingFold * phyg.wingFold)) / 4.0F;
            x2 = 0.0F;
            y2 = y * (float) Math.cos((double) phyg.wingAngle) - z * (float) Math.sin((double) phyg.wingAngle);
            float y21 = y * (float) Math.sin((double) phyg.wingAngle) + z * (float) Math.cos((double) phyg.wingAngle);

            this.leftWingInner.setRotationPoint(4.0F + y2, y21 + 12.0F, x2);
            this.rightWingInner.setRotationPoint(-4.0F - y2, y21 + 12.0F, x2);

            y *= 3.0F;
            y2 = y * (float) Math.cos((double) phyg.wingAngle) - z * (float) Math.sin((double) phyg.wingAngle);
            y21 = y * (float) Math.sin((double) phyg.wingAngle) + z * (float) Math.cos((double) phyg.wingAngle);

            this.leftWingOuter.setRotationPoint(4.0F + y2, y21 + 12.0F, x2);
            this.rightWingOuter.setRotationPoint(-4.0F - y2, y21 + 12.0F, x2);

            this.leftWingInner.rotateAngleZ = phyg.wingAngle + wingBend + ((float) Math.PI / 2.0F);
            this.leftWingOuter.rotateAngleZ = phyg.wingAngle - wingBend + ((float) Math.PI / 2.0F);
            this.rightWingInner.rotateAngleZ = -(phyg.wingAngle + wingBend - ((float) Math.PI / 2.0F));
            this.rightWingOuter.rotateAngleZ = -(phyg.wingAngle - wingBend + ((float) Math.PI / 2.0F));
        }
        else {
            wingBend = -((float)Math.acos((double)phyg.wingFold));
            x = 32.0F * phyg.wingFold / 4.0F;
            y = -32.0F * (float)Math.sqrt((double)(1.0F - phyg.wingFold * phyg.wingFold)) / 4.0F;
            z = 0.0F;
            x2 = x * (float)Math.cos((double)phyg.wingAngle) - y * (float)Math.sin((double)phyg.wingAngle);
            y2 = x * (float)Math.sin((double)phyg.wingAngle) + y * (float)Math.cos((double)phyg.wingAngle);

            this.leftWingInner.setRotationPoint(4.0F + x2, y2 + 12.0F, z);
            this.rightWingInner.setRotationPoint(-4.0F - x2, y2 + 12.0F, z);

            x *= 3.0F;
            x2 = x * (float)Math.cos((double)phyg.wingAngle) - y * (float)Math.sin((double)phyg.wingAngle);
            y2 = x * (float)Math.sin((double)phyg.wingAngle) + y * (float)Math.cos((double)phyg.wingAngle);

            this.leftWingOuter.setRotationPoint(4.0F + x2, y2 + 12.0F, z);
            this.rightWingOuter.setRotationPoint(-4.0F - x2, y2 + 12.0F, z);

            this.leftWingInner.rotateAngleZ = phyg.wingAngle + wingBend + ((float)Math.PI / 2.0F);
            this.leftWingOuter.rotateAngleZ = phyg.wingAngle - wingBend + ((float)Math.PI / 2.0F);
            this.rightWingInner.rotateAngleZ = -(phyg.wingAngle + wingBend - ((float)Math.PI / 2.0F));
            this.rightWingOuter.rotateAngleZ = -(phyg.wingAngle - wingBend + ((float)Math.PI / 2.0F));

        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if(isChild) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0F, 1.0F, 0.0F);

        }
        leftWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leftWingOuter.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightWingOuter.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
