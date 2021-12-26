package com.gildedgames.aether.common.loot.functions;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.block.util.IAetherDoubleDropBlock;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
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
		//TODO: Needs to be made to work with the new tag system for determining harvest tools/efficiency tools.
//		ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
//		if (tool != null && tool.getItem().is(AetherTags.Items.SKYROOT_TOOLS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0) {
//			BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
//			if (state != null && (!(state.getBlock() instanceof IAetherDoubleDropBlock) || state.getValue(AetherBlockStateProperties.DOUBLE_DROPS))) {
//				if (tool.getToolTypes().contains(state.getHarvestTool())) {
//					stack.setCount(2 * stack.getCount());
//				}
//			}
//		}
		return stack;
	}

	public static LootItemConditionalFunction.Builder<?> builder() {
		return LootItemConditionalFunction.simpleBuilder(DoubleDrops::new);
	}

    @Override
    public LootItemFunctionType getType() {
        return AetherLoot.DOUBLE_DROPS;
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
