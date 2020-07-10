package com.aether.client;

import com.aether.CommonProxy;
import com.aether.block.AetherBlocks;
import com.aether.client.gui.screen.inventory.EnchanterScreen;
import com.aether.client.renderer.entity.FloatingBlockRenderer;
import com.aether.client.renderer.entity.MimicRenderer;
import com.aether.client.renderer.entity.SentryRenderer;
import com.aether.entity.AetherEntityTypes;
import com.aether.entity.item.FloatingBlockEntity;
import com.aether.entity.monster.MimicEntity;
import com.aether.entity.monster.SentryEntity;
import com.aether.inventory.container.AetherContainerTypes;
import com.aether.item.AetherItems;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@SubscribeEvent
	@Override
	public void commonSetup(FMLCommonSetupEvent event) {
		super.commonSetup(event);
		registerColors();
	}
	
	@SubscribeEvent
	@Override
	public void clientSetup(FMLClientSetupEvent event) {
		super.clientSetup(event);
		registerEntityRenderers();
		registerGuiFactories();
	}
	
	private void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLOATING_BLOCK, FloatingBlockRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MIMIC, MimicRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SENTRY, SentryRenderer::new);
	}
	
	private void registerGuiFactories() {
		ScreenManager.registerFactory(AetherContainerTypes.ENCHANTER, EnchanterScreen::new);
	}
	
	private void registerColors() {
		// Block colors
		registerColor(AetherBlocks.BLUE_AERCLOUD);
		registerColor(AetherBlocks.GOLDEN_AERCLOUD);
		
		// Item colors
		registerColor(AetherBlocks.BLUE_AERCLOUD.asItem());
		registerColor(AetherBlocks.GOLDEN_AERCLOUD.asItem());
		registerColor(AetherItems.MIMIC_SPAWN_EGG);
		registerColor(AetherItems.SENTRY_SPAWN_EGG);
	}
	
	private static <B extends Block & IBlockColor> void registerColor(B block) {
		Minecraft.getInstance().getBlockColors().register(block, block);
	}
	
	private static <I extends Item & IItemColor> void registerColor(I item) {
		Minecraft.getInstance().getItemColors().register(item, item);
	}
	
	private static void registerColor(Item item, IItemColor colorProvider) {
		Minecraft.getInstance().getItemColors().register(colorProvider, item);
	}
	
	private static void registerColor(SpawnEggItem spawneggitem) {
		Minecraft.getInstance().getItemColors().register((itemStack, tintIndex) -> spawneggitem.getColor(tintIndex), spawneggitem);
	}
	
}
