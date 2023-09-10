package com.aetherteam.aether.world.placementmodifier;

import com.aetherteam.aether.data.ConfigSerializationUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * A {@link PlacementFilter} to prevent the feature from generating when the specified config condition is set to false.
 */
public class ConfigFilter extends PlacementFilter {
    public static final Codec<ConfigFilter> CODEC = Codec.STRING.comapFlatMap(ConfigFilter::buildDeserialization, configFilter -> ConfigSerializationUtil.serialize(configFilter.config));

    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    /**
     * @param config The config value for the filter to use.
     */
    public ConfigFilter(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return this.config.get();
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherPlacementModifiers.CONFIG_FILTER;
    }

    private static DataResult<ConfigFilter> buildDeserialization(String configId) {
        ForgeConfigSpec.ConfigValue<?> configEntry = ConfigSerializationUtil.deserialize(configId);
        if (configEntry instanceof ForgeConfigSpec.BooleanValue booleanConfigEntry) {
            return DataResult.success(new ConfigFilter(booleanConfigEntry));
        }
        return DataResult.error("Config entry " + configId + " does not provide a boolean! Must be boolean (true/false), to be valid for ConfigFilter.");
    }
}
