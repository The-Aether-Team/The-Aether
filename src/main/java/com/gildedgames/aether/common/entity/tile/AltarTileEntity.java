package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.inventory.container.AltarContainer;

import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Map;

public class AltarTileEntity extends AbstractFurnaceTileEntity
{
	private static final Map<Item, Integer> enchantingMap = Maps.newLinkedHashMap();

	public AltarTileEntity() {
		super(AetherTileEntityTypes.ALTAR.get(), RecipeTypes.ENCHANTING);
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + Aether.MODID + ".altar");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new AltarContainer(id, player, this, this.dataAccess);
	}

	public static Map<Item, Integer> getEnchantingMap() {
		return enchantingMap;
	}

	private static void addItemTagEnchantingTime(ITag<Item> itemTag, int burnTimeIn) {
		for (Item item : itemTag.getValues()) {
			enchantingMap.put(item, burnTimeIn);
		}
	}

	public static void addItemEnchantingTime(IItemProvider itemProvider, int burnTimeIn) {
		Item item = itemProvider.asItem();
		enchantingMap.put(item, burnTimeIn);
	}

	@Override
	protected int getBurnDuration(ItemStack fuel) {
		if (fuel.isEmpty() || !getEnchantingMap().containsKey(fuel.getItem())) {
			return 0;
		} else {
			return getEnchantingMap().get(fuel.getItem());
		}
	}
}
