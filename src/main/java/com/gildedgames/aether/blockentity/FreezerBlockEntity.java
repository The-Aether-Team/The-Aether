package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.inventory.menu.FreezerMenu;

import com.gildedgames.aether.recipe.AetherRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class FreezerBlockEntity extends AbstractAetherFurnaceBlockEntity {
	private static final Map<Item, Integer> freezingMap = new LinkedHashMap<>();

	public FreezerBlockEntity(BlockPos pos, BlockState state) {
		super(AetherBlockEntityTypes.FREEZER.get(), pos, state, AetherRecipeTypes.FREEZING.get());
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("menu." + Aether.MODID + ".freezer");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
		return new FreezerMenu(id, playerInventory, this, this.dataAccess);
	}

	@Override
	protected int getBurnDuration(ItemStack fuelStack) {
		if (fuelStack.isEmpty() || !getFreezingMap().containsKey(fuelStack.getItem())) {
			return 0;
		} else {
			return getFreezingMap().get(fuelStack.getItem());
		}
	}

	public static Map<Item, Integer> getFreezingMap() {
		return freezingMap;
	}

	private static void addItemTagFreezingTime(TagKey<Item> itemTag, int burnTime) {
		ITagManager<Item> tags = ForgeRegistries.ITEMS.tags();
		if (tags != null) {
			tags.getTag(itemTag).stream().forEach((item) -> freezingMap.put(item, burnTime));
		}
	}

	public static void addItemFreezingTime(ItemLike itemProvider, int burnTime) {
		Item item = itemProvider.asItem();
		freezingMap.put(item, burnTime);
	}
}
