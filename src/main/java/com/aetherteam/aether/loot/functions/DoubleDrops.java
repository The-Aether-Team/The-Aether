package com.aetherteam.aether.loot.functions;

import com.aetherteam.aether.item.tools.abilities.SkyrootTool;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class DoubleDrops extends LootItemConditionalFunction {
	protected DoubleDrops(LootItemCondition[] conditions) {
		super(conditions);
	}

	/**
	 * Doubles the dropped stack through {@link SkyrootTool#doubleDrops(ItemStack, ItemStack, BlockState)}.
	 * @param stack The {@link ItemStack} for the loot pool.
	 * @param context The {@link LootContext}.
	 * @return The {@link ItemStack} for the loot pool.
	 */
	@Override
	protected ItemStack run(ItemStack stack, LootContext context) {
		ItemStack toolStack = context.getParamOrNull(LootContextParams.TOOL);
		BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
		if (toolStack.getItem() instanceof SkyrootTool skyrootTool) {
			return skyrootTool.doubleDrops(stack, toolStack, blockState);
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

    public static class Serializer extends LootItemConditionalFunction.Serializer<DoubleDrops> {
		@Override
		public void serialize(JsonObject json, DoubleDrops instance, JsonSerializationContext context) {
			super.serialize(json, instance, context);
		}

		@Override
		public DoubleDrops deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
			return new DoubleDrops(conditions);
		}
	}
}
