package com.gildedgames.aether.common.block.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.inventory.container.AltarContainer;

import com.gildedgames.aether.common.registry.AetherBlockEntityTypes;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;
import java.util.Map;

public class AltarBlockEntity extends AbstractAetherFurnaceBlockEntity
{
	private static final Map<Item, Integer> enchantingMap = Maps.newLinkedHashMap();

	public AltarBlockEntity(BlockPos pos, BlockState state) {
		super(AetherBlockEntityTypes.ALTAR.get(), pos, state, RecipeTypes.ENCHANTING);
	}

	@Nonnull
	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent("container." + Aether.MODID + ".altar");
	}

	@Nonnull
	@Override
	protected AbstractContainerMenu createMenu(int id, @Nonnull Inventory playerInventory) {
		return new AltarContainer(id, playerInventory, this, this.dataAccess);
	}

	public static Map<Item, Integer> getEnchantingMap() {
		return enchantingMap;
	}

	private static void addItemTagEnchantingTime(Tag<Item> itemTag, int burnTime) {
		for (Item item : itemTag.getValues()) {
			enchantingMap.put(item, burnTime);
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
