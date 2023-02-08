package com.gildedgames.aether.data.resources.registries;

import java.util.List;

import com.gildedgames.aether.Aether;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class AetherStructureSets {
    public static final ResourceKey<StructureSet> LARGE_AERCLOUD = createKey("large_aercloud");
    public static final ResourceKey<StructureSet> BRONZE_DUNGEON = createKey("bronze_dungeon");    
    public static final ResourceKey<StructureSet> SILVER_AND_GOLD_DUNGEONS = createKey("silver_and_gold_dungeons");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(LARGE_AERCLOUD, new StructureSet(structures.getOrThrow(AetherStructures.LARGE_AERCLOUD), new RandomSpreadStructurePlacement(8, 4, RandomSpreadType.LINEAR, 15536586)));
        context.register(BRONZE_DUNGEON, new StructureSet(structures.getOrThrow(AetherStructures.BRONZE_DUNGEON), new RandomSpreadStructurePlacement(6, 5, RandomSpreadType.LINEAR, 32146754)));
        context.register(SILVER_AND_GOLD_DUNGEONS, new StructureSet(List.of(
        		StructureSet.entry(structures.getOrThrow(AetherStructures.SILVER_DUNGEON), 3),
        		StructureSet.entry(structures.getOrThrow(AetherStructures.GOLD_DUNGEON), 1)),
        	new RandomSpreadStructurePlacement(24, 20, RandomSpreadType.LINEAR, 4325806)));
    }
}
