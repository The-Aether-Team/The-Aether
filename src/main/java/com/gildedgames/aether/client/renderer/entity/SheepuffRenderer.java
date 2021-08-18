package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.layers.SheepuffWoolLayer;
import com.gildedgames.aether.client.renderer.entity.model.SheepuffModel;
import com.gildedgames.aether.common.entity.passive.SheepuffEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SheepuffRenderer extends MobRenderer<SheepuffEntity, SheepuffModel>
{
    private static final ResourceLocation SHEEPUFF_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff.png");
    
    public SheepuffRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SheepuffModel(), 0.7F);
        this.addLayer(new SheepuffWoolLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SheepuffEntity entity) {
        return SHEEPUFF_TEXTURE;
    }
}
