package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.CrystalModel;
import com.gildedgames.aether.common.entity.projectile.crystal.AbstractCrystalEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IceCrystalRenderer<T extends AbstractCrystalEntity> extends AbstractCrystalRenderer<T>
{
    private static final ResourceLocation ICE_CRYSTAL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/crystals/ice_ball.png");

    public IceCrystalRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new CrystalModel<>(renderer.bakeLayer(AetherModelLayers.CLOUD_CRYSTAL)));
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return ICE_CRYSTAL_TEXTURE;
    }
}
