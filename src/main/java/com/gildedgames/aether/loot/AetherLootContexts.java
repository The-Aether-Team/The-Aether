package com.gildedgames.aether.loot;

import com.gildedgames.aether.mixin.mixins.accessor.LootContextParamSetsAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.function.Consumer;

public class AetherLootContexts {
    public static final LootContextParamSet STRIPPING = LootContextParamSetsAccessor.register("aether:stripping", LootContextParamSet.Builder::build);
}
