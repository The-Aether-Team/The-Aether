package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class BlueSwetRenderer extends SwetRenderer {
    private static final ResourceLocation BLUE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/swet/swet_blue.png");

    public BlueSwetRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Swet swet) {
        return BLUE_TEXTURE;
    }
}
