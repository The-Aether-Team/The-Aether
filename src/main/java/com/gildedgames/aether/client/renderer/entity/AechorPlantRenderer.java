package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.entity.monster.AechorPlantEntity;
import com.gildedgames.aether.client.renderer.entity.model.AechorPlantModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class AechorPlantRenderer extends MobRenderer<AechorPlantEntity, AechorPlantModel<AechorPlantEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/aechor_plant/aechor_plant.png");

    public AechorPlantRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AechorPlantModel(), 0.3F);
    }

    protected void preRenderCallback(AechorPlantEntity aechorPlant, MatrixStack matrixStackIn, float partialTickTime) {
        float f1 = (float)Math.sin((double)aechorPlant.sinage);
        float f3;

        if (aechorPlant.hurtTime > 0) {
            f1 *= 0.45F;
            f1 -= 0.125F;
            f3 = 1.75F + (float)Math.sin((double)(aechorPlant.sinage + 2.0F)) * 1.5F;
        }
        else {
            f1 *= 0.125F;
            f3 = 1.75F;
        }

        this.getEntityModel().sinage = f1;
        this.getEntityModel().sinage2 = f3;
        float f2 = 0.625F + 1.0F / 6.0F;
        this.getEntityModel().size = f2;
        this.shadowSize = f2 - 0.25F;

        matrixStackIn.translate(0.0D, 1.2D, 0.0D);
        matrixStackIn.scale(0.75F + ((float) aechorPlant.size * 0.125F), 1.0F, 0.75F + ((float) aechorPlant.size * 0.125F));
    }

    @Override
    public ResourceLocation getEntityTexture(AechorPlantEntity entity) {
        return TEXTURE;
    }


}
