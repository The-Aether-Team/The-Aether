package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.dart.AbstractDartEntity;
import com.gildedgames.aether.common.entity.projectile.dart.EnchantedDartEntity;
import com.gildedgames.aether.common.entity.projectile.dart.GoldenDartEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DartRenderer extends ArrowRenderer<AbstractDartEntity>
{
    public static final ResourceLocation GOLDEN_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/golden_dart.png");
    public static final ResourceLocation ENCHANTED_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/enchanted_dart.png");
    public static final ResourceLocation POISON_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/poison_dart.png");

    public DartRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractDartEntity entity) {
        return entity instanceof GoldenDartEntity ? GOLDEN_DART_TEXTURE : entity instanceof EnchantedDartEntity ? ENCHANTED_DART_TEXTURE : POISON_DART_TEXTURE;
    }
}
