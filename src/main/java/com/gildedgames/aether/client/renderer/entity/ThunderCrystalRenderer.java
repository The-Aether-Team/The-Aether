package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.CrystalModel;
import com.gildedgames.aether.common.entity.projectile.crystal.ThunderCrystal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class ThunderCrystalRenderer extends AbstractCrystalRenderer<ThunderCrystal> {
    private static final ResourceLocation THUNDER_CRYSTAL_TEXTURE  = new ResourceLocation(Aether.MODID, "textures/entity/projectile/crystals/electric_ball.png");

    public ThunderCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalModel<>(context.bakeLayer(AetherModelLayers.THUNDER_CRYSTAL)));
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull ThunderCrystal pEntity) {
        return THUNDER_CRYSTAL_TEXTURE;
    }
}
