package com.gildedgames.aether.core.util;

import com.gildedgames.aether.core.AetherConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class ConfigSerializer {
    public static String serialize(ForgeConfigSpec.ConfigValue<Boolean> config) {
        return config.getPath().toString();
    }

    public static ForgeConfigSpec.ConfigValue<Boolean> deserialize(String string) {
        List<String> path = Arrays.asList(string.replace("[", "").replace("]", "").split(", "));
        return AetherConfig.COMMON_SPEC.getValues().get(path);
    }
}
