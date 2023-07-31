package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class WhirlwindRenderer extends EntityRenderer<AbstractWhirlwind> {
    public WhirlwindRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractWhirlwind whirlwind) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
