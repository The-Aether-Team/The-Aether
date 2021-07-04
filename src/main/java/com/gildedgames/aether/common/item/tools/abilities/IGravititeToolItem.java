package com.gildedgames.aether.common.item.tools.abilities;

import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.BlockState;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public interface IGravititeToolItem {
	
	float getDestroySpeed(ItemStack item, BlockState state);

	IItemTier getTier();
	
	Logger log = LogManager.getLogger(IGravititeToolItem.class);
	
	default ActionResultType onItemUse(ItemUseContext context) {
		BlockPos pos = context.getClickedPos();
		World world = context.getLevel();
		BlockState state = world.getBlockState(pos);
		ItemStack heldItem = context.getItemInHand();
		
		if ((this.getDestroySpeed(heldItem, state) == this.getTier().getSpeed() || ForgeHooks.isToolEffective(world, pos, heldItem)) && world.isEmptyBlock(pos.above())) {
			if (world.getBlockEntity(pos) != null || state.getDestroySpeed(world, pos) == -1.0F) {
				return ActionResultType.FAIL;
			}
			
			if (!world.isClientSide) {
				FloatingBlockEntity entity = new FloatingBlockEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state);
				entity.floatTime = 0;
				world.addFreshEntity(entity);
			}

			heldItem.hurtAndBreak(4, context.getPlayer(), (player) -> player.broadcastBreakEvent(context.getHand()));
			
			return ActionResultType.SUCCESS;
		}
		
		return defaultItemUse(context);
	}
	
	default ActionResultType defaultItemUse(ItemUseContext context) {
		return ActionResultType.SUCCESS;
	}
	
}
