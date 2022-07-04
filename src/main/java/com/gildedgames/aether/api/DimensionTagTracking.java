package com.gildedgames.aether.api;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.DimensionTypeHolderPacket;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.List;

public class DimensionTagTracking {
    private static final Table<ResourceKey<Level>, TagKey<DimensionType>, Boolean> tagTrackers = HashBasedTable.create();

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

    public static List<TagKey<DimensionType>> getTags(Level level) {
        return level.dimensionTypeRegistration().getTagKeys().filter(tagKey -> tagKey.location().getNamespace().equals(Aether.MODID)).toList();
    }
}
