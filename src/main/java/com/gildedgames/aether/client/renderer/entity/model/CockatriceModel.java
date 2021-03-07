package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CockatriceModel extends EntityModel<CockatriceEntity> {
    private final ModelRenderer head, body;
    private final ModelRenderer legs, legs2;
    private final ModelRenderer wings, wings2;
    private final ModelRenderer jaw, neck;
    private final ModelRenderer feather1, feather2, feather3;

    public CockatriceModel() {
        this.head = new ModelRenderer(this, 0, 13);
        this.head.addBox(-2.0F, -4.0F, -6.0F, 4, 4, 8, 0.0F);
        this.head.setRotationPoint(0.0F, -8 + 16, -4.0F);

        this.jaw = new ModelRenderer(this, 24, 13);
        this.jaw.addBox(-2.0F, -1.0F, -6.0F, 4, 1, 8, -0.1F);
        this.jaw.setRotationPoint(0.0F, -8 + 16, -4.0F);

        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-3.0F, -3.0F, 0.0F, 6, 8, 5, 0.0F);
        this.body.setRotationPoint(0.0F, 16, 0.0F);

        this.legs = new ModelRenderer(this, 22, 0);
        this.legs.addBox(-1.0F, -1.0F, -1.0F, 2, 9, 2);
        this.legs.setRotationPoint(-2.0F, 16, 1.0F);

        this.legs2 = new ModelRenderer(this, 22, 0);
        this.legs2.addBox(-1.0F, -1.0F, -1.0F, 2, 9, 2);
        this.legs2.setRotationPoint(2.0F, 16, 1.0F);

        this.wings = new ModelRenderer(this, 52, 0);
        this.wings.addBox(-1.0F, -0.0F, -1.0F, 1, 8, 4);
        this.wings.setRotationPoint(-3.0F, (16), 2.0F);

        this.wings2 = new ModelRenderer(this, 52, 0);
        this.wings2.addBox(0.0F, -0.0F, -1.0F, 1, 8, 4);
        this.wings2.setRotationPoint(3.0F, -4 + 16, 0.0F);

        this.neck = new ModelRenderer(this, 44, 0);
        this.neck.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2);
        this.neck.setRotationPoint(0.0F, -2 + 16, -4.0F);

        this.feather1 = new ModelRenderer(this, 30, 0);
        this.feather1.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather1.setRotationPoint(0.0F, 1 + 16, 1.0F);
        this.feather2 = new ModelRenderer(this, 30, 0);
        this.feather2.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather2.setRotationPoint(0.0F, 1 + 16, 1.0F);
        this.feather3 = new ModelRenderer(this, 30, 0);
        this.feather3.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather3.setRotationPoint(0.0F, 1 + 16, 1.0F);
        this.feather1.rotationPointY += 0.5F;
        this.feather2.rotationPointY += 0.5F;
        this.feather3.rotationPointY += 0.5F;
    }

    @Override
    public void setRotationAngles(CockatriceEntity cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch / 57.29578F;
        this.head.rotateAngleY = netHeadYaw / 57.29578F;

        this.jaw.rotateAngleX = head.rotateAngleX;
        this.jaw.rotateAngleY = head.rotateAngleY;

        this.body.rotateAngleX = 1.570796F;

        this.legs.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legs2.rotateAngleX = MathHelper.cos((float)(limbSwing * 0.6662F + Math.PI)) * 1.4F * limbSwingAmount;

        if (ageInTicks > 0.001F) {
            this.wings.rotationPointZ = -1F;
            this.wings2.rotationPointZ = -1F;
            this.wings.rotationPointY = 12F;
            this.wings2.rotationPointY = 12F;
            this.wings.rotateAngleX = 0.0F;
            this.wings2.rotateAngleX = 0.0F;
            this.wings.rotateAngleZ = ageInTicks;
            this.wings2.rotateAngleZ = -ageInTicks;
            this.legs.rotateAngleX = 0.6F;
            this.legs2.rotateAngleX = 0.6F;
        }
        else {
            this.wings.rotationPointZ = -3F;
            this.wings2.rotationPointZ = -3F;
            this.wings.rotationPointY = 14F;
            this.wings2.rotationPointY = 14F;
            this.wings.rotateAngleX = (float) (Math.PI / 2.0F);
            this.wings2.rotateAngleX = (float) (Math.PI / 2.0F);
            this.wings.rotateAngleZ = 0.0F;
            this.wings2.rotateAngleZ = 0.0F;
        }

        this.feather1.rotateAngleY = -0.375F;
        this.feather2.rotateAngleY = 0.0F;
        this.feather3.rotateAngleY = 0.375F;
        this.feather1.rotateAngleX = 0.25F;
        this.feather2.rotateAngleX = 0.25F;
        this.feather3.rotateAngleX = 0.25F;

        this.neck.rotateAngleX = 0.0F;
        this.neck.rotateAngleY = head.rotateAngleY;
        this.jaw.rotateAngleX += 0.35F;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.legs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.legs2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.jaw.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wings.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wings2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

}
