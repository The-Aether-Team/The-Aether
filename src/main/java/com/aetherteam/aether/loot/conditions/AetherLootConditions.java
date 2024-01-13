package com.aetherteam.aether.loot.conditions;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class AetherLootConditions {
    public static final LazyRegistrar<LootItemConditionType> LOOT_CONDITION_TYPES = LazyRegistrar.create(Registries.LOOT_CONDITION_TYPE, Aether.MODID);

    public static final RegistryObject<LootItemConditionType> CONFIG_ENABLED = LOOT_CONDITION_TYPES.register("config_enabled", () -> new LootItemConditionType(new ConfigEnabled.Serializer()));
}
