package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.SheepuffModel;
import com.gildedgames.aether.client.renderer.entity.model.SheepuffWoolModel;
import com.gildedgames.aether.common.entity.passive.SheepuffEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.resources.ResourceLocation;

public class SheepuffWoolLayer extends RenderLayer<SheepuffEntity, SheepuffModel>
{
    private static final ResourceLocation SHEEPUFF_WOOL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff_wool.png");
    private final SheepuffWoolModel woolModel;
    private final SheepuffWoolModel puffedModel;

    // woolModel (0.0F, 1.75F);
    // puffedModel (2.0F, 3.75F);

    public SheepuffWoolLayer(RenderLayerParent<SheepuffEntity, SheepuffModel> entityRendererIn, EntityModelSet modelSet) {
        super(entityRendererIn);
        woolModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_WOOL));
        puffedModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_WOOL_PUFFED));
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, SheepuffEntity sheepuff, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!sheepuff.isSheared() && !sheepuff.isInvisible()) {
            float f;
            float f1;
            float f2;
            if (sheepuff.hasCustomName() && "jeb_".equals(sheepuff.getName().getContents())) {
                int i = sheepuff.tickCount / 25 + sheepuff.getId();
                int j = DyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f3 = ((float) (sheepuff.tickCount % 25) + partialTicks) / 25.0F;
                float[] afloat1 = SheepuffEntity.getColorArray(DyeColor.byId(k));
                float[] afloat2 = SheepuffEntity.getColorArray(DyeColor.byId(l));
                f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            } else {
                float[] afloat = SheepuffEntity.getColorArray(sheepuff.getColor());
                f = afloat[0];
                f1 = afloat[1];
                f2 = afloat[2];
            }
            if (sheepuff.getPuffed()) {
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.puffedModel, SHEEPUFF_WOOL_TEXTURE, matrixStackIn, bufferIn, packedLightIn, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);
            } else {
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.puffedModel, SHEEPUFF_WOOL_TEXTURE, matrixStackIn, bufferIn, packedLightIn, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.woolModel, SHEEPUFF_WOOL_TEXTURE, matrixStackIn, bufferIn, packedLightIn, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);
            }
        }
    }
}
