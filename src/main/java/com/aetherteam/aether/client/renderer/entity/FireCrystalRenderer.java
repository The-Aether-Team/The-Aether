package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.CrystalModel;
import com.aetherteam.aether.client.renderer.entity.state.CrystalRenderState;
import com.aetherteam.aether.entity.projectile.crystal.FireCrystal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireCrystalRenderer extends AbstractCrystalRenderer<FireCrystal, CrystalRenderState> {
    private static final ResourceLocation FIRE_CRYSTAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/projectile/crystals/fire_ball.png");

    public FireCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalModel<>(context.bakeLayer(AetherModelLayers.CLOUD_CRYSTAL)));
    }

    @Override
    public CrystalRenderState createRenderState() {
        return new CrystalRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalRenderState crystal) {
        return FIRE_CRYSTAL_TEXTURE;
    }
}
