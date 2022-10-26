package com.gildedgames.aether.loot;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class AetherLootContexts {
    public static final LootContextParamSet STRIPPING = LootContextParamSets.register("aether:stripping", LootContextParamSet.Builder::build);
}
