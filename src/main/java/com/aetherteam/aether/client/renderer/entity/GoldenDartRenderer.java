package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.projectile.dart.GoldenDart;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GoldenDartRenderer extends ArrowRenderer<GoldenDart> {
    public static final ResourceLocation GOLDEN_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/golden_dart.png");

    public GoldenDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

   
    @Override
    public ResourceLocation getTextureLocation(GoldenDart dart) {
        return GOLDEN_DART_TEXTURE;
    }
}
