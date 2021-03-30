package com.gildedgames.aether.common.item.materials;

import com.gildedgames.aether.common.registry.AetherBlocks;

import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AmbrosiumShardItem extends Item
{
	public AmbrosiumShardItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if (context instanceof BlockItemUseContext) {
			BlockItemUseContext blockcontext = (BlockItemUseContext) context;
			if (blockcontext.getLevel().getBlockState(blockcontext.getClickedPos()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get()) {
				blockcontext.getLevel().setBlockAndUpdate(blockcontext.getClickedPos(), AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
				if (context.getPlayer() != null) {
					if (!context.getPlayer().isCreative()) {
						context.getItemInHand().shrink(1);
					}
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.useOn(context);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (AetherConfig.COMMON.edible_ambrosium.get()) {
			ItemStack itemstack = playerIn.getItemInHand(handIn);
			if (playerIn.getHealth() < playerIn.getMaxHealth() && !playerIn.isCreative()) {
				playerIn.startUsingItem(handIn);
				return ActionResult.consume(itemstack);
			} else {
				return ActionResult.fail(itemstack);
			}
		} else {
			return ActionResult.pass(playerIn.getItemInHand(handIn));
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stackIn, World worldIn, LivingEntity playerIn) {
		if (AetherConfig.COMMON.edible_ambrosium.get()) {
			playerIn.heal(1);
			stackIn.shrink(1);
		}
		return stackIn;
	}

	public UseAction getUseAnimation(ItemStack stackIn) {
		return AetherConfig.COMMON.edible_ambrosium.get() ? UseAction.EAT : UseAction.NONE;
	}

	public int getUseDuration(ItemStack stackIn) {
		return AetherConfig.COMMON.edible_ambrosium.get() ? 16 : 0;
	}
}
