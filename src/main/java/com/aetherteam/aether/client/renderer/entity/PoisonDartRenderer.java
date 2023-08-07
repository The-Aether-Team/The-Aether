package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.projectile.dart.PoisonDart;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PoisonDartRenderer extends ArrowRenderer<PoisonDart> {
    private static final ResourceLocation POISON_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/poison_dart.png");

    public PoisonDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PoisonDart dart) {
        return POISON_DART_TEXTURE;
    }
}
