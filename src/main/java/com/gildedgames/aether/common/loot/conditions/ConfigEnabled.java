package com.gildedgames.aether.common.loot.conditions;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.util.ConfigSerializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ConfigEnabled implements LootItemCondition {
    private final ForgeConfigSpec.ConfigValue<Boolean> config;

    public ConfigEnabled(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    @Nonnull
    public LootItemConditionType getType() {
        return AetherLoot.CONFIG_ENABLED;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return this.config.get();
    }

    public static LootItemCondition.Builder isEnabled(ForgeConfigSpec.ConfigValue<Boolean> config) {
        return () -> new ConfigEnabled(config);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ConfigEnabled> {
        public void serialize(JsonObject object, ConfigEnabled condition, @Nonnull JsonSerializationContext context) {
            object.addProperty("config", ConfigSerializer.serialize(condition.config));
        }

        @Nonnull
        public ConfigEnabled deserialize(@Nonnull JsonObject object, @Nonnull JsonDeserializationContext context) {
            return new ConfigEnabled(ConfigSerializer.deserialize(GsonHelper.getAsString(object, "config")));
        }
    }
}
