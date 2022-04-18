package com.gildedgames.aether.common.world.gen.placement;

import com.gildedgames.aether.core.util.ConfigSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * A PlacementFilter to prevent the feature from generating when the specified config condition is set to false.
 */
public class ConfigFilter extends PlacementFilter {

    public static final Codec<ConfigFilter> CODEC = Codec.STRING.xmap(configID -> new ConfigFilter(ConfigSerializer.deserialize(configID)), configFilter -> {
        try {
            return ConfigSerializer.serialize(configFilter.config);
        } catch (NullPointerException e) {
            throw new JsonSyntaxException("Error loading placed feature! Maybe the config key is incorrect?");
        }
    });

    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    /**
     * @param config The config value for the filter to use."
     */
    public ConfigFilter(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    protected boolean shouldPlace(@Nonnull PlacementContext context, @Nonnull Random random, @Nonnull BlockPos pos) {
        return this.config.get();
    }

    @Override
    @Nonnull
    public PlacementModifierType<?> type() {
        return PlacementModifiers.CONFIG_FILTER;
    }
}
