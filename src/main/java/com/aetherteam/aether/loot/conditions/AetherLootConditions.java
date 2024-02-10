package com.aetherteam.aether.loot.conditions;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherLootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Aether.MODID);

    public static final Supplier<LootItemConditionType> CONFIG_ENABLED = LOOT_CONDITION_TYPES.register("config_enabled", () -> new LootItemConditionType(ConfigEnabled.CODEC));
}
