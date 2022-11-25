package com.gildedgames.aether.world.placementmodifier;

import com.gildedgames.aether.data.resources.AetherStructures;
import com.gildedgames.aether.mixin.mixins.common.WorldGenRegionAccessor;
import com.gildedgames.aether.world.structure.AetherStructureTypes;
import com.gildedgames.aether.world.structure.GoldDungeonStructure;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.Structure;

public class DungeonBlacklistFilter extends PlacementFilter {

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        if (!(context.getLevel() instanceof WorldGenRegion)) {
            return false;
        }

        Registry<Structure> configuredStructureFeatureRegistry = context.getLevel().registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
        StructureManager structureManager = ((WorldGenRegionAccessor)context.getLevel()).getStructureManager();
    }

    @Override
    public PlacementModifierType<?> type() {
        return null;
    }
}
