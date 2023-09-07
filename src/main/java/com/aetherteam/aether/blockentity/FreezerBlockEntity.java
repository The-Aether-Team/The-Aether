package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.inventory.menu.FreezerMenu;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

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

	public static void addItemFreezingTime(ItemLike itemProvider, int burnTime) {
		Item item = itemProvider.asItem();
		getFreezingMap().put(item, burnTime);
	}

	public static void addItemsFreezingTime(ItemLike[] itemProviders, int burnTime) {
		Stream.of(itemProviders).map(ItemLike::asItem).forEach((item) -> getFreezingMap().put(item, burnTime));
	}

	public static void addItemTagFreezingTime(TagKey<Item> itemTag, int burnTime) {
		ITagManager<Item> tags = ForgeRegistries.ITEMS.tags();
		if (tags != null) {
			tags.getTag(itemTag).stream().forEach((item) -> getFreezingMap().put(item, burnTime));
		}
	}

	public static void removeItemFreezingTime(ItemLike itemProvider) {
		Item item = itemProvider.asItem();
		getFreezingMap().remove(item);
	}

	public static void removeItemsFreezingTime(ItemLike[] itemProviders) {
		Stream.of(itemProviders).map(ItemLike::asItem).forEach((item) -> getFreezingMap().remove(item));
	}

	public static void removeItemTagFreezingTime(TagKey<Item> itemTag) {
		ITagManager<Item> tags = ForgeRegistries.ITEMS.tags();
		if (tags != null) {
			tags.getTag(itemTag).stream().forEach((item) -> getFreezingMap().remove(item));
		}
	}
}