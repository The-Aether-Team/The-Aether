package com.gildedgames.aether.common.world.gen.placement;

import com.gildedgames.aether.core.AetherConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A PlacementFilter to prevent the feature from generating when the specified config condition is set to false.
 */
public class ConfigFilter extends PlacementFilter {

    public static final Codec<ConfigFilter> CODEC = Codec.STRING.xmap(ConfigFilter::new, configFilter -> String.join(", ", configFilter.config.getPath()));

    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    public ConfigFilter(String config) {
        List<String> path = Arrays.asList(config.replace("[", "").replace("]", "").split(", "));
        this.config = AetherConfig.COMMON_SPEC.getValues().get(path);
    }

    @Override
    protected boolean shouldPlace(@Nonnull PlacementContext context, @Nonnull Random random, @Nonnull BlockPos pos) {
        return config.get();
    }

    @Override
    @Nonnull
    public PlacementModifierType<?> type() {
        return PlacementModifiers.CONFIG_FILTER;
    }
}
