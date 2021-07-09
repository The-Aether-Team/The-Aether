package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.inventory.container.FreezerContainer;

import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class FreezerTileEntity extends AbstractFurnaceTileEntity
{
	private static final Map<Item, Integer> freezingMap = Maps.newLinkedHashMap();

	public FreezerTileEntity() {
		super(AetherTileEntityTypes.FREEZER.get(), RecipeTypes.FREEZING);
	}
	
	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + Aether.MODID + ".freezer");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new FreezerContainer(id, player, this, this.dataAccess);
	}

	public static Map<Item, Integer> getFreezingMap() {
		return freezingMap;
	}

	private static void addItemTagFreezingTime(ITag<Item> itemTag, int burnTimeIn) {
		for (Item item : itemTag.getValues()) {
			freezingMap.put(item, burnTimeIn);
		}
	}

	public static void addItemFreezingTime(IItemProvider itemProvider, int burnTimeIn) {
		Item item = itemProvider.asItem();
		freezingMap.put(item, burnTimeIn);
	}

	@Override
	public void burn(@Nullable IRecipe<?> p_214007_1_) {
		if (p_214007_1_ != null && this.canBurn(p_214007_1_)) {
			ItemStack itemstack = this.items.get(0);
			ItemStack itemstack1 = p_214007_1_.getResultItem();
			ItemStack itemstack2 = this.items.get(2);

			if (itemstack.getItem() == itemstack1.getItem()) {
				EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(itemstack), itemstack1);
				if (itemstack.hasTag()) {
					itemstack1.setTag(itemstack.getTag());
				}
				itemstack1.setDamageValue(0);
			}

			if (itemstack2.isEmpty()) {
				this.items.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}

			if (!this.level.isClientSide) {
				this.setRecipeUsed(p_214007_1_);
			}

			itemstack.shrink(1);
		}
	}

	@Override
	protected int getBurnDuration(ItemStack fuel) {
		if (fuel.isEmpty() || !getFreezingMap().containsKey(fuel.getItem())) {
			return 0;
		} else {
			return getFreezingMap().get(fuel.getItem());
		}
	}

	@Override
	public boolean canPlaceItem(int p_94041_1_, ItemStack p_94041_2_) {
		if (p_94041_1_ == 2) {
			return false;
		} else if (p_94041_1_ != 1) {
			return true;
		} else {
			return this.getBurnDuration(p_94041_2_) > 0;
		}
	}

	@Override
	public void awardUsedRecipesAndPopExperience(PlayerEntity p_235645_1_) { }

	@Override
	public List<IRecipe<?>> getRecipesToAwardAndPopExperience(World p_235640_1_, Vector3d p_235640_2_) {
		return Lists.newArrayList();
	}
}
