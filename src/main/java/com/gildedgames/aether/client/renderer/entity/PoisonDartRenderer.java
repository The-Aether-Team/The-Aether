package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.dart.AbstractDartEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class PoisonDartRenderer extends ArrowRenderer<AbstractDartEntity>
{
    public static final ResourceLocation POISON_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/poison_dart.png");

    public PoisonDartRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractDartEntity entity) {
        return POISON_DART_TEXTURE;
    }
}
