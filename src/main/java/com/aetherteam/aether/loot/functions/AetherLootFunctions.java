package com.aetherteam.aether.loot.functions;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class AetherLootFunctions {
    public static final LazyRegistrar<LootItemFunctionType> LOOT_FUNCTION_TYPES = LazyRegistrar.create(Registries.LOOT_FUNCTION_TYPE, Aether.MODID);

    public static final RegistryObject<LootItemFunctionType> DOUBLE_DROPS = LOOT_FUNCTION_TYPES.register("double_drops", () -> new LootItemFunctionType(new DoubleDrops.Serializer()));
    public static final RegistryObject<LootItemFunctionType> SPAWN_TNT = LOOT_FUNCTION_TYPES.register("spawn_tnt", () -> new LootItemFunctionType(new SpawnTNT.Serializer()));
    public static final RegistryObject<LootItemFunctionType> SPAWN_XP = LOOT_FUNCTION_TYPES.register("spawn_xp", () -> new LootItemFunctionType(new SpawnXP.Serializer()));
    public static final RegistryObject<LootItemFunctionType> WHIRLWIND_SPAWN_ENTITY = LOOT_FUNCTION_TYPES.register("whirlwind_spawn_entity", () -> new LootItemFunctionType(new WhirlwindSpawnEntity.Serializer()));
}
