package com.gildedgames.aether.common.loot.functions;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.block.util.IAetherDoubleDropBlock;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

public class DoubleDrops extends LootFunction
{
	protected DoubleDrops(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ItemStack run(ItemStack stack, LootContext context) {
		ItemStack tool = context.getParamOrNull(LootParameters.TOOL);
		if (tool != null && tool.getItem().is(AetherTags.Items.SKYROOT_TOOLS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0) {
			BlockState state = context.getParamOrNull(LootParameters.BLOCK_STATE);
			if (state != null && (!(state.getBlock() instanceof IAetherDoubleDropBlock) || state.getValue(AetherBlockStateProperties.DOUBLE_DROPS))) {
				if (tool.getToolTypes().contains(state.getHarvestTool())) {
					stack.setCount(2 * stack.getCount());
				}
			}
		}
		return stack;
	}

	public static LootFunction.Builder<?> builder() {
		return LootFunction.simpleBuilder(DoubleDrops::new);
	}

    @Override
    public LootFunctionType getType() {
        return AetherLoot.DOUBLE_DROPS;
    }

    public static class Serializer extends LootFunction.Serializer<DoubleDrops>
	{
		@Override
		public void serialize(JsonObject object, DoubleDrops functionClazz, JsonSerializationContext serializationContext) {
			super.serialize(object, functionClazz, serializationContext);
		}

		@Override
		public DoubleDrops deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
			return new DoubleDrops(conditionsIn);
		}
	}
}
