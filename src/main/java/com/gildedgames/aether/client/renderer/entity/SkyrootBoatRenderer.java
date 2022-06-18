package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import javax.annotation.Nonnull;

public class SkyrootBoatRenderer extends BoatRenderer {
    private static final ResourceLocation SKYROOT_BOAT = new ResourceLocation(Aether.MODID, "textures/entity/miscellaneous/boat/skyroot.png");
    private final Pair<ResourceLocation, BoatModel> skyrootBoatResource;

    public SkyrootBoatRenderer(EntityRendererProvider.Context context, boolean chest) {
        super(context, chest);
        this.skyrootBoatResource = Pair.of(SKYROOT_BOAT, new BoatModel(context.bakeLayer(AetherModelLayers.SKYROOT_BOAT), chest));
    }

    @Nonnull
    @Override
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(@Nonnull Boat boat) {
        return this.skyrootBoatResource;
    }
}
