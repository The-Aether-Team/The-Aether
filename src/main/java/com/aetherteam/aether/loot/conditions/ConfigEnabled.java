package com.aetherteam.aether.loot.conditions;

import com.aetherteam.aether.data.ConfigSerializationUtil;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Checks if a config value is true or false for a loot table.
 */
public class ConfigEnabled implements LootItemCondition {
    private final ModConfigSpec.ConfigValue<Boolean> config;

    public ConfigEnabled(ModConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    public LootItemConditionType getType() {
        return AetherLootConditions.CONFIG_ENABLED.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return this.config.get();
    }

    public static LootItemCondition.Builder isEnabled(ModConfigSpec.ConfigValue<Boolean> config) {
        return () -> new ConfigEnabled(config);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ConfigEnabled> {
        @Override
        public void serialize(JsonObject json, ConfigEnabled instance, JsonSerializationContext context) {
            json.addProperty("config", ConfigSerializationUtil.serialize(instance.config));
        }

        @Override
        public ConfigEnabled deserialize(JsonObject json, JsonDeserializationContext context) {
            return new ConfigEnabled(ConfigSerializationUtil.deserialize(GsonHelper.getAsString(json, "config")));
        }
    }
}
