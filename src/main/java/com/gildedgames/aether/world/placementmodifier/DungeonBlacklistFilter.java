package com.gildedgames.aether.world.placementmodifier;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.mixin.mixins.common.accessor.WorldGenRegionAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.Structure;

public class DungeonBlacklistFilter extends PlacementFilter {
    public static final Codec<DungeonBlacklistFilter> CODEC = Codec.unit(DungeonBlacklistFilter::new);

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        if (!(context.getLevel() instanceof WorldGenRegion)) {
            return false;
        }

        Registry<Structure> configuredStructureFeatureRegistry = context.getLevel().registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
        StructureManager structureManager = ((WorldGenRegionAccessor)context.getLevel()).getStructureManager();

        for (Holder<Structure> structure : configuredStructureFeatureRegistry.getOrCreateTag(AetherTags.Structures.DUNGEONS)) {
            if (structureManager.getStructureAt(pos, structure.value()).isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherPlacementModifiers.DUNGEON_BLACKLIST_FILTER;
    }
}
