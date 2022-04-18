package com.gildedgames.aether.common.world.gen.placement;

import com.gildedgames.aether.core.AetherConfig;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
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

    public static final Codec<ConfigFilter> CODEC = Codec.STRING.xmap(ConfigFilter::new, configFilter -> {
        try {
            return String.join(", ", configFilter.config.getPath());
        } catch (NullPointerException e) {
            throw new JsonSyntaxException("Error loading placed feature! Maybe the config key is incorrect?");
        }
    });

    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    /**
     * @param configID The definition for the config value to use. Format: "[(Config group), (Config definition)]"
     */
    public ConfigFilter(String configID) {
        List<String> path = Arrays.asList(configID.replace("[", "").replace("]", "").split(", "));
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
