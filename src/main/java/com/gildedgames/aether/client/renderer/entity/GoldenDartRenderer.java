package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.dart.AbstractDartEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoldenDartRenderer extends ArrowRenderer<AbstractDartEntity>
{
    public static final ResourceLocation GOLDEN_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/golden_dart.png");

    public GoldenDartRenderer(EntityRendererProvider.Context renderer) {
        super(renderer);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractDartEntity entity) {
        return GOLDEN_DART_TEXTURE;
    }
}
