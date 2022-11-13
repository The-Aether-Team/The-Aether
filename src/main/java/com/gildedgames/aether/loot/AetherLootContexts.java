package com.gildedgames.aether.loot;

import com.gildedgames.aether.mixin.mixins.accessor.LootContextParamSetsAccessor;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

public class AetherLootContexts {
    public static final LootContextParamSet STRIPPING = LootContextParamSetsAccessor.callRegister("aether:stripping", LootContextParamSet.Builder::build);
}
