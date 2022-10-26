package com.gildedgames.aether.recipe.conditions;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.util.ConfigSerializationUtil;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ConfigCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(Aether.MODID, "config");
    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    public ConfigCondition(ForgeConfigSpec.ConfigValue<Boolean> config)
    {
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
        public void write(JsonObject object, ConfigCondition condition) {
            object.addProperty("config", ConfigSerializationUtil.serialize(condition.config));
        }

        @Override
        public ConfigCondition read(JsonObject object) {
            return new ConfigCondition(ConfigSerializationUtil.deserialize(GsonHelper.getAsString(object, "config")));
        }

        @Override
        public ResourceLocation getID() {
            return ConfigCondition.NAME;
        }
    }
}
