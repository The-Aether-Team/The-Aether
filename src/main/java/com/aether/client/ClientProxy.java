package com.aether.client;

import com.aether.Aether;
import com.aether.CommonProxy;
import com.aether.registry.AetherBlocks;
import com.aether.block.util.IAetherBlockColor;
import com.aether.capability.AetherCapabilities;
import com.aether.client.gui.screen.inventory.EnchanterScreen;
import com.aether.client.gui.screen.inventory.FreezerScreen;
import com.aether.client.gui.screen.inventory.IncubatorScreen;
import com.aether.client.renderer.entity.*;
import com.aether.client.renderer.tileentity.ChestMimicTileEntityRenderer;
import com.aether.client.renderer.tileentity.CustomItemStackTileEntityRenderer;
import com.aether.client.renderer.tileentity.TreasureChestTileEntityRenderer;
import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherContainerTypes;
import com.aether.registry.AetherItems;
import com.aether.item.IAetherItemColor;
import com.aether.item.tools.abilities.IValkyrieToolItem;
import com.aether.network.AetherPacketHandler;
import com.aether.network.packet.ExtendedAttackPacket;
import com.aether.network.packet.JumpPacket;
import com.aether.registry.AetherTileEntityTypes;

import com.aether.entity.tile.ChestMimicTileEntity;
import com.aether.entity.tile.TreasureChestTileEntity;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy
{
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
		registerItemModelProperties();
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
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), m -> new SpriteRenderer<>(m, event.getMinecraftSupplier().get().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.GOLDEN_DART.get(), DartRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ENCHANTED_DART.get(), DartRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.POISON_DART.get(), DartRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.PHOENIX_ARROW.get(), PhoenixArrowRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SPECTRAL_PHOENIX_ARROW.get(), PhoenixArrowRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MIMIC.get(), MimicRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SENTRY.get(), SentryRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MOA.get(), MoaRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.PHYG.get(), PhygRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.WHIRLWIND.get(), WhirlwindRenderer::new);
	}
	
	protected void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.CHEST_MIMIC.get(), ChestMimicTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.TREASURE_CHEST.get(), TreasureChestTileEntityRenderer::new);
	}
	
	protected void registerGuiFactories() {
		ScreenManager.registerFactory(AetherContainerTypes.ENCHANTER.get(), EnchanterScreen::new);
		ScreenManager.registerFactory(AetherContainerTypes.FREEZER.get(), FreezerScreen::new);
		ScreenManager.registerFactory(AetherContainerTypes.INCUBATOR.get(), IncubatorScreen::new);
	}
	
	protected void registerColors() {
		// Block colors
		registerColor(AetherBlocks.BLUE_AERCLOUD.get());
		registerColor(AetherBlocks.GOLDEN_AERCLOUD.get());
		
		// Item colors
		registerColor(AetherBlocks.BLUE_AERCLOUD.get().asItem());
		registerColor(AetherBlocks.GOLDEN_AERCLOUD.get().asItem());
		registerColor(AetherItems.MIMIC_SPAWN_EGG.get());
		registerColor(AetherItems.SENTRY_SPAWN_EGG.get());
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
		setTranslucentRenderLayer(AetherBlocks.COLD_AERCLOUD.get());
		setTranslucentRenderLayer(AetherBlocks.BLUE_AERCLOUD.get());
		setTranslucentRenderLayer(AetherBlocks.GOLDEN_AERCLOUD.get());
		setTranslucentRenderLayer(AetherBlocks.PINK_AERCLOUD.get());
		setTranslucentRenderLayer(AetherBlocks.AEROGEL.get());
		setTranslucentRenderLayer(AetherBlocks.AEROGEL_SLAB.get());
		setTranslucentRenderLayer(AetherBlocks.AEROGEL_STAIRS.get());
		setTranslucentRenderLayer(AetherBlocks.AEROGEL_WALL.get());
		setTranslucentRenderLayer(AetherBlocks.QUICKSOIL_GLASS.get());
		setTranslucentRenderLayer(AetherBlocks.AETHER_PORTAL.get());
		setCutoutRenderLayer(AetherBlocks.BERRY_BUSH.get());
		setCutoutRenderLayer(AetherBlocks.BERRY_BUSH_STEM.get());
		setCutoutRenderLayer(AetherBlocks.AMBROSIUM_TORCH.get());
		setCutoutRenderLayer(AetherBlocks.AMBROSIUM_WALL_TORCH.get());
		setCutoutRenderLayer(AetherBlocks.SKYROOT_SAPLING.get());
		setCutoutRenderLayer(AetherBlocks.GOLDEN_OAK_SAPLING.get());
		setCutoutRenderLayer(AetherBlocks.PURPLE_FLOWER.get());
		setCutoutRenderLayer(AetherBlocks.WHITE_FLOWER.get());
		setCutoutRenderLayer(AetherBlocks.POTTED_PURPLE_FLOWER.get());
		setCutoutRenderLayer(AetherBlocks.POTTED_WHITE_FLOWER.get());
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

	protected void registerItemModelProperties() {
		ItemModelsProperties.registerProperty(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pulling"), (stack, world, living) -> {
			return living != null && living.isHandActive() && living.getActiveItemStack() == stack ? 1.0F : 0.0F;
		});
		ItemModelsProperties.registerProperty(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pull"), (stack, world, living) -> {
			if (living == null) {
				return 0.0F;
			} else {
				return living.getActiveItemStack() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getItemInUseCount()) / 20.0F;
			}
		});
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

	@SubscribeEvent
	public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
		PlayerEntity player = event.getPlayer();
		if(event.getItemStack().getItem() instanceof IValkyrieToolItem) {
			handleExtendedReach(player);
		}
	}

	@SubscribeEvent
	public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		PlayerEntity player = event.getPlayer();
		if(event.getItemStack().getItem() instanceof IValkyrieToolItem) {
			event.setCanceled(handleExtendedReach(player));
		}
	}

	private static boolean handleExtendedReach(PlayerEntity player) {
		double reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
		Vector3d eyePos = player.getEyePosition(1.0F);
		Vector3d lookVec = player.getLookVec();
		Vector3d reachVec = eyePos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);
		AxisAlignedBB playerBox = player.getBoundingBox().expand(lookVec.scale(reach)).grow(1.0D, 1.0D, 1.0D);
		EntityRayTraceResult traceResult = ProjectileHelper.rayTraceEntities(player, eyePos, reachVec, playerBox, (target) -> {
			return !target.isSpectator() && target.canBeCollidedWith();
		}, reach * reach);
		if (traceResult != null) {
			Entity target = traceResult.getEntity();
			Vector3d hitVec = traceResult.getHitVec();
			double distance = eyePos.squareDistanceTo(hitVec);
			if (distance < reach * reach) {
				AetherPacketHandler.INSTANCE.sendToServer(new ExtendedAttackPacket(target.getEntityId()));
				return true;
			}
		}
		return false;
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
