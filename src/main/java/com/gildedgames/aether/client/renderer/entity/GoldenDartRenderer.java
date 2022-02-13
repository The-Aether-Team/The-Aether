package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.dart.AbstractDart;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class GoldenDartRenderer extends ArrowRenderer<AbstractDart> {
    public static final ResourceLocation GOLDEN_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/golden_dart.png");

    public GoldenDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull AbstractDart dart) {
        return GOLDEN_DART_TEXTURE;
    }
}
