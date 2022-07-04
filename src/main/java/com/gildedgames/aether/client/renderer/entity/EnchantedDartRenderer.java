package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.entity.projectile.dart.EnchantedDart;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class EnchantedDartRenderer extends ArrowRenderer<EnchantedDart> {
    public static final ResourceLocation ENCHANTED_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/enchanted_dart.png");

    public EnchantedDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull EnchantedDart dart) {
        return ENCHANTED_DART_TEXTURE;
    }
}
