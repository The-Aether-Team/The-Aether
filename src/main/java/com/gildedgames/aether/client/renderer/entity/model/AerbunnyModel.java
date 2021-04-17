package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class AerbunnyModel extends EntityModel<AerbunnyEntity> {

    public ModelRenderer a;
    public ModelRenderer b;
    public ModelRenderer b2;
    public ModelRenderer b3;
    public ModelRenderer e1;
    public ModelRenderer e2;
    public ModelRenderer ff1;
    public ModelRenderer ff2;
    public ModelRenderer g;
    public ModelRenderer g2;
    public ModelRenderer h;
    public ModelRenderer h2;
    public float puffiness;

    public AerbunnyModel() {
        byte byte0 = 16;
        this.a = new ModelRenderer(this, 0, 0);
        this.a.addBox(-2.0F, -1.0F, -4.0F, 4, 4, 6, 0.0F);
        this.a.setPos(0.0F, (float)(-1 + byte0), -4.0F);
        this.g = new ModelRenderer(this, 14, 0);
        this.g.addBox(-2.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
        this.g.setPos(0.0F, (float)(-1 + byte0), -4.0F);
        this.g2 = new ModelRenderer(this, 14, 0);
        this.g2.addBox(1.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
        this.g2.setPos(0.0F, (float)(-1 + byte0), -4.0F);
        this.h = new ModelRenderer(this, 20, 0);
        this.h.addBox(-4.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
        this.h.setPos(0.0F, (float)(-1 + byte0), -4.0F);
        this.h2 = new ModelRenderer(this, 20, 0);
        this.h2.addBox(2.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
        this.h2.setPos(0.0F, (float)(-1 + byte0), -4.0F);
        this.b = new ModelRenderer(this, 0, 10);
        this.b.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
        this.b.setPos(0.0F, (float)(byte0), 0.0F);
        this.b2 = new ModelRenderer(this, 0, 24);
        this.b2.addBox(-2.0F, 4.0F, -2.0F, 4, 3, 4, 0.0F);
        this.b2.setPos(0.0F, (float)(byte0), 0.0F);
        this.b3 = new ModelRenderer(this, 29, 0);
        this.b3.addBox(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);
        this.b3.setPos(0.0F, 0.0F, 0.0F);
        this.e1 = new ModelRenderer(this, 24, 16);
        this.e1.addBox(-2.0F, 0.0F, -1.0F, 2, 2, 2);
        this.e1.setPos(3.0F, (float)(3 + byte0), -3.0F);
        this.e2 = new ModelRenderer(this, 24, 16);
        this.e2.addBox(0.0F, 0.0F, -1.0F, 2, 2, 2);
        this.e2.setPos(-3.0F, (float)(3 + byte0), -3.0F);
        this.ff1 = new ModelRenderer(this, 16, 24);
        this.ff1.addBox(-2.0F, 0.0F, -4.0F, 2, 2, 4);
        this.ff1.setPos(3.0F, (float)(3 + byte0), 4.0F);
        this.ff2 = new ModelRenderer(this, 16, 24);
        this.ff2.addBox(0.0F, 0.0F, -4.0F, 2, 2, 4);
        this.ff2.setPos(-3.0F, (float)(3 + byte0), 4.0F);
    }

    @Override
    public void setupAnim(AerbunnyEntity aerbunny, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.a.xRot = -(netHeadYaw / (180F / (float)Math.PI));
        this.a.yRot = headPitch / (180F / (float)Math.PI);
        this.g.xRot = this.a.xRot;
        this.g.yRot = this.a.yRot;
        this.g2.xRot = this.a.xRot;
        this.g2.yRot = this.a.yRot;
        this.h.xRot = this.a.xRot;
        this.h.yRot = this.a.yRot;
        this.h2.xRot = this.a.xRot;
        this.h2.yRot = this.a.yRot;
        this.b.xRot = ((float)Math.PI / 2F);
        this.b2.xRot = ((float)Math.PI / 2F);
        this.b3.xRot = ((float)Math.PI / 2F);
        this.e1.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
        this.ff1.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.2F * limbSwingAmount;
        this.e2.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
        this.ff2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.2F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float a;

        if (this.young) {
            a = 2.0F;
            matrixStack.pushPose();
//            matrixStack.translate(0.0F, f5, f5);
            this.a.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.g.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.g2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.h.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.h2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            matrixStack.popPose();
            matrixStack.scale(1.0F / a, 1.0F / a, 1.0F / a);
            matrixStack.translate(0.0F, 18.0F, 0.0F);
            this.b.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.b2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.e1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.e2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.ff1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.ff2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            float a1 = 1.0F + this.puffiness * 0.5F;
            matrixStack.translate(0.0F, 1.0F, 0.0F);
            matrixStack.scale(a1, a1, a1);
            this.b3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        else {
            this.a.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.g.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.g2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.h.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.h2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.b.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.b2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            matrixStack.pushPose();
            a = 1.0F + this.puffiness * 0.5F;
            matrixStack.translate(0.0F, 1.0F, 0.0F);
            matrixStack.scale(a, a, a);
            this.b3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            matrixStack.popPose();
            this.e1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.e2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.ff1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.ff2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }
}
