package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.monster.WhirlwindEntity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WhirlwindRenderer extends EntityRenderer<WhirlwindEntity> {
    public WhirlwindRenderer(EntityRendererProvider.Context renderer) {
        super(renderer);
    }

    @Override
    public ResourceLocation getTextureLocation(WhirlwindEntity entity) {
        return null;
    }
}
