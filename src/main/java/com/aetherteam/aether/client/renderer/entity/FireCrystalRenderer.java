package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.CrystalModel;
import com.aetherteam.aether.entity.projectile.crystal.AbstractCrystal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireCrystalRenderer<T extends AbstractCrystal> extends AbstractCrystalRenderer<T> {
    private static final ResourceLocation FIRE_CRYSTAL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/crystals/fire_ball.png");

    public FireCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalModel<>(context.bakeLayer(AetherModelLayers.CLOUD_CRYSTAL)));
    }

   
    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return FIRE_CRYSTAL_TEXTURE;
    }
}
