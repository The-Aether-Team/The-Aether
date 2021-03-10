package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.ResourceLocation;

public class PhoenixArrowRenderer<T extends AbstractArrowEntity> extends ArrowRenderer<T> {
    private static final ResourceLocation PHOENIX_ARROW_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/flaming_arrow.png");

    public PhoenixArrowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return PHOENIX_ARROW_TEXTURE;
    }
}
