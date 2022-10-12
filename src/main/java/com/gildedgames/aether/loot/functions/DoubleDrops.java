package com.gildedgames.aether.loot.functions;

import com.gildedgames.aether.item.tools.abilities.SkyrootTool;
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

import javax.annotation.Nonnull;

public class DoubleDrops extends LootItemConditionalFunction {
	protected DoubleDrops(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Nonnull
	@Override
	protected ItemStack run(@Nonnull ItemStack stack, LootContext context) {
		ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
		BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
		if (tool.getItem() instanceof SkyrootTool skyrootTool) {
			return skyrootTool.doubleDrops(stack, tool, state);
		} else {
			return stack;
		}
	}

	public static LootItemConditionalFunction.Builder<?> builder() {
		return LootItemConditionalFunction.simpleBuilder(DoubleDrops::new);
	}

    @Nonnull
	@Override
    public LootItemFunctionType getType() {
        return AetherLootFunctions.DOUBLE_DROPS.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<DoubleDrops> {
		@Override
		public void serialize(@Nonnull JsonObject object, @Nonnull DoubleDrops function, @Nonnull JsonSerializationContext serializationContext) {
			super.serialize(object, function, serializationContext);
		}

		@Nonnull
		@Override
		public DoubleDrops deserialize(@Nonnull JsonObject object, @Nonnull JsonDeserializationContext deserializationContext, @Nonnull LootItemCondition[] conditions) {
			return new DoubleDrops(conditions);
		}
	}
}
