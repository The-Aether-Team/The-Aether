package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.SheepuffWoolLayer;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.client.renderer.entity.state.SheepuffRenderState;
import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SheepuffRenderer extends AgeableMobRenderer<Sheepuff, SheepuffRenderState, SheepuffModel> {
    private static final ResourceLocation SHEEPUFF_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff.png");

    public SheepuffRenderer(EntityRendererProvider.Context context) {
        super(context, new SheepuffModel(context.bakeLayer(AetherModelLayers.SHEEPUFF)), new SheepuffModel(context.bakeLayer(AetherModelLayers.SHEEPUFF_BABY)), 0.7F);
        this.addLayer(new SheepuffWoolLayer(this, context.getModelSet()));
    }

    @Override
    public SheepuffRenderState createRenderState() {
        return new SheepuffRenderState();
    }

    @Override
    public void extractRenderState(Sheepuff sheepuff, SheepuffRenderState renderState, float p_361157_) {
        super.extractRenderState(sheepuff, renderState, p_361157_);
        renderState.headEatAngleScale = sheepuff.getHeadEatAngleScale(p_361157_);
        renderState.headEatPositionScale = sheepuff.getHeadEatPositionScale(p_361157_);
        renderState.isSheared = sheepuff.isSheared();
        renderState.woolColor = sheepuff.getColor();
        renderState.id = sheepuff.getId();
        renderState.puff = sheepuff.getPuffed();
    }


    @Override
    public ResourceLocation getTextureLocation(SheepuffRenderState sheepuff) {
        return SHEEPUFF_TEXTURE;
    }
}
