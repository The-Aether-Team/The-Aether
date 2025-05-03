package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.PhygHaloLayer;
import com.aetherteam.aether.client.renderer.entity.layers.QuadrupedWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.HaloModel;
import com.aetherteam.aether.client.renderer.entity.model.QuadrupedWingsModel;
import com.aetherteam.aether.client.renderer.entity.state.PhygRenderState;
import com.aetherteam.aether.entity.passive.Phyg;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PhygRenderer extends AgeableMobRenderer<Phyg, PhygRenderState, PigModel> {
    private static final ResourceLocation PHYG_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/phyg/phyg.png");

    public PhygRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel(context.bakeLayer(AetherModelLayers.PHYG)), new PigModel(context.bakeLayer(AetherModelLayers.PHYG_BABY)), 0.7F);
        this.addLayer(new QuadrupedWingsLayer(this, new QuadrupedWingsModel<>(context.bakeLayer(AetherModelLayers.PHYG_WINGS)), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/phyg/phyg_wings.png")));
        this.addLayer(new SaddleLayer(this, new PigModel(context.bakeLayer(AetherModelLayers.PHYG_SADDLE)), new PigModel(context.bakeLayer(AetherModelLayers.PHYG_BABY_SADDLE)), ResourceLocation.withDefaultNamespace("textures/entity/pig/pig_saddle.png")));
        this.addLayer(new PhygHaloLayer(this, new HaloModel(context.bakeLayer(AetherModelLayers.PHYG_HALO))));
    }

    @Override
    public PhygRenderState createRenderState() {
        return new PhygRenderState();
    }

    @Override
    public void extractRenderState(Phyg phyg, PhygRenderState wingEntityRenderState, float p_361157_) {
        super.extractRenderState(phyg, wingEntityRenderState, p_361157_);
        wingEntityRenderState.wingAngle = (phyg.getWingFold() * Mth.sin(wingEntityRenderState.ageInTicks / 15.9F));
        wingEntityRenderState.wingHold = phyg.getWingFold();
        wingEntityRenderState.saddle = phyg.isSaddled();
    }

    @Override
    public ResourceLocation getTextureLocation(PhygRenderState phyg) {
        return PHYG_TEXTURE;
    }
}
