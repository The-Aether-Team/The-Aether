package com.aetherteam.aether.loot.functions;

import com.aetherteam.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherLootFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registry.LOOT_FUNCTION_REGISTRY, Aether.MODID);

    public static final RegistryObject<LootItemFunctionType> DOUBLE_DROPS = LOOT_FUNCTION_TYPES.register("double_drops", () -> new LootItemFunctionType(new DoubleDrops.Serializer()));
    public static final RegistryObject<LootItemFunctionType> SPAWN_TNT = LOOT_FUNCTION_TYPES.register("spawn_tnt", () -> new LootItemFunctionType(new SpawnTNT.Serializer()));
    public static final RegistryObject<LootItemFunctionType> SPAWN_XP = LOOT_FUNCTION_TYPES.register("spawn_xp", () -> new LootItemFunctionType(new SpawnXP.Serializer()));
    public static final RegistryObject<LootItemFunctionType> WHIRLWIND_SPAWN_ENTITY = LOOT_FUNCTION_TYPES.register("whirlwind_spawn_entity", () -> new LootItemFunctionType(new WhirlwindSpawnEntity.Serializer()));
}
