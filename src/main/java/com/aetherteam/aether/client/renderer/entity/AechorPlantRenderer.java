package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.AechorPlantModel;
import com.aetherteam.aether.client.renderer.entity.state.AechorPlantRenderState;
import com.aetherteam.aether.entity.monster.AechorPlant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AechorPlantRenderer extends MobRenderer<AechorPlant, AechorPlantRenderState, AechorPlantModel> {
    private static final ResourceLocation AECHOR_PLANT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/aechor_plant/aechor_plant.png");

    public AechorPlantRenderer(EntityRendererProvider.Context context) {
        super(context, new AechorPlantModel(context.bakeLayer(AetherModelLayers.AECHOR_PLANT)), 0.3F);
    }

    @Override
    public AechorPlantRenderState createRenderState() {
        return new AechorPlantRenderState();
    }

    @Override
    public void extractRenderState(AechorPlant entity, AechorPlantRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isTargeting = entity.getTargetingEntity();
        reusedState.isHurt = entity.hurtTime > 0;
        reusedState.sinage = entity.getSinage();
        reusedState.sinageAdd = entity.getSinageAdd();
        reusedState.ageInTicks = getBob(entity, partialTick);
    }

    /**
     * Scales the Aechor Plant according to its size.
     *
     * @param aechorPlant  The {@link AechorPlantRenderState} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     */
    @Override
    protected void scale(AechorPlantRenderState aechorPlant, PoseStack poseStack) {
        float f2 = 0.625F + aechorPlant.scale / 6.0F;
        poseStack.scale(f2, f2, f2);
        poseStack.translate(0.0, 1.2, 0.0);
        this.shadowRadius = f2 - 0.25F;
    }

    /**
     * Passes the Aechor Plant's petal rotation to the model as the "ageInTicks" parameter.
     *
     * @param aechorPlant  The {@link AechorPlant} entity.
     * @param partialTick The {@link Float} for the game's partial ticks.
     * @return The {@link Float} for the petal rotation.
     */

    protected float getBob(AechorPlant aechorPlant, float partialTick) {
        return Mth.lerp(partialTick, aechorPlant.getSinage(), aechorPlant.getSinage() + aechorPlant.getSinageAdd());
    }

    @Override
    public ResourceLocation getTextureLocation(AechorPlantRenderState aechorPlant) {
        return AECHOR_PLANT_TEXTURE;
    }
}
