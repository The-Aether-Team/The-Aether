package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.entity.monster.AbstractWhirlwind;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import javax.annotation.Nonnull;

public class WhirlwindRenderer extends EntityRenderer<AbstractWhirlwind> {
    public WhirlwindRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull AbstractWhirlwind whirlwind) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
