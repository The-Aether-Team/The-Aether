package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.CockatriceMarkingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aether.client.renderer.entity.state.BirdRenderState;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CockatriceRenderer extends MobRenderer<Cockatrice, BirdRenderState, CockatriceModel> {
    private static final ResourceLocation COCKATRICE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice.png");

    public CockatriceRenderer(EntityRendererProvider.Context context) {
        super(context, new CockatriceModel(context.bakeLayer(AetherModelLayers.COCKATRICE)), 0.7F);
        this.addLayer(new CockatriceMarkingsLayer<>(this));
    }

    @Override
    public BirdRenderState createRenderState() {
        return new BirdRenderState();
    }

    @Override
    protected void scale(BirdRenderState cockatrice, PoseStack poseStack) {
        poseStack.scale(1.8F, 1.8F, 1.8F);
    }

    @Override
    public void extractRenderState(Cockatrice entity, BirdRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isEntityOnGround = entity.isEntityOnGround();
        reusedState.ageInTicks = getBob(reusedState, entity, partialTick);
    }

    /**
     * Passes the Cockatrice's wing rotation to the model as the "ageInTicks" parameter.
     *
     * @param cockatrice   The {@link Cockatrice} entity.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     * @return The {@link Float} for the petal rotation.
     */
    protected float getBob(BirdRenderState state, Cockatrice cockatrice, float partialTicks) {
        return state.setupWingsAnimation(cockatrice, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(BirdRenderState cockatrice) {
        return COCKATRICE_TEXTURE;
    }
}
