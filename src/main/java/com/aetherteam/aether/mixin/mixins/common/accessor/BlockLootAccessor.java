package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockLootSubProvider.class)
public interface BlockLootAccessor {
    @Accessor("NORMAL_LEAVES_SAPLING_CHANCES")
    static float[] aether$getNormalLeavesSaplingChances() {
        throw new AssertionError();
    }
}
