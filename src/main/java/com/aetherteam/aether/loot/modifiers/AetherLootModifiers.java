package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.Aether;
import com.mojang.serialization.Codec;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;

public class AetherLootModifiers {
    public static final LazyRegistrar<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = LazyRegistrar.create(PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS_KEY, Aether.MODID);

    public static final RegistryObject<Codec<RemoveSeedsModifier>> REMOVE_SEEDS = GLOBAL_LOOT_MODIFIERS.register("remove_seeds", () -> RemoveSeedsModifier.CODEC);
    public static final RegistryObject<Codec<EnchantedGrassModifier>> ENCHANTED_GRASS = GLOBAL_LOOT_MODIFIERS.register("enchanted_grass", () -> EnchantedGrassModifier.CODEC);
    public static final RegistryObject<Codec<DoubleDropsModifier>> DOUBLE_DROPS = GLOBAL_LOOT_MODIFIERS.register("double_drops", () -> DoubleDropsModifier.CODEC);
    public static final RegistryObject<Codec<PigDropsModifier>> PIG_DROPS = GLOBAL_LOOT_MODIFIERS.register("pig_drops", () -> PigDropsModifier.CODEC);
    public static final RegistryObject<Codec<GlovesLootModifier>> GLOVES_LOOT = GLOBAL_LOOT_MODIFIERS.register("gloves_loot", () -> GlovesLootModifier.CODEC);
}
