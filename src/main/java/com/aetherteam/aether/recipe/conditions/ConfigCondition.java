package com.aetherteam.aether.recipe.conditions;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.util.ConfigSerializationUtil;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

/**
 * Checks if a config value is true or false for a recipe.
 */
public class ConfigCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(Aether.MODID, "config");
    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    public ConfigCondition(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return this.config.get();
    }

    @Override
    public String toString() {
        return "config(\"" + ConfigSerializationUtil.serialize(this.config) + "\")";
    }

    public static class Serializer implements IConditionSerializer<ConfigCondition> {
        public static final ConfigCondition.Serializer INSTANCE = new ConfigCondition.Serializer();

        @Override
        public void write(JsonObject json, ConfigCondition condition) {
            json.addProperty("config", ConfigSerializationUtil.serialize(condition.config));
        }

        @Override
        public ConfigCondition read(JsonObject json) {
            return new ConfigCondition(ConfigSerializationUtil.deserialize(GsonHelper.getAsString(json, "config")));
        }

        @Override
        public ResourceLocation getID() {
            return ConfigCondition.NAME;
        }
    }
}
