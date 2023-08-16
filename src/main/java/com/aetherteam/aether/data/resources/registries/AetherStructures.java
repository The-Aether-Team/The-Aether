package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.builders.AetherStructureBuilders;
import com.aetherteam.aether.world.structure.BronzeDungeonStructure;
import com.aetherteam.aether.world.structure.GoldDungeonStructure;
import com.aetherteam.aether.world.structure.LargeAercloudStructure;
import com.aetherteam.aether.world.structure.SilverDungeonStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class AetherStructures {
    public static final ResourceKey<Structure> LARGE_AERCLOUD = createKey("large_aercloud");
    public static final ResourceKey<Structure> BRONZE_DUNGEON = createKey("bronze_dungeon");
    public static final ResourceKey<Structure> SILVER_DUNGEON = createKey("silver_dungeon");
    public static final ResourceKey<Structure> GOLD_DUNGEON = createKey("gold_dungeon");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        Map<MobCategory, StructureSpawnOverride> mobSpawnsBox = Arrays.stream(MobCategory.values())
                .collect(Collectors.toMap((category) -> category, (category) -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create())));

        Map<MobCategory, StructureSpawnOverride> mobSpawnsPiece = Arrays.stream(MobCategory.values())
                .collect(Collectors.toMap((category) -> category, (category) -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create())));


        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        context.register(LARGE_AERCLOUD, new LargeAercloudStructure(
                AetherStructureBuilders.structure(biomes.getOrThrow(AetherTags.Biomes.HAS_LARGE_AERCLOUD), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                BlockStateProvider.simple(AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)), 3));
        context.register(BRONZE_DUNGEON, new BronzeDungeonStructure(AetherStructureBuilders.structure(
                biomes.getOrThrow(AetherTags.Biomes.HAS_BRONZE_DUNGEON),
                mobSpawnsPiece,
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE),
                8, 32, 24));
        context.register(SILVER_DUNGEON, new SilverDungeonStructure(AetherStructureBuilders.structure(
                biomes.getOrThrow(AetherTags.Biomes.HAS_SILVER_DUNGEON),
                mobSpawnsBox,
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE),
                128, 2, 18, 35, 70));
        context.register(GOLD_DUNGEON, new GoldDungeonStructure(AetherStructureBuilders.structure(
                biomes.getOrThrow(AetherTags.Biomes.HAS_GOLD_DUNGEON),
                mobSpawnsBox,
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE),
                8, 20, 40, 60));
    }
}
