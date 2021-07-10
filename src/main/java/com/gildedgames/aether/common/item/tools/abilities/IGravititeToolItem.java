package com.gildedgames.aether.common.item.tools.abilities;

import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public interface IGravititeToolItem
{
	default ActionResultType floatBlock(ItemUseContext context, float destroySpeed, float efficiency) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		ItemStack heldItem = context.getItemInHand();
		BlockState state = world.getBlockState(pos);
		PlayerEntity player = context.getPlayer();
		Hand hand = context.getHand();
		if (player != null) {
			if ((destroySpeed == efficiency || ForgeHooks.isToolEffective(world, pos, heldItem)) && world.isEmptyBlock(pos.above())) {
				if (world.getBlockEntity(pos) == null && state.getDestroySpeed(world, pos) != -1.0F) {
					if (!world.isClientSide) {
						FloatingBlockEntity entity = new FloatingBlockEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state);
						if (world.getBlockState(pos).is(BlockTags.ANVIL)) {
							entity.setHurtsEntities(true);
						}
						world.removeBlock(pos, false);
						world.addFreshEntity(entity);
						heldItem.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
					}
					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		}
		return ActionResultType.PASS;
	}
}
