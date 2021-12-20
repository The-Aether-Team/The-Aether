package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.inventory.container.AltarContainer;

import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class AltarTileEntity extends AbstractFurnaceBlockEntity
{
	private static final Map<Item, Integer> enchantingMap = Maps.newLinkedHashMap();

	public AltarTileEntity(BlockPos pos, BlockState state) {
		super(AetherTileEntityTypes.ALTAR.get(), pos, state, RecipeTypes.ENCHANTING);
	}

	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent("container." + Aether.MODID + ".altar");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return new AltarContainer(id, player, this, this.dataAccess);
	}

	public static Map<Item, Integer> getEnchantingMap() {
		return enchantingMap;
	}

	private static void addItemTagEnchantingTime(Tag<Item> itemTag, int burnTimeIn) {
		for (Item item : itemTag.getValues()) {
			enchantingMap.put(item, burnTimeIn);
		}
	}

	public static void addItemEnchantingTime(ItemLike itemProvider, int burnTimeIn) {
		Item item = itemProvider.asItem();
		enchantingMap.put(item, burnTimeIn);
	}

//	@Override
//	public void burn(@Nullable Recipe<?> p_214007_1_) {
//		if (p_214007_1_ != null && this.(p_214007_1_)) {
//			ItemStack itemstack = this.items.get(0);
//			ItemStack itemstack1 = p_214007_1_.getResultItem();
//			ItemStack itemstack2 = this.items.get(2);
//
//			if (itemstack.getItem() == itemstack1.getItem() || itemstack1.is(AetherTags.Items.SAVE_NBT_IN_RECIPE)) {
//				EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(itemstack), itemstack1);
//				if (itemstack.hasTag()) {
//					itemstack1.setTag(itemstack.getTag());
//				}
//				itemstack1.setDamageValue(0);
//			}
//
//			if (itemstack2.isEmpty()) {
//				this.items.set(2, itemstack1.copy());
//			} else if (itemstack2.getItem() == itemstack1.getItem()) {
//				itemstack2.grow(itemstack1.getCount());
//			}
//
//			if (!this.level.isClientSide) {
//				this.setRecipeUsed(p_214007_1_);
//			}
//
//			itemstack.shrink(1);
//		}
//	}

	@Override
	protected int getBurnDuration(ItemStack fuel) {
		if (fuel.isEmpty() || !getEnchantingMap().containsKey(fuel.getItem())) {
			return 0;
		} else {
			return getEnchantingMap().get(fuel.getItem());
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
//
//	@Override
//	public List<Recipe<?>> getRecipesToAwardAndPopExperience(Level p_235640_1_, Vec3 p_235640_2_) {
//		return Lists.newArrayList();
//	}
}
