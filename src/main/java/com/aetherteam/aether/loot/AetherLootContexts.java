package com.aetherteam.aether.loot;

import com.aetherteam.aether.mixin.mixins.common.accessor.LootContextParamSetsAccessor;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class AetherLootContexts {
    public static final LootContextParamSet STRIPPING = LootContextParamSetsAccessor.callRegister("aether_stripping", (builder) -> builder.required(LootContextParams.TOOL).build());
}
