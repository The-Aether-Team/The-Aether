package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CrystalModel<T extends Entity> extends EntityModel<T>
{
    public final ModelRenderer[] main = new ModelRenderer[3];

    public CrystalModel() {
        this.main[0] = new ModelRenderer(this, 0, 0);
        this.main[1] = new ModelRenderer(this, 32, 0);
        this.main[2] = new ModelRenderer(this, 0, 16);
        for (int i = 0; i < 3; i++) {
            this.main[i].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
            this.main[i].setPos(0.0F, 0.0F, 0.0F);
        }
    }

    @Override
    public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        for (int i = 0; i < 3; i++) {
            this.main[i].yRot = p_225597_5_ * ((float) Math.PI / 180F);
            this.main[i].xRot = p_225597_6_ * ((float) Math.PI / 180F);
        }
    }

    @Override
    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        for (int i = 0; i < 3; i++) {
            this.main[i].render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        }
    }
}
