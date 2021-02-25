package com.gildedgames.aether.loot.functions;

import com.gildedgames.aether.registry.AetherLoot;
import com.gildedgames.aether.registry.AetherTags;
import com.gildedgames.aether.block.util.IAetherDoubleDropBlock;
import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;
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
	protected ItemStack doApply(ItemStack stack, LootContext context) {
		ItemStack tool = context.get(LootParameters.TOOL);
		if (tool != null && tool.getItem().isIn(AetherTags.Items.SKYROOT_TOOLS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0 && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0) {
			BlockState state = context.get(LootParameters.BLOCK_STATE);
			if (state != null && (!(state.getBlock() instanceof IAetherDoubleDropBlock) || state.get(AetherBlockStateProperties.DOUBLE_DROPS))) {
				if (tool.getToolTypes().contains(state.getHarvestTool())) {
					stack.setCount(2 * stack.getCount());
				}
			}
		}
		return stack;
	}

	public static LootFunction.Builder<?> builder() {
		return LootFunction.builder(DoubleDrops::new);
	}

    @Override
    public LootFunctionType getFunctionType() {
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
