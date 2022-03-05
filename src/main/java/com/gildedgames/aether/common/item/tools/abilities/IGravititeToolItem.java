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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface IGravititeToolItem
{
	default InteractionResult floatBlock(UseOnContext context, float destroySpeed, float efficiency) {
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		ItemStack heldItem = context.getItemInHand();
		BlockState blockState = level.getBlockState(blockPos);
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		if (player != null) {
			if ((destroySpeed == efficiency || heldItem.isCorrectToolForDrops(blockState)) && level.isEmptyBlock(blockPos.above())) {
				if (level.getBlockEntity(blockPos) == null && blockState.getDestroySpeed(level, blockPos) >= 0.0F && !blockState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
					if (!level.isClientSide) {
						FloatingBlockEntity entity = new FloatingBlockEntity(level, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, blockState);
						if (blockState.is(BlockTags.ANVIL)) {
							entity.setHurtsEntities(2.0F, 40);
						}
						level.addFreshEntity(entity);
						heldItem.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
					}
					return InteractionResult.sidedSuccess(level.isClientSide);
				}
			}
		}
		return InteractionResult.PASS;
	}
}