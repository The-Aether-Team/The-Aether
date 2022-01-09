package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.gen.chunk.CelledSpaceGenerator;
import com.gildedgames.aether.common.world.gen.chunk.GriddedGenerator;
import com.gildedgames.aether.common.world.gen.placement.ElevationAdjustment;
import com.gildedgames.aether.common.world.gen.placement.ElevationFilter;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class AetherWorldComponents {
    public static final PlacementModifierType<ElevationAdjustment> ELEVATION_ADJUSTMENT = register(new ResourceLocation(Aether.MODID, "elevation_adjustment"), ElevationAdjustment.CODEC);
    public static final PlacementModifierType<ElevationFilter> ELEVATION_FILTER = register(new ResourceLocation(Aether.MODID, "elevation_filter"), ElevationFilter.CODEC);

    static {
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Aether.MODID, "gridded"), GriddedGenerator.CODEC);
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Aether.MODID, "cells"), CelledSpaceGenerator.CODEC);
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> register(ResourceLocation name, Codec<P> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> codec);
    }

    // You HAVE to call this. This class won't load otherwise unless properly addressed
    public static void init() {
    }
}
