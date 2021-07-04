package com.gildedgames.aether.common.item.materials;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.event.SwettyBallGrowGrassEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class SwetBallItem extends Item
{
	public SwetBallItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		ItemStack heldItem = context.getItemInHand();
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		PlayerEntity player = context.getPlayer();
		
		BlockState oldBlockState = world.getBlockState(pos);
		BlockState newBlockState = oldBlockState;
		Block block = oldBlockState.getBlock();
		
		if (block == AetherBlocks.AETHER_DIRT.get()) {
			newBlockState = AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState();
		} else if (block == Blocks.DIRT) {
			newBlockState = Blocks.GRASS_BLOCK.defaultBlockState();
		}
		
		SwettyBallGrowGrassEvent event = new SwettyBallGrowGrassEvent(heldItem, oldBlockState, newBlockState, player, pos);
		MinecraftForge.EVENT_BUS.post(event);
		newBlockState = event.getNewBlockState();
		
		if (event.isCanceled() || oldBlockState == newBlockState) {
			return ActionResultType.FAIL;
		}
		
		world.setBlockAndUpdate(pos, newBlockState);
		
		if (!player.abilities.instabuild) {
			heldItem.shrink(1);
		}
		
		return ActionResultType.SUCCESS;
	}
}
