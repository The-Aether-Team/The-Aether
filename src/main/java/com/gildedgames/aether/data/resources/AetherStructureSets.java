package com.gildedgames.aether.data.resources;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.HashMap;
import java.util.Map;

public class AetherStructureSets {
    public static final Map<ResourceLocation, StructureSet> STRUCTURE_SETS = new HashMap<>();
//todo: better spacing and separation.
    public static final ResourceKey<StructureSet> LARGE_AERCLOUD = register("large_aercloud", new StructureSet(AetherStructures.dataHolder(AetherStructures.LARGE_AERCLOUD), new RandomSpreadStructurePlacement(5, 2, RandomSpreadType.LINEAR, 15536586)));

    public static ResourceKey<StructureSet> register(String name, StructureSet structure) {
        ResourceLocation location = new ResourceLocation(Aether.MODID, name);
        STRUCTURE_SETS.putIfAbsent(location, structure);
        return ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, location);
    }
}
