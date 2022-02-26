package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.loot.modifiers.RemoveSeedsModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Aether.MODID);

    public static final RegistryObject<RemoveSeedsModifier.Serializer> REMOVE_SEEDS = GLOBAL_LOOT_MODIFIERS.register("remove_seeds", RemoveSeedsModifier.Serializer::new);
}
