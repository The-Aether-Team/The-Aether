package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.QuadrupedWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.QuadrupedWingsModel;
import com.aetherteam.aether.entity.passive.FlyingCow;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class FlyingCowRenderer extends MobRenderer<FlyingCow, CowModel<FlyingCow>> {
    private static final ResourceLocation FLYING_COW_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow.png");
    
    public FlyingCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(AetherModelLayers.FLYING_COW)), 0.7F);
        this.addLayer(new QuadrupedWingsLayer<>(this, new QuadrupedWingsModel<>(context.bakeLayer(AetherModelLayers.FLYING_COW_WINGS)), new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_wings.png")));
        this.addLayer(new SaddleLayer<>(this, new CowModel<>(context.bakeLayer(AetherModelLayers.FLYING_COW_SADDLE)), new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_saddle.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingCow flyingCow) {
        return FLYING_COW_TEXTURE;
    }
}
