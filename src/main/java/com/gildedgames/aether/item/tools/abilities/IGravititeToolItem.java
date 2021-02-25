package com.gildedgames.aether.item.tools.abilities;

import com.gildedgames.aether.entity.block.FloatingBlockEntity;
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
		BlockPos pos = context.getPos();
		World world = context.getWorld();
		BlockState state = world.getBlockState(pos);
		ItemStack heldItem = context.getItem();
		
		if ((this.getDestroySpeed(heldItem, state) == this.getTier().getEfficiency() || ForgeHooks.isToolEffective(world, pos, heldItem)) && world.isAirBlock(pos.up())) {
			if (world.getTileEntity(pos) != null || state.getBlockHardness(world, pos) == -1.0F) {
				return ActionResultType.FAIL;
			}
			
			if (!world.isRemote) {
				FloatingBlockEntity entity = new FloatingBlockEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state);
				entity.floatTime = 0;
				world.addEntity(entity);
			}
			
			if (!context.getPlayer().isCreative()) {
				heldItem.damageItem(4, context.getPlayer(), (player) -> player.sendBreakAnimation(context.getHand()));
			}
			
			return ActionResultType.SUCCESS;
		}
		
		return defaultItemUse(context);
	}
	
	default ActionResultType defaultItemUse(ItemUseContext context) {
		return ActionResultType.SUCCESS;
	}
	
}
