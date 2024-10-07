package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.Aether;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Aether.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<RemoveSeedsModifier>> REMOVE_SEEDS = GLOBAL_LOOT_MODIFIERS.register("remove_seeds", () -> RemoveSeedsModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<EnchantedGrassModifier>> ENCHANTED_GRASS = GLOBAL_LOOT_MODIFIERS.register("enchanted_grass", () -> EnchantedGrassModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<DoubleDropsModifier>> DOUBLE_DROPS = GLOBAL_LOOT_MODIFIERS.register("double_drops", () -> DoubleDropsModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<PigDropsModifier>> PIG_DROPS = GLOBAL_LOOT_MODIFIERS.register("pig_drops", () -> PigDropsModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<GlovesLootModifier>> GLOVES_LOOT = GLOBAL_LOOT_MODIFIERS.register("gloves_loot", () -> GlovesLootModifier.CODEC);
}
