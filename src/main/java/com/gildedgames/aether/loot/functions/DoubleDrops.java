package com.gildedgames.aether.loot.functions;

import com.gildedgames.aether.item.tools.abilities.SkyrootTool;
import com.gildedgames.aether.loot.AetherLoot;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class DoubleDrops extends LootItemConditionalFunction
{
	protected DoubleDrops(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ItemStack run(ItemStack stack, LootContext context) {
		ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
		BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
		stack = SkyrootTool.doubleDrops(stack, tool, state);
		return stack;
	}

	public static LootItemConditionalFunction.Builder<?> builder() {
		return LootItemConditionalFunction.simpleBuilder(DoubleDrops::new);
	}

    @Override
    public LootItemFunctionType getType() {
        return AetherLootFunctions.DOUBLE_DROPS.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<DoubleDrops>
	{
		@Override
		public void serialize(JsonObject object, DoubleDrops functionClazz, JsonSerializationContext serializationContext) {
			super.serialize(object, functionClazz, serializationContext);
		}

		@Override
		public DoubleDrops deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
			return new DoubleDrops(conditionsIn);
		}
	}
}
