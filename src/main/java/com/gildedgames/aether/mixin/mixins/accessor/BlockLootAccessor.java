package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockLoot.class)
public interface BlockLootAccessor {
    @Accessor("HAS_SILK_TOUCH")
    static LootItemCondition.Builder hasSilkTouch() {
        throw new AssertionError();
    }

    @Accessor("HAS_SHEARS_OR_SILK_TOUCH")
    static LootItemCondition.Builder hasShearsOrSilkTouch() {
        throw new AssertionError();
    }

    @Accessor("NORMAL_LEAVES_SAPLING_CHANCES")
    static float[] getNormalLeavesSaplingChances() {
        throw new AssertionError();
    }
}