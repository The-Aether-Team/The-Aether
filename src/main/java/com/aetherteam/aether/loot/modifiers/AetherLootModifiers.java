package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.fabric.loot.IGlobalLootModifier;
import com.aetherteam.nitrogen.fabric.loot.LootTableModificationAPI;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import com.mojang.serialization.MapCodec;

public class AetherLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(LootTableModificationAPI.GLOBAL_LOOT_MODIFIER_CODEC, Aether.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<RemoveSeedsModifier>> REMOVE_SEEDS = GLOBAL_LOOT_MODIFIERS.register("remove_seeds", () -> RemoveSeedsModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<EnchantedGrassModifier>> ENCHANTED_GRASS = GLOBAL_LOOT_MODIFIERS.register("enchanted_grass", () -> EnchantedGrassModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<DoubleDropsModifier>> DOUBLE_DROPS = GLOBAL_LOOT_MODIFIERS.register("double_drops", () -> DoubleDropsModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<PigDropsModifier>> PIG_DROPS = GLOBAL_LOOT_MODIFIERS.register("pig_drops", () -> PigDropsModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<GlovesLootModifier>> GLOVES_LOOT = GLOBAL_LOOT_MODIFIERS.register("gloves_loot", () -> GlovesLootModifier.CODEC);
}
