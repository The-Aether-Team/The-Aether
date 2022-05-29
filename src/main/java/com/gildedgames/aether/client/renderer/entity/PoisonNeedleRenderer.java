package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.PoisonNeedle;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class PoisonNeedleRenderer extends ArrowRenderer<PoisonNeedle> {
    public static final ResourceLocation POISON_NEEDLE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/poison_needle.png");

    public PoisonNeedleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull PoisonNeedle dart) {
        return POISON_NEEDLE_TEXTURE;
    }
}
