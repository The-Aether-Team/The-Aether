package com.aether.world.storage.loot.functions;

import com.aether.Aether;
import com.aether.AetherTags;
import com.aether.block.IAetherDoubleDropBlock;
import com.aether.block.state.properties.AetherBlockStateProperties;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class DoubleDrops extends LootFunction {

	protected DoubleDrops(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ItemStack doApply(ItemStack stack, LootContext context) {
		ItemStack tool = context.get(LootParameters.TOOL);
		if (tool != null && tool.getItem().isIn(AetherTags.Items.SKYROOT_TOOLS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0) {
			BlockState state = context.get(LootParameters.BLOCK_STATE);
			if (state == null || !(state.getBlock() instanceof IAetherDoubleDropBlock) || state.get(AetherBlockStateProperties.DOUBLE_DROPS) == true) {
				stack.setCount(2 * stack.getCount());
			}
		}
		return stack;
	}
	
	public static LootFunction.Builder<?> builder() {
		return LootFunction.builder(DoubleDrops::new);
	}
	
	public static class Serializer extends LootFunction.Serializer<DoubleDrops> {
		public Serializer() {
			super(new ResourceLocation(Aether.MODID, "double_drops"), DoubleDrops.class);
		}
		
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
