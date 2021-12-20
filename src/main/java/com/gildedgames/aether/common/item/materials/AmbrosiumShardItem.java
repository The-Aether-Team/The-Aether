package com.gildedgames.aether.common.item.materials;

import com.gildedgames.aether.common.registry.AetherBlocks;

import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;

public class AmbrosiumShardItem extends Item
{
	public AmbrosiumShardItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player playerentity = context.getPlayer();
		Level world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		if (blockstate.is(AetherTags.Blocks.ENCHANTABLE_GRASS_BLOCKS)) {
			world.setBlockAndUpdate(blockpos, AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
			if (playerentity != null) {
				if (!playerentity.getAbilities().instabuild) {
					context.getItemInHand().shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(context);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (AetherConfig.COMMON.edible_ambrosium.get()) {
			ItemStack itemstack = playerIn.getItemInHand(handIn);
			if (playerIn.getHealth() < playerIn.getMaxHealth() && !playerIn.isCreative()) {
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
		if (AetherConfig.COMMON.edible_ambrosium.get()) {
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
