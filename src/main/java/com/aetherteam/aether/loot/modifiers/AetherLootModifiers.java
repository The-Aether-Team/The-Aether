package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.Aether;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AetherLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Aether.MODID);

    public static final Supplier<Codec<RemoveSeedsModifier>> REMOVE_SEEDS = GLOBAL_LOOT_MODIFIERS.register("remove_seeds", () -> RemoveSeedsModifier.CODEC);
    public static final Supplier<Codec<EnchantedGrassModifier>> ENCHANTED_GRASS = GLOBAL_LOOT_MODIFIERS.register("enchanted_grass", () -> EnchantedGrassModifier.CODEC);
    public static final Supplier<Codec<DoubleDropsModifier>> DOUBLE_DROPS = GLOBAL_LOOT_MODIFIERS.register("double_drops", () -> DoubleDropsModifier.CODEC);
    public static final Supplier<Codec<PigDropsModifier>> PIG_DROPS = GLOBAL_LOOT_MODIFIERS.register("pig_drops", () -> PigDropsModifier.CODEC);
    public static final Supplier<Codec<GlovesLootModifier>> GLOVES_LOOT = GLOBAL_LOOT_MODIFIERS.register("gloves_loot", () -> GlovesLootModifier.CODEC);
}
