package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.CrystalModel;
import com.gildedgames.aether.entity.projectile.crystal.AbstractCrystal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class FireCrystalRenderer<T extends AbstractCrystal> extends AbstractCrystalRenderer<T> {
    private static final ResourceLocation FIRE_CRYSTAL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/crystals/fire_ball.png");

    public FireCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalModel<>(context.bakeLayer(AetherModelLayers.CLOUD_CRYSTAL)));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull T pEntity) {
        return FIRE_CRYSTAL_TEXTURE;
    }
}
