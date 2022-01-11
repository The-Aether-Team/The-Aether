package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.monster.Whirlwind;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WhirlwindRenderer extends EntityRenderer<Whirlwind> {
    public WhirlwindRenderer(EntityRendererProvider.Context renderer) {
        super(renderer);
    }

    @Override
    public ResourceLocation getTextureLocation(Whirlwind entity) {
        return null;
    }
}
