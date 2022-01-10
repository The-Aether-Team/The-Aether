package com.gildedgames.aether.common.item.tools.abilities;

import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IGravititeToolItem
{
	default InteractionResult floatBlock(UseOnContext context, float destroySpeed, float efficiency) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		ItemStack heldItem = context.getItemInHand();
		BlockState state = world.getBlockState(pos);
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		if (player != null) {
			if ((destroySpeed == efficiency || heldItem.isCorrectToolForDrops(state)) && world.isEmptyBlock(pos.above())) {
				if (world.getBlockEntity(pos) == null && state.getDestroySpeed(world, pos) >= 0.0F) {
					if (!world.isClientSide) {
						FloatingBlockEntity entity = new FloatingBlockEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, state);
						if (world.getBlockState(pos).is(BlockTags.ANVIL)) {
							entity.setHurtsEntities(true);
						}
						world.removeBlock(pos, false);
						world.addFreshEntity(entity);
						heldItem.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
					}
					return InteractionResult.sidedSuccess(world.isClientSide);
				}
			}
		}
		return InteractionResult.PASS;
	}
}