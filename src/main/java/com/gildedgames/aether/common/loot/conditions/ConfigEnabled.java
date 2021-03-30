package com.gildedgames.aether.common.loot.conditions;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.core.AetherConfig;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class ConfigEnabled implements ILootCondition
{
    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    public ConfigEnabled(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    public LootConditionType getType() {
        return AetherLoot.CONFIG_ENABLED;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return this.config.get();
    }

    public static ILootCondition.IBuilder isEnabled(ForgeConfigSpec.ConfigValue<Boolean> config) {
        return () -> new ConfigEnabled(config);
    }

    public static class Serializer implements ILootSerializer<ConfigEnabled>
    {
        public void serialize(JsonObject object, ConfigEnabled condition, JsonSerializationContext context) {
            object.addProperty("config", condition.config.getPath().toString());
        }

        public ConfigEnabled deserialize(JsonObject object, JsonDeserializationContext context) {
            List<String> path = Arrays.asList(JSONUtils.getAsString(object, "config").replace("[", "").replace("]", "").split(", "));
            return new ConfigEnabled(AetherConfig.COMMON_SPEC.getValues().get(path));
        }
    }
}
