package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.AechorPlantEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;

public class AechorPlantModel<T extends Entity> extends EntityModel<AechorPlantEntity> {

    private final ModelRenderer[] Petal = new ModelRenderer[10];
    private final ModelRenderer[] Leaf = new ModelRenderer[10];
    private final ModelRenderer[] StamenStem = new ModelRenderer[3];
    private final ModelRenderer[] StamenTip = new ModelRenderer[3];
    private final ModelRenderer[] Thorn = new ModelRenderer[4];
    private final ModelRenderer Stem = new ModelRenderer(this, 24, 13);
    private final ModelRenderer Head = new ModelRenderer(this, 0, 12);

    public float sinage;
    public float sinage2;
    public float size;

    public AechorPlantModel() {
        this(0.0F);
    }
    public AechorPlantModel(float size) {

        for (int i = 0; i < 10; i++) {
            Petal[i] = new ModelRenderer(this, 0, 0);

            if (i % 2 == 0) {
                Petal[i] = new ModelRenderer(this, 28, 2);
                Petal[i].addBox(-4.0F, -1.0F, -12.0F, 8, 0.5F, 10, Math.abs(size - 0.25F));
                Petal[i].setPos(0.0F, 1.0F, 0.0F);
            } else {
                Petal[i] = new ModelRenderer(this, 0, 0);
                Petal[i].addBox(-4.0F, -1.0F, -12.0F, 8, 0.5F, 10, Math.abs(size - 0.25F));
            }

            Leaf[i] = new ModelRenderer(this, 38, 13);
            Leaf[i].addBox(-2.0F, -1.0F, -9.5F, 4, 1, 8, Math.abs(size - 0.15F));
            Leaf[i].setPos(0.0F, 1.0F, 0.0F);

        }
        for (int i = 0; i < 3; i++) {
            StamenStem[i] = new ModelRenderer(this, 36, 13);
            StamenStem[i].addBox(0.0F, -9.0F, -1.5F, 1, 6, 1, Math.abs(size - 0.25F));
            StamenStem[i].setPos(0.0F, 1.0F, 0.0F);

            StamenTip[i] = new ModelRenderer(this, 32, 15);
            StamenTip[i].addBox(0.0F, -10.0F, -1.5F, 1, 1, 1, Math.abs(size + 0.125F));
            StamenTip[i].setPos(0.0F, 1.0F, 0.0F);
        }

        Head.addBox(-3.0F, -3.0F, -3.0F, 6, 2, 6, Math.abs(size + 0.75F));
        Head.setPos(0.0F, 1.0F, 0.0F);

        Stem.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, Math.abs(size));
        Stem.setPos(0.0F, 1.0F, 0.0F);

        for (int i = 0; i < 4; i++) {
            Thorn[i] = new ModelRenderer(this, 32, 13);
            Thorn[i].setPos(0.0F, 1.0F, 0.0F);
        }

        Thorn[0].addBox(-1.75F, 1.25F, -1F, 1, 1, 1, Math.abs(size - 0.25F));
        Thorn[1].addBox(-1F, 2.25F, 0.75F, 1, 1, 1, Math.abs(size - 0.25F));
        Thorn[2].addBox(0.75F, 1.25F, 0F, 1, 1, 1, Math.abs(size - 0.25F));
        Thorn[3].addBox(0F, 2.25F, -1.75F, 1, 1, 1, Math.abs(size - 0.25F));
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        for (int i = 0; i < 10; i++) {
            Petal[i].render(matrixStack, buffer, packedLight, packedOverlay);
            Leaf[i].render(matrixStack, buffer, packedLight, packedOverlay);
        }
        for (int i = 0; i < 3; i++) {
            StamenStem[i].render(matrixStack, buffer, packedLight, packedOverlay);
            StamenTip[i].render(matrixStack, buffer, packedLight, packedOverlay);
        }
        for (int i = 0; i < 4; i++) {
            Thorn[i].render(matrixStack, buffer, packedLight, packedOverlay);
        }

        Head.render(matrixStack, buffer, packedLight, packedOverlay);
        Stem.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setupAnim(AechorPlantEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Head.xRot = 0.0F;
        Head.yRot = headPitch / 57.29578F;

        float boff = this.sinage2;

        Stem.yRot = Head.yRot;
        Stem.y = boff * 0.5F;

        for(int i = 0; i < 10; i++)
        {
            if (i < 3)
            {
                StamenStem[i].xRot = 0.2F + ((float)i / 15F);
                StamenStem[i].yRot = Head.yRot + 0.1F;
                StamenStem[i].yRot += ((Math.PI * 2) / (float)3) * (float)i;
                StamenStem[i].xRot += sinage * 0.4F;

                StamenTip[i].xRot = 0.2F + ((float)i / 15F);
                StamenTip[i].yRot = Head.yRot + 0.1F;
                StamenTip[i].yRot += ((Math.PI * 2) / (float)3) * (float)i;
                StamenTip[i].xRot += sinage * 0.4F;

                StamenStem[i].y = boff + ((sinage) * 2F);
                StamenTip[i].y = boff + ((sinage) * 2F);
            }

            if (i < 4)
            {
                Thorn[i].yRot = Head.yRot;
                Thorn[i].y = boff * 0.5F;
            }

            Petal[i].xRot = ((i % 2 == 0) ? -0.25F : -0.4125F);
            Petal[i].xRot += sinage;
            Petal[i].yRot = Head.yRot;
            Petal[i].yRot += ((Math.PI * 2) / (float)10) * (float)i;

            Leaf[i].xRot = ((i % 2 == 0) ? 0.1F : 0.2F);
            Leaf[i].xRot += sinage * 0.75F;
            Leaf[i].yRot = (float) (Head.yRot + ((Math.PI * 2) / (float)10 / 2F));
            Leaf[i].yRot += ((Math.PI * 2) / (float)10) * (float)i;

            Petal[i].y = boff;
            Leaf[i].y = boff;
        }

        Head.y = boff + ((sinage) * 2F);
    }

}
