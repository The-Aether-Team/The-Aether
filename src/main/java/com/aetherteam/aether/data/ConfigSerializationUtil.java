package com.aetherteam.aether.data;

import com.aetherteam.aether.AetherConfig;
import com.google.gson.JsonSyntaxException;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public final class ConfigSerializationUtil {
    /**
     * Create a serializable string out of a config value's path.
     * @param config The {@link net.minecraftforge.common.ForgeConfigSpec.ConfigValue}<{@link Boolean}> to serialize from.
     * @return The serializable {@link String}.
     */
    public static String serialize(ForgeConfigSpec.ConfigValue<Boolean> config) {
        try {
            return config.getPath().toString();
        } catch (NullPointerException e) {
            throw new JsonSyntaxException("Error loading config entry from JSON! Maybe the config key is incorrect?");
        }
    }

    /**
     * Gets a config value out of a serialized string.
     * @param string The {@link String} to deserialize from.
     * @return The deserialized {@link net.minecraftforge.common.ForgeConfigSpec.ConfigValue}<{@link Boolean}>.
     */
    public static ForgeConfigSpec.ConfigValue<Boolean> deserialize(String string) {
        List<String> path = Arrays.asList(string.replace("[", "").replace("]", "").split(", "));
        ForgeConfigSpec.ConfigValue<Boolean> config = AetherConfig.SERVER_SPEC.getValues().get(path);
        if (config == null) {
            config = AetherConfig.COMMON_SPEC.getValues().get(path);
        }
        return config;
    }
}
