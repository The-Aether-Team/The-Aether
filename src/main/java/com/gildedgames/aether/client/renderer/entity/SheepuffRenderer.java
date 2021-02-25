package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.layers.SheepuffCoatLayer;
import com.gildedgames.aether.client.renderer.entity.model.SheepuffModel;
import com.gildedgames.aether.entity.passive.SheepuffEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SheepuffRenderer extends MobRenderer<SheepuffEntity, SheepuffModel> {
    private static final ResourceLocation SHEEPUFF_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/sheepuff/sheepuff.png");
    
    public SheepuffRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SheepuffModel(), 0.7F);
        this.addLayer(new SheepuffCoatLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(SheepuffEntity entity) {
        return SHEEPUFF_TEXTURE;
    }
}
