package com.gildedgames.aether.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderer.class)
public interface EntityRendererAccessor {
    @Accessor("shadowRadius")
    float aether$getShadowRadius();

    @Accessor("shadowRadius")
    void aether$setShadowRadius(float shadowRadius);
}