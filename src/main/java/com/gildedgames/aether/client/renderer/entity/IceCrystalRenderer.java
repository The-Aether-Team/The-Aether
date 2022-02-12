package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.CrystalModel;
import com.gildedgames.aether.common.entity.projectile.crystal.AbstractCrystal;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class IceCrystalRenderer<T extends AbstractCrystal> extends AbstractCrystalRenderer<T>
{
    private static final ResourceLocation ICE_CRYSTAL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/crystals/ice_ball.png");

    public IceCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalModel<>(context.bakeLayer(AetherModelLayers.ICE_CRYSTAL)));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull T crystal) {
        return ICE_CRYSTAL_TEXTURE;
    }
}
