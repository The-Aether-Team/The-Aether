package com.gildedgames.aether.data.resources.registries;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.data.resources.builders.AetherStructureBuilders;
import com.gildedgames.aether.world.structure.BronzeDungeonStructure;
import com.gildedgames.aether.world.structure.GoldDungeonStructure;
import com.gildedgames.aether.world.structure.LargeAercloudStructure;
import com.gildedgames.aether.world.structure.SilverDungeonStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

public class AetherStructures {
    public static final ResourceKey<Structure> LARGE_AERCLOUD = createKey("large_aercloud");
    public static final ResourceKey<Structure> BRONZE_DUNGEON = createKey("bronze_dungeon");
    public static final ResourceKey<Structure> SILVER_DUNGEON = createKey("silver_dungeon");
    public static final ResourceKey<Structure> GOLD_DUNGEON = createKey("gold_dungeon");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        context.register(LARGE_AERCLOUD, new LargeAercloudStructure(
                AetherStructureBuilders.structure(biomes.getOrThrow(AetherTags.Biomes.HAS_LARGE_AERCLOUD), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                BlockStateProvider.simple(AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)), 3));
        context.register(BRONZE_DUNGEON, new BronzeDungeonStructure(AetherStructureBuilders.structure(
                biomes.getOrThrow(AetherTags.Biomes.HAS_BRONZE_DUNGEON),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.BURY),
                8));
        context.register(SILVER_DUNGEON, new SilverDungeonStructure(AetherStructureBuilders.structure(
                biomes.getOrThrow(AetherTags.Biomes.HAS_SILVER_DUNGEON),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE)));
        context.register(GOLD_DUNGEON, new GoldDungeonStructure(AetherStructureBuilders.structure(
                biomes.getOrThrow(AetherTags.Biomes.HAS_GOLD_DUNGEON),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE)));
    }
}
