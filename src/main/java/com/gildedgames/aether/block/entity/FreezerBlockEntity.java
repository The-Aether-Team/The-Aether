package com.gildedgames.aether.block.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.recipe.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.inventory.container.FreezerMenu;

import com.gildedgames.aether.block.AetherBlockEntityTypes;
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
import java.util.Map;

public class FreezerBlockEntity extends AbstractAetherFurnaceBlockEntity
{
	private static final Map<Item, Integer> freezingMap = Maps.newLinkedHashMap();

	public FreezerBlockEntity(BlockPos pos, BlockState state) {
		super(AetherBlockEntityTypes.FREEZER.get(), pos, state, RecipeTypes.FREEZING.get());
	}
	
	@Nonnull
	@Override
	protected Component getDefaultName() {
		return Component.translatable("container." + Aether.MODID + ".freezer");
	}

	@Nonnull
	@Override
	protected AbstractContainerMenu createMenu(int id, @Nonnull Inventory playerInventory) {
		return new FreezerMenu(id, playerInventory, this, this.dataAccess);
	}

	public static Map<Item, Integer> getFreezingMap() {
		return freezingMap;
	}

	private static void addItemTagFreezingTime(TagKey<Item> itemTag, int burnTime) {
		for(Holder<Item> holder : Registry.ITEM.getTagOrEmpty(itemTag)) {
			freezingMap.put(holder.value(), burnTime);
		}
	}

	public static void addItemFreezingTime(ItemLike itemProvider, int burnTime) {
		Item item = itemProvider.asItem();
		freezingMap.put(item, burnTime);
	}

	@Override
	protected int getBurnDuration(ItemStack fuelStack) {
		if (fuelStack.isEmpty() || !getFreezingMap().containsKey(fuelStack.getItem())) {
			return 0;
		} else {
			return getFreezingMap().get(fuelStack.getItem());
		}
	}
}
