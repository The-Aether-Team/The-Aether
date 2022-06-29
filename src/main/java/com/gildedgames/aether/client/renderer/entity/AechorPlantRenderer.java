package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.AechorPlantModel;
import com.gildedgames.aether.entity.monster.AechorPlant;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class AechorPlantRenderer extends MobRenderer<AechorPlant, AechorPlantModel> {
    private static final ResourceLocation AECHOR_PLANT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aechor_plant/aechor_plant.png");

    public AechorPlantRenderer(EntityRendererProvider.Context context) {
        super(context, new AechorPlantModel(context.bakeLayer(AetherModelLayers.AECHOR_PLANT)), 0.3F);
    }

    protected void scale(AechorPlant aechorPlant, @Nonnull PoseStack poseStack, float partialTickTime) {
        float f2 = 0.625F + aechorPlant.getSize() / 6.0F;
        poseStack.scale(f2, f2, f2);
        poseStack.translate(0.0, 1.2, 0.0);
        this.shadowRadius = f2 - 0.25F;
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull AechorPlant aechorPlant) {
        return AECHOR_PLANT_TEXTURE;
    }
}
