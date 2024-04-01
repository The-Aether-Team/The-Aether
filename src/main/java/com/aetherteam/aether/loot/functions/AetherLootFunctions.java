package com.aetherteam.aether.loot.functions;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherLootFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Aether.MODID);

    public static final DeferredHolder<LootItemFunctionType, LootItemFunctionType> DOUBLE_DROPS = LOOT_FUNCTION_TYPES.register("double_drops", () -> new LootItemFunctionType(DoubleDrops.CODEC));
    public static final DeferredHolder<LootItemFunctionType, LootItemFunctionType> SPAWN_TNT = LOOT_FUNCTION_TYPES.register("spawn_tnt", () -> new LootItemFunctionType(SpawnTNT.CODEC));
    public static final DeferredHolder<LootItemFunctionType, LootItemFunctionType> SPAWN_XP = LOOT_FUNCTION_TYPES.register("spawn_xp", () -> new LootItemFunctionType(SpawnXP.CODEC));
    public static final DeferredHolder<LootItemFunctionType, LootItemFunctionType> WHIRLWIND_SPAWN_ENTITY = LOOT_FUNCTION_TYPES.register("whirlwind_spawn_entity", () -> new LootItemFunctionType(WhirlwindSpawnEntity.CODEC));
}
