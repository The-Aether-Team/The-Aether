package com.gildedgames.aether.data.resources;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.data.resources.builders.AetherStructureBuilders;
import com.gildedgames.aether.world.structure.LargeAercloudStructure;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.HashMap;
import java.util.Map;

public class AetherStructures {
    public static final Map<ResourceLocation, Structure> STRUCTURES = new HashMap<>();

    public static final ResourceKey<Structure> LARGE_AERCLOUD = register("large_aercloud", new LargeAercloudStructure(AetherStructureBuilders.structure(AetherTags.Biomes.HAS_LARGE_AERCLOUD, GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));

    public static ResourceKey<Structure> register(String name, Structure structure) {
        ResourceLocation location = new ResourceLocation(Aether.MODID, name);
        STRUCTURES.putIfAbsent(location, structure);
        return ResourceKey.create(Registry.STRUCTURE_REGISTRY, location);
    }
}
