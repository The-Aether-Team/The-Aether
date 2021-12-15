package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.monster.WhirlwindEntity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WhirlwindRenderer extends EntityRenderer<WhirlwindEntity> {
    public WhirlwindRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(WhirlwindEntity entity) {
        return null;
    }
}
