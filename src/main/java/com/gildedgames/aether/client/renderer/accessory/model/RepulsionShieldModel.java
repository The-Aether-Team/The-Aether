package com.gildedgames.aether.client.renderer.accessory.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

public class RepulsionShieldModel extends BipedModel<LivingEntity>
{
    public RepulsionShieldModel() {
        super(1.0F);
        this.texWidth = 64;
        this.texHeight = 32;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 1.1F);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 1.1F);
        this.body.setPos(0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 40, 16);
        this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.1F);
        this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 40, 16);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.1F);
        this.leftArm.setPos(5.0F, 2.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 16);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.1F);
        this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 16);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.1F);
        this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
    }

    @Override
    public void renderToBuffer(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
        this.head.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
        this.body.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
        this.rightArm.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
        this.leftArm.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
        this.rightLeg.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
        this.leftLeg.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
    }
}
