package com.aetherteam.aether.world.placementmodifier;

import com.aetherteam.aether.api.ConfigSerializationUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;

/**
 * A PlacementFilter to prevent the feature from generating when the specified config condition is set to false.
 */
public class ConfigFilter extends PlacementFilter {
    public static final Codec<ConfigFilter> CODEC = Codec.STRING.comapFlatMap(ConfigFilter::build, configFilter -> ConfigSerializationUtil.serialize(configFilter.config));

    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    /**
     * @param config The config value for the filter to use."
     */
    public ConfigFilter(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    protected boolean shouldPlace(@Nonnull PlacementContext context, @Nonnull RandomSource random, @Nonnull BlockPos pos) {
        return this.config.get();
    }

    @Override
    @Nonnull
    public PlacementModifierType<?> type() {
        return AetherPlacementModifiers.CONFIG_FILTER;
    }

    private static DataResult<ConfigFilter> build(String configID) {
        @SuppressWarnings("rawtypes") // The type of value coming from the config ID cannot be trusted
        ForgeConfigSpec.ConfigValue unsafeConfigEntry = ConfigSerializationUtil.deserialize(configID);
        // Java erases generics after compile, meaning the code executed during ConfigSerializer.deserialize will not error,
        // even if the generic type is not Boolean. Otherwise, it will error and crash, while executing `this.config.get()` during Worldgen.

        if (unsafeConfigEntry instanceof ForgeConfigSpec.BooleanValue booleanConfigEntry)
            return DataResult.success(new ConfigFilter(booleanConfigEntry));

        return DataResult.error(() -> "Config entry " + configID + " does not provide a boolean! Must be boolean (true/false), to be valid for ConfigFilter.");
    }
}
