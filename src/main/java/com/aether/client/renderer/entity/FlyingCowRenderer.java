package com.aether.client.renderer.entity;

import com.aether.Aether;
import com.aether.client.renderer.entity.layers.FlyingCowSaddleLayer;
import com.aether.client.renderer.entity.layers.FlyingCowWingsLayer;
import com.aether.entity.passive.FlyingCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;

public class FlyingCowRenderer extends MobRenderer<FlyingCowEntity, CowModel<FlyingCowEntity>> {
    private static final ResourceLocation FLYING_COW_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/flying_cow/flying_cow.png");
    public FlyingCowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
        this.addLayer(new FlyingCowWingsLayer(this));
        this.addLayer(new FlyingCowSaddleLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(FlyingCowEntity entity) {
        return FLYING_COW_TEXTURE;
    }
}
