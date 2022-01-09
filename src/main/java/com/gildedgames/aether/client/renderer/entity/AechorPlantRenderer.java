package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.AechorPlantModel;
import com.gildedgames.aether.common.entity.monster.AechorPlantEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AechorPlantRenderer extends MobRenderer<AechorPlantEntity, AechorPlantModel<AechorPlantEntity>>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aechor_plant/aechor_plant.png");

    public AechorPlantRenderer(EntityRendererProvider.Context context) {
        super(context, new AechorPlantModel(context.bakeLayer(AetherModelLayers.AECHOR_PLANT)), 0.3F);
    }

    protected void scale(AechorPlantEntity aechorPlant, PoseStack matrixStackIn, float partialTickTime) {
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

        this.getModel().sinage = f1;
        this.getModel().sinage2 = f3;
        float f2 = 0.625F + 1.0F / 6.0F;
        this.getModel().size = f2;
        this.shadowRadius = f2 - 0.25F;

        matrixStackIn.translate(0.0D, 1.2D, 0.0D);
        matrixStackIn.scale(0.75F + ((float) aechorPlant.size * 0.125F), 1.0F, 0.75F + ((float) aechorPlant.size * 0.125F));
    }

    @Override
    public ResourceLocation getTextureLocation(AechorPlantEntity entity) {
        return TEXTURE;
    }


}
