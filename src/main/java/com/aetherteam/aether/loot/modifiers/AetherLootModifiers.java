package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.Aether;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Aether.MODID);

    public static final RegistryObject<Codec<RemoveSeedsModifier>> REMOVE_SEEDS = GLOBAL_LOOT_MODIFIERS.register("remove_seeds", () -> RemoveSeedsModifier.CODEC);
    public static final RegistryObject<Codec<EnchantedGrassModifier>> ENCHANTED_GRASS = GLOBAL_LOOT_MODIFIERS.register("enchanted_grass", () -> EnchantedGrassModifier.CODEC);
    public static final RegistryObject<Codec<DoubleDropsModifier>> DOUBLE_DROPS = GLOBAL_LOOT_MODIFIERS.register("double_drops", () -> DoubleDropsModifier.CODEC);
    public static final RegistryObject<Codec<PigDropsModifier>> PIG_DROPS = GLOBAL_LOOT_MODIFIERS.register("pig_drops", () -> PigDropsModifier.CODEC);
}
