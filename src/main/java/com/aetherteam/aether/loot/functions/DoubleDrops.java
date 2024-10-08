package com.aetherteam.aether.loot.functions;

import com.aetherteam.aether.item.tools.abilities.SkyrootTool;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class DoubleDrops extends LootItemConditionalFunction {
    public static final MapCodec<DoubleDrops> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance).apply(instance, DoubleDrops::new));

    protected DoubleDrops(List<LootItemCondition> conditions) {
        super(conditions);
    }

    /**
     * Doubles the dropped stack through {@link SkyrootTool#doubleDrops(ItemStack, ItemStack, BlockState)}.
     *
     * @param stack   The {@link ItemStack} for the loot pool.
     * @param context The {@link LootContext}.
     * @return The {@link ItemStack} for the loot pool.
     */
    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        Level level = context.getLevel();
        ItemStack toolStack = context.getParamOrNull(LootContextParams.TOOL);
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        if (toolStack.getItem() instanceof SkyrootTool skyrootTool) {
            return skyrootTool.doubleDrops(level, stack, toolStack, blockState);
        } else {
            return stack;
        }
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return LootItemConditionalFunction.simpleBuilder(DoubleDrops::new);
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherLootFunctions.DOUBLE_DROPS.get();
    }
}
