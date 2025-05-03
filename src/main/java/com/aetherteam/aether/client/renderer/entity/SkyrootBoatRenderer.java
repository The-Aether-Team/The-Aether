package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SkyrootBoatRenderer extends BoatRenderer {
    public static final ResourceLocation SKYROOT_BOAT = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/miscellaneous/boat/skyroot.png");
    public static final ResourceLocation SKYROOT_CHEST_BOAT = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/miscellaneous/chest_boat/skyroot.png");
    public final ResourceLocation resourceLocation;

    public SkyrootBoatRenderer(EntityRendererProvider.Context context, ModelLayerLocation chest, ResourceLocation resourceLocation) {
        super(context, chest);
        this.resourceLocation = resourceLocation;
    }

    @Override
    protected RenderType renderType() {
        return this.model().renderType(this.resourceLocation);
    }
}
