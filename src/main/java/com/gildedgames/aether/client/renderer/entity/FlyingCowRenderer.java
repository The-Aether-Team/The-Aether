package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.layers.FlyingCowWingsLayer;
import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;

public class FlyingCowRenderer extends MobRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>>
{
    private static final ResourceLocation FLYING_COW_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow.png");
    
    public FlyingCowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
        this.addLayer(new FlyingCowWingsLayer(this));
        this.addLayer(new SaddleLayer<>(this, new CowModel<>(), new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_saddle.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingCowEntity entity) {
        return FLYING_COW_TEXTURE;
    }
}
