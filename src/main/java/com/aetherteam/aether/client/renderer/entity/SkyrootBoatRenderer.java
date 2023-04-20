package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class SkyrootBoatRenderer extends BoatRenderer {
    private static final ResourceLocation SKYROOT_BOAT = new ResourceLocation(Aether.MODID, "textures/entity/miscellaneous/boat/skyroot.png");
    private static final ResourceLocation SKYROOT_CHEST_BOAT = new ResourceLocation(Aether.MODID, "textures/entity/miscellaneous/chest_boat/skyroot.png");
    private final Pair<ResourceLocation, ListModel<Boat>> skyrootBoatResource;

    public SkyrootBoatRenderer(EntityRendererProvider.Context context, boolean chest) {
        super(context, chest);
        this.skyrootBoatResource = Pair.of(chest ? SKYROOT_CHEST_BOAT : SKYROOT_BOAT, chest ? new ChestBoatModel(context.bakeLayer(AetherModelLayers.SKYROOT_CHEST_BOAT)) : new BoatModel(context.bakeLayer(AetherModelLayers.SKYROOT_BOAT)));
    }

    @Nonnull
    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(@Nonnull Boat boat) {
        return this.skyrootBoatResource;
    }
}
