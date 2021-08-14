package com.gildedgames.aether.common.item.materials;

import com.gildedgames.aether.common.registry.AetherBlocks;

import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AmbrosiumShardItem extends Item
{
	public AmbrosiumShardItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		PlayerEntity playerentity = context.getPlayer();
		World world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		if (blockstate.getBlock().is(AetherTags.Blocks.ENCHANTABLE_GRASS_BLOCKS)) {
			world.setBlockAndUpdate(blockpos, AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
			if (playerentity != null) {
				if (!playerentity.abilities.instabuild) {
					context.getItemInHand().shrink(1);
				}
				return ActionResultType.SUCCESS;
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
