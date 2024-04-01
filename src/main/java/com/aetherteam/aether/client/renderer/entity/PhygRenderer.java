package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.PhygHaloLayer;
import com.aetherteam.aether.client.renderer.entity.layers.QuadrupedWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.HaloModel;
import com.aetherteam.aether.client.renderer.entity.model.QuadrupedWingsModel;
import com.aetherteam.aether.entity.passive.Phyg;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class PhygRenderer extends MobRenderer<Phyg, PigModel<Phyg>> {
    private static final ResourceLocation PHYG_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg.png");

    public PhygRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(AetherModelLayers.PHYG)), 0.7F);
        this.addLayer(new QuadrupedWingsLayer<>(this, new QuadrupedWingsModel<>(context.bakeLayer(AetherModelLayers.PHYG_WINGS)), new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg_wings.png")));
        this.addLayer(new SaddleLayer<>(this, new PigModel<>(context.bakeLayer(AetherModelLayers.PHYG_SADDLE)), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
        this.addLayer(new PhygHaloLayer(this, new HaloModel<>(context.bakeLayer(AetherModelLayers.PHYG_HALO))));
    }

    @Override
    public ResourceLocation getTextureLocation(Phyg phyg) {
        return PHYG_TEXTURE;
    }
}
