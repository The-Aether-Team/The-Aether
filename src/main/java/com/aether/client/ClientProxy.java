package com.aether.client;

import com.aether.CommonProxy;
import com.aether.block.AetherBlocks;
import com.aether.block.TintedAercloudBlock;
import com.aether.item.AetherItems;
import com.aether.item.TintedBlockItem;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void setup() {
		registerColors();
	}
	
	private static void registerColors() {
		// Block colors
		registerColor((TintedAercloudBlock) AetherBlocks.BLUE_AERCLOUD);
		registerColor((TintedAercloudBlock) AetherBlocks.GOLDEN_AERCLOUD);
		
		// Item colors
		registerColor((TintedBlockItem) AetherItems.BLUE_AERCLOUD);
		registerColor((TintedBlockItem) AetherItems.GOLDEN_AERCLOUD);
	}
	
	private static <B extends Block & IBlockColor> void registerColor(B block) {
		Minecraft.getInstance().getBlockColors().register(block, block);
	}
	
	private static <I extends Item & IItemColor> void registerColor(I item) {
		Minecraft.getInstance().getItemColors().register(item, item);
	}
	
}
