package com.aetherteam.aether.loot.conditions;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherLootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Aether.MODID);

    public static final RegistryObject<LootItemConditionType> CONFIG_ENABLED = LOOT_CONDITION_TYPES.register("config_enabled", () -> new LootItemConditionType(new ConfigEnabled.Serializer()));
}
