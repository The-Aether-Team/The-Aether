package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.SheepuffModel;
import com.gildedgames.aether.client.renderer.entity.model.SheepuffWoolModel;
import com.gildedgames.aether.client.renderer.entity.model.SheepuffedModel;
import com.gildedgames.aether.entity.passive.SheepuffEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;

public class SheepuffCoatLayer extends LayerRenderer<SheepuffEntity, SheepuffModel> {
    private static final ResourceLocation FUR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/sheepuff/sheepuff_fur.png");

    private SheepuffWoolModel woolModel = new SheepuffWoolModel();
    private SheepuffedModel puffedModel = new SheepuffedModel();

    public SheepuffCoatLayer(IEntityRenderer<SheepuffEntity, SheepuffModel> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SheepuffEntity sheepuff, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!sheepuff.getSheared() && !sheepuff.isInvisible()) {
            matrixStackIn.push();
            float f;
            float f1;
            float f2;
            if (sheepuff.hasCustomName() && "jeb_".equals(sheepuff.getName().getUnformattedComponentText())) {
                int i = sheepuff.ticksExisted / 25 + sheepuff.getEntityId();
                int j1 = DyeColor.values().length;
                int k = i % j1;
                int l = (i + 1) % j1;
                float f3 = (sheepuff.ticksExisted % 25 + partialTicks) / 25.0F;
                float[] afloat1 = SheepEntity.getDyeRgb(DyeColor.byId(k));
                float[] afloat2 = SheepEntity.getDyeRgb(DyeColor.byId(l));
                f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            }
            else {
                float[] afloat = SheepEntity.getDyeRgb(sheepuff.getFleeceColor());
                f = afloat[0];
                f1 = afloat[1];
                f2 = afloat[2];
            }

            if (sheepuff.getPuffed()) {
                renderCopyCutoutModel(this.getEntityModel(), this.puffedModel, FUR_TEXTURE, matrixStackIn, bufferIn, packedLightIn, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);
            }
            else {
                renderCopyCutoutModel(this.getEntityModel(), this.woolModel, FUR_TEXTURE, matrixStackIn, bufferIn, packedLightIn, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);
            }

            matrixStackIn.pop();
        }

    }
}
