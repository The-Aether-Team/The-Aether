package com.aetherteam.aether.loot.conditions;

import com.aetherteam.aether.data.ConfigSerializationUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Checks if a config value is true or false for a loot table.
 */
public class ConfigEnabled implements LootItemCondition {
    public static final Codec<ConfigEnabled> CODEC = RecordCodecBuilder.create((builder) -> builder.group(Codec.STRING.fieldOf("config").forGetter(instance -> ConfigSerializationUtil.serialize(instance.config))).apply(builder, (e) -> new ConfigEnabled(ConfigSerializationUtil.deserialize(e))));
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
}
