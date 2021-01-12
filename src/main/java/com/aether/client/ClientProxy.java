package com.aether.client;

import com.aether.Aether;
import com.aether.CommonProxy;
import com.aether.block.AetherBlocks;
import com.aether.block.IAetherBlockColor;
import com.aether.capability.AetherCapabilities;
import com.aether.client.gui.screen.inventory.EnchanterScreen;
import com.aether.client.gui.screen.inventory.FreezerScreen;
import com.aether.client.gui.screen.inventory.IncubatorScreen;
import com.aether.client.renderer.entity.*;
import com.aether.client.renderer.tileentity.ChestMimicTileEntityRenderer;
import com.aether.client.renderer.tileentity.CustomItemStackTileEntityRenderer;
import com.aether.client.renderer.tileentity.TreasureChestTileEntityRenderer;
import com.aether.entity.AetherEntityTypes;
import com.aether.inventory.container.AetherContainerTypes;
import com.aether.item.AetherItems;
import com.aether.item.IAetherItemColor;
import com.aether.network.AetherPacketHandler;
import com.aether.network.JumpPacket;
import com.aether.tileentity.AetherTileEntityTypes;

import com.aether.tileentity.ChestMimicTileEntity;
import com.aether.tileentity.TreasureChestTileEntity;
import com.aether.world.dimension.AetherDimensions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
		DimensionRenderInfo.field_239208_a_.put(AetherDimensions.AETHER_DIMENSION.getLocation(), new DimensionRenderInfo(-5.0F, true, DimensionRenderInfo.FogType.NORMAL, false, false) {
			@Override
			public Vector3d func_230494_a_(Vector3d color, float p_230494_2_) {
				return color.mul((p_230494_2_ * 0.94F + 0.06F), (p_230494_2_ * 0.94F + 0.06F), (p_230494_2_ * 0.91F + 0.09F));
			}

			@Override
			public boolean func_230493_a_(int x, int z) {
				return false;
			}
		});
	}
	
	protected void registerEntityRenderers(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.LIGHTNING_KNIFE, LightningKnifeRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ZEPHYR_SNOWBALL, m -> new SpriteRenderer<>(m, event.getMinecraftSupplier().get().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLOATING_BLOCK, FloatingBlockRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MIMIC, MimicRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SENTRY, SentryRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ZEPHYR, ZephyrRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MOA, MoaRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.COCKATRICE, CockatriceRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.AECHOR_PLANT, AechorPlantRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.PHYG, PhygRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLYING_COW, FlyingCowRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SHEEPUFF, SheepuffRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.AERWHALE, AerwhaleRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.WHIRLWIND, WhirlwindRenderer::new);
	}
	
	protected void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.CHEST_MIMIC, ChestMimicTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.TREASURE_CHEST, TreasureChestTileEntityRenderer::new);
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
	
	public static <B extends Block & IAetherBlockColor> void registerColor(B block) {
		Minecraft.getInstance().getBlockColors().register((blockState, lightReader, blockPos, color) -> block.getColor(false), block);
	}
	
	public static <I extends Item & IAetherItemColor> void registerColor(I item) {
		Minecraft.getInstance().getItemColors().register((itemStack, color) -> item.getColor(false), item);
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

	public static CustomItemStackTileEntityRenderer chestMimicRenderer() {
		return new CustomItemStackTileEntityRenderer(ChestMimicTileEntity::new);
	}

	public static CustomItemStackTileEntityRenderer treasureChestRenderer() {
		return new CustomItemStackTileEntityRenderer(TreasureChestTileEntity::new);
	}
	
	@SubscribeEvent
	public static void onJump(InputUpdateEvent event) {
		event.getPlayer().getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY).ifPresent((player) -> {
			boolean isJumping = event.getMovementInput().jump;
			if (isJumping != player.isJumping()) {
				AetherPacketHandler.INSTANCE.sendToServer(new JumpPacket(event.getPlayer().getUniqueID(), isJumping));
				
				player.setJumping(isJumping);
			}
		});
	}

	/**
	 * The purpose of this event handler is to prevent the fog from turning black near the void in the Aether.
	 */
	@SubscribeEvent
	public static void onRenderFogColor(EntityViewRenderEvent.FogColors event) {
		ActiveRenderInfo renderInfo = event.getInfo();
		ClientWorld world = (ClientWorld) renderInfo.getRenderViewEntity().world;
		if(world.getDimensionKey() == AetherDimensions.AETHER_WORLD) {
			double height = renderInfo.getProjectedView().y;
			ClientWorld.ClientWorldInfo worldInfo = world.getWorldInfo();
			double d0 = height * worldInfo.getFogDistance();
			FluidState fluidState = renderInfo.getFluidState();
			if(d0 < 1.0D && !fluidState.isTagged(FluidTags.LAVA)) { // Reverse implementation of FogRenderer.updateFogColor.
				if (d0 < 0.0D) {
					d0 = 0.0D;
				}
				d0 = d0 * d0;
				if(d0 != 0.0D) {
					event.setRed((float) ((double) event.getRed() / d0));
					event.setGreen((float) ((double) event.getGreen() / d0));
					event.setBlue((float) ((double) event.getBlue() / d0));
				}
			}
		}
	}
}
