package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.SheepuffWoolLayer;
import com.gildedgames.aether.client.renderer.entity.model.SheepuffModel;
import com.gildedgames.aether.common.entity.passive.SheepuffEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepuffRenderer extends MobRenderer<SheepuffEntity, SheepuffModel>
{
    private static final ResourceLocation SHEEPUFF_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff.png");
    
    public SheepuffRenderer(EntityRendererProvider.Context context) {
        super(context, new SheepuffModel(context.bakeLayer(AetherModelLayers.SHEEPUFF)), 0.7F);
        this.addLayer(new SheepuffWoolLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(SheepuffEntity entity) {
        return SHEEPUFF_TEXTURE;
    }
}
