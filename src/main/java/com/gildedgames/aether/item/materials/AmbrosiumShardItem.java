package com.gildedgames.aether.item.materials;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.item.materials.behavior.ItemUseConversion;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.recipes.block.AmbrosiumRecipe;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;

import javax.annotation.Nonnull;

public class AmbrosiumShardItem extends Item implements ItemUseConversion<AmbrosiumRecipe> {
	public AmbrosiumShardItem(Item.Properties properties) {
		super(properties);
	}

	@Nonnull
	@Override
	public InteractionResult useOn(@Nonnull UseOnContext context) {
		return this.convertBlock(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get(), context);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (AetherConfig.COMMON.edible_ambrosium.get()) {
			ItemStack itemstack = playerIn.getItemInHand(handIn);
			if (playerIn.getHealth() < playerIn.getMaxHealth() || playerIn.isCreative()) {
				playerIn.startUsingItem(handIn);
				return InteractionResultHolder.consume(itemstack);
			} else {
				return InteractionResultHolder.fail(itemstack);
			}
		} else {
			return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stackIn, Level worldIn, LivingEntity playerIn) {
		if (AetherConfig.COMMON.edible_ambrosium.get() && playerIn instanceof Player player && !player.isCreative()) {
			playerIn.heal(1);
			stackIn.shrink(1);
		}
		return stackIn;
	}

	public UseAnim getUseAnimation(ItemStack stackIn) {
		return AetherConfig.COMMON.edible_ambrosium.get() ? UseAnim.EAT : UseAnim.NONE;
	}

	public int getUseDuration(ItemStack stackIn) {
		return AetherConfig.COMMON.edible_ambrosium.get() ? 16 : 0;
	}
}
