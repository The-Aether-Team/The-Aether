package com.gildedgames.aether.data.resources.builders;

import com.gildedgames.aether.data.generators.AetherDataGenerators;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;

public class AetherStructureBuilders {
    public static Structure.StructureSettings structure(TagKey<Biome> biomeTagKey, Map<MobCategory, StructureSpawnOverride> mobs, GenerationStep.Decoration step, TerrainAdjustment adjustment) {
        return new Structure.StructureSettings(biomes(biomeTagKey), mobs, step, adjustment);
    }

    public static Structure.StructureSettings structure(TagKey<Biome> biomeTagKey, GenerationStep.Decoration step, TerrainAdjustment adjustment) {
        return structure(biomeTagKey, Map.of(), step, adjustment);
    }

    public static HolderSet<Biome> biomes(TagKey<Biome> biomeTagKey) {
        return AetherDataGenerators.DATA_REGISTRY.registryOrThrow(Registry.BIOME_REGISTRY).getOrCreateTag(biomeTagKey);
    }
}
