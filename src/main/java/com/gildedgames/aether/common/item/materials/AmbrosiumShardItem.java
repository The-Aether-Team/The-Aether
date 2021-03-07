package com.gildedgames.aether.common.item.materials;

import com.gildedgames.aether.common.registry.AetherBlocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
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
	public ActionResultType onItemUse(ItemUseContext context) {
		if (context instanceof BlockItemUseContext) {
			BlockItemUseContext blockcontext = (BlockItemUseContext) context;
			if (blockcontext.getWorld().getBlockState(blockcontext.getPos()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get()) {
				blockcontext.getWorld().setBlockState(blockcontext.getPos(), AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().getDefaultState());
				if (context.getPlayer() != null) {
					if (!context.getPlayer().isCreative()) {
						context.getItem().shrink(1);
					}
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.onItemUse(context);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
