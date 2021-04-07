package com.gildedgames.aether.client.renderer.accessory.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

public class GlovesModel extends BipedModel<LivingEntity>
{
    public GlovesModel(boolean isSlim) {
        super(1.0F);
        this.texWidth = 16;
        this.texHeight = 16;
        this.rightArm = new ModelRenderer(this, 0, 0);
        this.leftArm = new ModelRenderer(this, 0, 0);
        this.leftArm.mirror = true;
        if (!isSlim) {
            this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
            this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F);
            this.leftArm.setPos(5.0F, 2.0F, 0.0F);
            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F);
        } else {
            this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
            this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.6F);
            this.leftArm.setPos(5.0F, 2.0F, 0.0F);
            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.6F);
        }
    }

    @Override
    public void renderToBuffer(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
        this.rightArm.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
        this.leftArm.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
    }
}
