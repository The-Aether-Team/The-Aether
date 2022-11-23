package com.gildedgames.aether.mixin.mixins.common.accessor;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

@Mixin(LootContextParamSets.class)
public interface LootContextParamSetsAccessor {
    @Invoker
    static LootContextParamSet callRegister(String registryName, Consumer<LootContextParamSet.Builder> builderConsumer) {
        throw new AssertionError();
    }
}