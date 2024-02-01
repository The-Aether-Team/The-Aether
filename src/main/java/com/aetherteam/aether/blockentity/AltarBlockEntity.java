package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.inventory.menu.AltarMenu;
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
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.tags.ITagManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AltarBlockEntity extends AbstractAetherFurnaceBlockEntity {
	private static final Map<Item, Integer> enchantingMap = new LinkedHashMap<>();

	public AltarBlockEntity(BlockPos pos, BlockState state) {
		super(AetherBlockEntityTypes.ALTAR.get(), pos, state, AetherRecipeTypes.ENCHANTING.get());
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("menu." + Aether.MODID + ".altar");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
		return new AltarMenu(id, playerInventory, this, this.dataAccess);
	}

	@Override
	protected int getBurnDuration(ItemStack fuelStack) {
		if (fuelStack.isEmpty() || !getEnchantingMap().containsKey(fuelStack.getItem())) {
			return 0;
		} else {
			return getEnchantingMap().get(fuelStack.getItem());
		}
	}

	public static Map<Item, Integer> getEnchantingMap() {
		return enchantingMap;
	}

	public static void addItemEnchantingTime(ItemLike itemProvider, int burnTime) {
		Item item = itemProvider.asItem();
		getEnchantingMap().put(item, burnTime);
	}

	public static void addItemsEnchantingTime(ItemLike[] itemProviders, int burnTime) {
		Stream.of(itemProviders).map(ItemLike::asItem).forEach((item) -> getEnchantingMap().put(item, burnTime));
	}

	public static void addItemTagEnchantingTime(TagKey<Item> itemTag, int burnTime) {
		ITagManager<Item> tags = ForgeRegistries.ITEMS.tags();
		if (tags != null) {
			tags.getTag(itemTag).stream().forEach((item) -> getEnchantingMap().put(item, burnTime));
		}
	}

	public static void removeItemEnchantingTime(ItemLike itemProvider) {
		Item item = itemProvider.asItem();
		getEnchantingMap().remove(item);
	}

	public static void removeItemsEnchantingTime(ItemLike[] itemProviders) {
		Stream.of(itemProviders).map(ItemLike::asItem).forEach((item) -> getEnchantingMap().remove(item));
	}

	public static void removeItemTagEnchantingTime(TagKey<Item> itemTag) {
		ITagManager<Item> tags = ForgeRegistries.ITEMS.tags();
		if (tags != null) {
			tags.getTag(itemTag).stream().forEach((item) -> getEnchantingMap().remove(item));
		}
	}
}