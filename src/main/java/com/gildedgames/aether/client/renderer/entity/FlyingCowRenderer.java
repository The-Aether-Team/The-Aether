package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.FlyingCowWingsLayer;
import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.model.CowModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlyingCowRenderer extends MobRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>>
{
    private static final ResourceLocation FLYING_COW_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow.png");
    
    public FlyingCowRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new CowModel<>(renderer.bakeLayer(AetherModelLayers.FLYING_COW)), 0.7F);
        this.addLayer(new FlyingCowWingsLayer(this, renderer.getModelSet()));
        this.addLayer(new SaddleLayer<>(this, new CowModel<>(renderer.bakeLayer(AetherModelLayers.FLYING_COW)), new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_saddle.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingCowEntity entity) {
        return FLYING_COW_TEXTURE;
    }
}
