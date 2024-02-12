package com.aetherteam.aether.loot.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * Checks if a config value is true or false for a loot table.
 */
public class ConfigEnabled implements LootItemCondition {
    public static final Codec<ConfigEnabled> CODEC = RecordCodecBuilder.create((builder) -> builder.group(Codec.BOOL.fieldOf("config").forGetter(instance -> instance.config)).apply(builder, ConfigEnabled::new));
    private final boolean config;

    public ConfigEnabled(boolean config) {
        this.config = config;
    }

    @Override
    public LootItemConditionType getType() {
        return AetherLootConditions.CONFIG_ENABLED.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return this.config;
    }

    public static LootItemCondition.Builder isEnabled(boolean config) {
        return () -> new ConfigEnabled(config);
    }
}
