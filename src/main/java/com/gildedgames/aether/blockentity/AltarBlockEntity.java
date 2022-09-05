package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.inventory.menu.AltarMenu;

import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

public class AltarBlockEntity extends AbstractAetherFurnaceBlockEntity
{
	private static final Map<Item, Integer> enchantingMap = new LinkedHashMap<>();

	public AltarBlockEntity(BlockPos pos, BlockState state) {
		super(AetherBlockEntityTypes.ALTAR.get(), pos, state, AetherRecipeTypes.ENCHANTING.get());
	}

	@Nonnull
	@Override
	protected Component getDefaultName() {
		return Component.translatable("menu." + Aether.MODID + ".altar");
	}

	@Nonnull
	@Override
	protected AbstractContainerMenu createMenu(int id, @Nonnull Inventory playerInventory) {
		return new AltarMenu(id, playerInventory, this, this.dataAccess);
	}

	public static Map<Item, Integer> getEnchantingMap() {
		return enchantingMap;
	}

	private static void addItemTagEnchantingTime(TagKey<Item> itemTag, int burnTime) {
		for(Holder<Item> holder : Registry.ITEM.getTagOrEmpty(itemTag)) {
			enchantingMap.put(holder.value(), burnTime);
		}
	}

	public static void addItemEnchantingTime(ItemLike itemProvider, int burnTime) {
		Item item = itemProvider.asItem();
		enchantingMap.put(item, burnTime);
	}

	@Override
	protected int getBurnDuration(ItemStack fuelStack) {
		if (fuelStack.isEmpty() || !getEnchantingMap().containsKey(fuelStack.getItem())) {
			return 0;
		} else {
			return getEnchantingMap().get(fuelStack.getItem());
		}
	}
}
