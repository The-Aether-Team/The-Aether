package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.CrystalModel;
import com.aetherteam.aether.entity.projectile.crystal.ThunderCrystal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ThunderCrystalRenderer extends AbstractCrystalRenderer<ThunderCrystal> {
    private static final ResourceLocation THUNDER_CRYSTAL_TEXTURE  = new ResourceLocation(Aether.MODID, "textures/entity/projectile/crystals/electric_ball.png");

    public ThunderCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalModel<>(context.bakeLayer(AetherModelLayers.THUNDER_CRYSTAL)));
    }

    @Override
   
    public ResourceLocation getTextureLocation(ThunderCrystal pEntity) {
        return THUNDER_CRYSTAL_TEXTURE;
    }
}
