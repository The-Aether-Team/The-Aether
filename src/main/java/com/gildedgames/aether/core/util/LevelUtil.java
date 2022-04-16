package com.gildedgames.aether.core.util;

import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.DimensionTypeHolderPacket;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.List;

public class LevelUtil {
    private static final Table<ResourceKey<Level>, TagKey<DimensionType>, Boolean> tagTrackers = HashBasedTable.create();
    private static final List<TagKey<DimensionType>> tags = List.of(AetherTags.Dimensions.HOSTILE_PARADISE, AetherTags.Dimensions.ULTRACOLD, AetherTags.Dimensions.ETERNAL_DAY, AetherTags.Dimensions.NO_WHEAT_SEEDS, AetherTags.Dimensions.FALL_TO_OVERWORLD, AetherTags.Dimensions.DISPLAY_TRAVEL_TEXT, AetherTags.Dimensions.AETHER_MUSIC);

    public static ResourceKey<Level> destinationDimension() { //Default: aether:the_aether
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AetherConfig.COMMON.portalDestinationDimensionID.get()));
    }

    public static ResourceKey<Level> returnDimension() { //Default: minecraft:overworld
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AetherConfig.COMMON.portalReturnDimensionID.get()));
    }

    public static void addTracker(Level level, TagKey<DimensionType> tagKey) {
        if (!level.isClientSide()) {
            ResourceKey<Level> dimensionKey = level.dimension();
            if (!tagTrackers.contains(dimensionKey, tagKey)) {
                boolean value = level.dimensionTypeRegistration().is(tagKey);
                tagTrackers.put(dimensionKey, tagKey, value);
            }
        }
    }

    public static void syncTrackerFromServer(Level level, TagKey<DimensionType> tagKey) {
        if (!level.isClientSide()) {
            ResourceKey<Level> dimensionKey = level.dimension();
            boolean value = level.dimensionTypeRegistration().is(tagKey);
            AetherPacketHandler.sendToAll(new DimensionTypeHolderPacket(dimensionKey, tagKey, value));
        }
    }

    public static Table<ResourceKey<Level>, TagKey<DimensionType>, Boolean> getTagTrackers() {
        return tagTrackers;
    }

    public static boolean inTag(Level level, TagKey<DimensionType> tagKey) {
        ResourceKey<Level> dimensionKey = level.dimension();
        return Boolean.TRUE.equals(tagTrackers.get(dimensionKey, tagKey));
    }

    public static List<TagKey<DimensionType>> getTags() {
        return tags;
    }
}
