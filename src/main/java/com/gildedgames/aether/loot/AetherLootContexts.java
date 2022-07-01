package com.gildedgames.aether.loot;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class AetherLootContexts {
    public static final LootContextParamSet STRIPPING = LootContextParamSets.register("aether:stripping", (p_237455_0_) ->
            p_237455_0_.required(LootContextParams.BLOCK_STATE).required(LootContextParams.ORIGIN).required(LootContextParams.TOOL));
}
