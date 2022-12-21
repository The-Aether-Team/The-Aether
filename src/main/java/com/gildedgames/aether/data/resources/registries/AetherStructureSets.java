package com.gildedgames.aether.data.resources.registries;

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
    public static final ResourceKey<StructureSet> SILVER_DUNGEON = createKey("silver_dungeon");
    public static final ResourceKey<StructureSet> GOLD_DUNGEON = createKey("gold_dungeon");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(LARGE_AERCLOUD, new StructureSet(structures.getOrThrow(AetherStructures.LARGE_AERCLOUD), new RandomSpreadStructurePlacement(4, 2, RandomSpreadType.LINEAR, 15536586)));
        context.register(BRONZE_DUNGEON, new StructureSet(structures.getOrThrow(AetherStructures.BRONZE_DUNGEON), new RandomSpreadStructurePlacement(12, 10, RandomSpreadType.LINEAR, 32146754)));
        context.register(SILVER_DUNGEON, new StructureSet(structures.getOrThrow(AetherStructures.SILVER_DUNGEON), new RandomSpreadStructurePlacement(16, 6, RandomSpreadType.LINEAR, 4325806)));
        context.register(GOLD_DUNGEON, new StructureSet(structures.getOrThrow(AetherStructures.GOLD_DUNGEON), new RandomSpreadStructurePlacement(20, 6, RandomSpreadType.LINEAR, 15436785)));
    }
}
