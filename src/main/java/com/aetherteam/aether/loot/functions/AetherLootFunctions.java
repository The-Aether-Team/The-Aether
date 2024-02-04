package com.aetherteam.aether.loot.functions;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherLootFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Aether.MODID);

    public static final Supplier<LootItemFunctionType> DOUBLE_DROPS = LOOT_FUNCTION_TYPES.register("double_drops", () -> new LootItemFunctionType(new DoubleDrops.Serializer()));
    public static final Supplier<LootItemFunctionType> SPAWN_TNT = LOOT_FUNCTION_TYPES.register("spawn_tnt", () -> new LootItemFunctionType(new SpawnTNT.Serializer()));
    public static final Supplier<LootItemFunctionType> SPAWN_XP = LOOT_FUNCTION_TYPES.register("spawn_xp", () -> new LootItemFunctionType(new SpawnXP.Serializer()));
    public static final Supplier<LootItemFunctionType> WHIRLWIND_SPAWN_ENTITY = LOOT_FUNCTION_TYPES.register("whirlwind_spawn_entity", () -> new LootItemFunctionType(new WhirlwindSpawnEntity.Serializer()));
}
