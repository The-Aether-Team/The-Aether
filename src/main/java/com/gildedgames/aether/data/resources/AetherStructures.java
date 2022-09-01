package com.gildedgames.aether.data.resources;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.data.generators.AetherDataGenerators;
import com.gildedgames.aether.data.resources.builders.AetherStructureBuilders;
import com.gildedgames.aether.world.structure.BronzeDungeonStructure;
import com.gildedgames.aether.world.structure.GoldDungeonStructure;
import com.gildedgames.aether.world.structure.LargeAercloudStructure;
import com.gildedgames.aether.world.structure.SilverDungeonStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.HashMap;
import java.util.Map;

public class AetherStructures {
    public static final Map<ResourceLocation, Structure> STRUCTURES = new HashMap<>();

    public static final ResourceKey<Structure> LARGE_AERCLOUD = register("large_aercloud", new LargeAercloudStructure(AetherStructureBuilders.structure(AetherTags.Biomes.HAS_LARGE_AERCLOUD, GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE), BlockStateProvider.simple(AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)), 3));

    public static final ResourceKey<Structure> BRONZE_DUNGEON = register("bronze_dungeon",
            new BronzeDungeonStructure(AetherStructureBuilders.structure(
                    AetherTags.Biomes.HAS_BRONZE_DUNGEON,
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.NONE)
            )
    );
    public static final ResourceKey<Structure> SILVER_DUNGEON = register("silver_dungeon",
            new SilverDungeonStructure(AetherStructureBuilders.structure(
                    AetherTags.Biomes.HAS_SILVER_DUNGEON,
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.NONE)
            )
    );
    public static final ResourceKey<Structure> GOLD_DUNGEON = register("gold_dungeon",
            new GoldDungeonStructure(AetherStructureBuilders.structure(
                    AetherTags.Biomes.HAS_GOLD_DUNGEON,
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.NONE)
            )
    );

    public static ResourceKey<Structure> register(String name, Structure structure) {
        ResourceLocation location = new ResourceLocation(Aether.MODID, name);
        STRUCTURES.putIfAbsent(location, structure);
        return ResourceKey.create(Registry.STRUCTURE_REGISTRY, location);
    }

    public static Holder<Structure> dataHolder(ResourceKey<Structure> resourceKey) {
        return AetherDataGenerators.DATA_REGISTRY.registryOrThrow(Registry.STRUCTURE_REGISTRY).getOrCreateHolderOrThrow(resourceKey);
    }
}
