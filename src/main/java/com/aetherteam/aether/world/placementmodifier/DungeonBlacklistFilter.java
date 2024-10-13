package com.aetherteam.aether.world.placementmodifier;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

/**
 * A {@link PlacementFilter} to prevent the feature from generating inside of a dungeon.
 */
public class DungeonBlacklistFilter extends PlacementFilter {
    public static final MapCodec<DungeonBlacklistFilter> CODEC = MapCodec.unit(DungeonBlacklistFilter::new);

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        if (!(context.getLevel() instanceof WorldGenRegion)) {
            return false;
        }
//        StructureManager structureManager = ((WorldGenRegionAccessor) context.getLevel()).aether$getStructureManager();
//        Registry<Structure> configuredStructureFeatureRegistry = context.getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE);
//        for (Holder<Structure> structure : configuredStructureFeatureRegistry.getOrCreateTag(AetherTags.Structures.DUNGEONS)) {
//            if (structureManager.getStructureAt(pos, structure.value()).isValid()) {
//                return false;
//            }
//        }
        return true;
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherPlacementModifiers.DUNGEON_BLACKLIST_FILTER.get();
    }
}
