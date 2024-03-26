package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.AechorPlantModel;
import com.aetherteam.aether.entity.monster.AechorPlant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AechorPlantRenderer extends MobRenderer<AechorPlant, AechorPlantModel> {
    private static final ResourceLocation AECHOR_PLANT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aechor_plant/aechor_plant.png");

    public AechorPlantRenderer(EntityRendererProvider.Context context) {
        super(context, new AechorPlantModel(context.bakeLayer(AetherModelLayers.AECHOR_PLANT)), 0.3F);
    }

    /**
     * Scales the Aechor Plant according to its size.
     *
     * @param aechorPlant  The {@link AechorPlant} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     */
    @Override
    protected void scale(AechorPlant aechorPlant, PoseStack poseStack, float partialTicks) {
        float f2 = 0.625F + aechorPlant.getSize() / 6.0F;
        poseStack.scale(f2, f2, f2);
        poseStack.translate(0.0, 1.2, 0.0);
        this.shadowRadius = f2 - 0.25F;
    }

    /**
     * Passes the Aechor Plant's petal rotation to the model as the "ageInTicks" parameter.
     *
     * @param aechorPlant  The {@link AechorPlant} entity.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     * @return The {@link Float} for the petal rotation.
     */
    @Override
    protected float getBob(AechorPlant aechorPlant, float partialTicks) {
        return Mth.lerp(partialTicks, aechorPlant.getSinage(), aechorPlant.getSinage() + aechorPlant.getSinageAdd());
    }

    @Override
    public ResourceLocation getTextureLocation(AechorPlant aechorPlant) {
        return AECHOR_PLANT_TEXTURE;
    }
}
