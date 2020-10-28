package com.aether.client;

import com.aether.CommonProxy;
import com.aether.block.AetherBlocks;
import com.aether.capability.AetherCapabilities;
import com.aether.client.gui.screen.inventory.EnchanterScreen;
import com.aether.client.gui.screen.inventory.FreezerScreen;
import com.aether.client.gui.screen.inventory.IncubatorScreen;
import com.aether.client.renderer.entity.*;
import com.aether.client.renderer.tileentity.ChestMimicTileEntityRenderer;
import com.aether.entity.AetherEntityTypes;
import com.aether.inventory.container.AetherContainerTypes;
import com.aether.item.AetherItems;
import com.aether.network.AetherPacketHandler;
import com.aether.network.JumpPacket;
import com.aether.tileentity.AetherTileEntityTypes;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
		registerEntityRenderers(event);
		registerTileEntityRenderers();
		registerGuiFactories();
		registerBlockRenderLayers();
	}
	
	protected void registerEntityRenderers(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.LIGHTNING_KNIFE, LightningKnifeRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ZEPHYR_SNOWBALL, m -> new SpriteRenderer<>(m, event.getMinecraftSupplier().get().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLOATING_BLOCK, FloatingBlockRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MIMIC, MimicRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SENTRY, SentryRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ZEPHYR, ZephyrRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MOA, MoaRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.PHYG, PhygRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLYING_COW, FlyingCowRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SHEEPUFF, SheepuffRenderer::new);
	}
	
	protected void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.CHEST_MIMIC, ChestMimicTileEntityRenderer::new);
	}
	
	protected void registerGuiFactories() {
		ScreenManager.registerFactory(AetherContainerTypes.ENCHANTER, EnchanterScreen::new);
		ScreenManager.registerFactory(AetherContainerTypes.FREEZER, FreezerScreen::new);
		ScreenManager.registerFactory(AetherContainerTypes.INCUBATOR, IncubatorScreen::new);
	}
	
	protected void registerColors() {
		// Block colors
		registerColor(AetherBlocks.BLUE_AERCLOUD);
		registerColor(AetherBlocks.GOLDEN_AERCLOUD);
		
		// Item colors
		registerColor(AetherBlocks.BLUE_AERCLOUD.asItem());
		registerColor(AetherBlocks.GOLDEN_AERCLOUD.asItem());
		registerColor(AetherItems.MIMIC_SPAWN_EGG);
		registerColor(AetherItems.SENTRY_SPAWN_EGG);
	}
	
	public static <B extends Block & IBlockColor> void registerColor(B block) {
		Minecraft.getInstance().getBlockColors().register(block, block);
	}
	
	public static <I extends Item & IItemColor> void registerColor(I item) {
		Minecraft.getInstance().getItemColors().register(item, item);
	}
	
	public static void registerColor(Item item, IItemColor colorProvider) {
		Minecraft.getInstance().getItemColors().register(colorProvider, item);
	}
	
	public static void registerColor(SpawnEggItem spawneggitem) {
		Minecraft.getInstance().getItemColors().register((itemStack, tintIndex) -> spawneggitem.getColor(tintIndex), spawneggitem);
	}
	
	protected void registerBlockRenderLayers() {
		setTranslucentRenderLayer(AetherBlocks.COLD_AERCLOUD);
		setTranslucentRenderLayer(AetherBlocks.BLUE_AERCLOUD);
		setTranslucentRenderLayer(AetherBlocks.GOLDEN_AERCLOUD);
		setTranslucentRenderLayer(AetherBlocks.PINK_AERCLOUD);
		setTranslucentRenderLayer(AetherBlocks.AEROGEL);
		setTranslucentRenderLayer(AetherBlocks.AEROGEL_SLAB);
		setTranslucentRenderLayer(AetherBlocks.AEROGEL_STAIRS);
		setTranslucentRenderLayer(AetherBlocks.AEROGEL_WALL);
		setTranslucentRenderLayer(AetherBlocks.QUICKSOIL_GLASS);
		setTranslucentRenderLayer(AetherBlocks.AETHER_PORTAL);
		setCutoutRenderLayer(AetherBlocks.BERRY_BUSH);
		setCutoutRenderLayer(AetherBlocks.BERRY_BUSH_STEM);
		setCutoutRenderLayer(AetherBlocks.AMBROSIUM_TORCH);
		setCutoutRenderLayer(AetherBlocks.AMBROSIUM_WALL_TORCH);
		setCutoutRenderLayer(AetherBlocks.SKYROOT_SAPLING);
		setCutoutRenderLayer(AetherBlocks.GOLDEN_OAK_SAPLING);
	}
	
	public static void setSolidRenderLayer(Block block) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getSolid());
	}
	
	public static void setCutoutRenderLayer(Block block) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
	}
	
	public static void setCutoutMippedRenderLayer(Block block) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
	}
	
	public static void setTranslucentRenderLayer(Block block) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucent());
	}
	
	public static void setTranslucentNoCrumblingRenderLayer(Block block) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucentNoCrumbling());
	}
	
	@SubscribeEvent
	public void onJump(InputUpdateEvent event) {
		event.getPlayer().getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY).ifPresent((player) -> {
			boolean isJumping = event.getMovementInput().jump;
			if (isJumping != player.isJumping()) {
				AetherPacketHandler.INSTANCE.sendToServer(new JumpPacket(event.getPlayer().getUniqueID(), isJumping));
				
				player.setJumping(isJumping);
			}
		});
	}
	
}
