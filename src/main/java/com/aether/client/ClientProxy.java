package com.aether.client;

import com.aether.CommonProxy;
import com.aether.block.AetherBlocks;
import com.aether.client.renderer.entity.FloatingBlockRenderer;
import com.aether.client.renderer.entity.MimicRenderer;
import com.aether.entity.item.FloatingBlockEntity;
import com.aether.entity.monster.MimicEntity;
import com.aether.item.AetherItems;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public void commonSetup(FMLCommonSetupEvent event) {
		super.commonSetup(event);
		registerColors();
	}
	
	public void clientSetup(FMLClientSetupEvent event) {
		super.clientSetup(event);
		registerEntityRenderers();
	}
	
	private void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(FloatingBlockEntity.class, FloatingBlockRenderer::new);	
		RenderingRegistry.registerEntityRenderingHandler(MimicEntity.class, MimicRenderer::new);
	}
	
	private void registerColors() {
		// Block colors
		registerColor(AetherBlocks.BLUE_AERCLOUD);
		registerColor(AetherBlocks.GOLDEN_AERCLOUD);
		
		// Item colors
		registerColor(AetherItems.BLUE_AERCLOUD);
		registerColor(AetherItems.GOLDEN_AERCLOUD);
	}
	
	private static <B extends Block & IBlockColor> void registerColor(B block) {
		Minecraft.getInstance().getBlockColors().register(block, block);
	}
	
	private static <I extends Item & IItemColor> void registerColor(I item) {
		Minecraft.getInstance().getItemColors().register(item, item);
	}
	
}
