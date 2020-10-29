package com.aether;

import static net.minecraft.tileentity.AbstractFurnaceTileEntity.addItemBurnTime;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.aether.block.AetherBlocks;
import com.aether.capability.AetherCapabilities;
import com.aether.entity.AetherEntityTypes.Registration;
import com.aether.event.AetherBannedItemEvent;
import com.aether.hooks.AetherEventHooks;
import com.aether.item.AetherItems;
import com.aether.network.AetherPacketHandler;
import com.aether.player.IAetherPlayer;
import com.aether.tags.AetherEntityTypeTags;
import com.aether.tags.AetherItemTags;
import com.aether.world.dimension.AetherDimensions;
import com.aether.world.storage.loot.functions.DoubleDrops;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy {

	@SubscribeEvent
	public void commonSetup(FMLCommonSetupEvent event) {
		AetherPacketHandler.register();
		AetherCapabilities.register();
		Registration.registerSpawnPlacements();
		registerLootTableFunctions();
		registerLootTableConditions();
		registerDispenserBehaviors();
		registerAxeStrippingBlocks();
	}
	
	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event) {
		
	}
	
	protected void registerLootTableFunctions() {
		LootFunctionManager.registerFunction(new DoubleDrops.Serializer());
	}
	
	protected void registerLootTableConditions() {
//		LootConditionManager.registerCondition(new ######.Serializer());
	}
	
	protected void registerDispenserBehaviors() {
		IDispenseItemBehavior dispenseSpawnEgg = new DefaultDispenseItemBehavior() {
			/**
			 * Dispense the specified stack, play the dispense sound and spawn particles.
			 */
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> entitytype = ((SpawnEggItem)stack.getItem()).getType(stack.getTag());
				entitytype.spawn(source.getWorld(), stack, (PlayerEntity)null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		};
		DispenserBlock.registerDispenseBehavior(AetherItems.PHYG_SPAWN_EGG, dispenseSpawnEgg);
		DispenserBlock.registerDispenseBehavior(AetherItems.FLYING_COW_SPAWN_EGG, dispenseSpawnEgg);
		DispenserBlock.registerDispenseBehavior(AetherItems.SHEEPUFF_SPAWN_EGG, dispenseSpawnEgg);
		//DispenserBlock.registerDispenseBehavior(AetherItems.AERBUNNY_SPAWN_EGG, dispenseSpawnEgg);
		//DispenserBlock.registerDispenseBehavior(AetherItems.AERWHALE_SPAWN_EGG, dispenseSpawnEgg);
		//DispenserBlock.registerDispenseBehavior(AetherItems.BLUE_SWET_SPAWN_EGG, dispenseSpawnEgg);
		//DispenserBlock.registerDispenseBehavior(AetherItems.GOLDEN_SWET_SPAWN_EGG, dispenseSpawnEgg);
		//DispenserBlock.registerDispenseBehavior(AetherItems.COCKATRICE_SPAWN_EGG, dispenseSpawnEgg);
		DispenserBlock.registerDispenseBehavior(AetherItems.SENTRY_SPAWN_EGG, dispenseSpawnEgg);
		DispenserBlock.registerDispenseBehavior(AetherItems.ZEPHYR_SPAWN_EGG, dispenseSpawnEgg);
		//DispenserBlock.registerDispenseBehavior(AetherItems.AECHOR_PLANT_SPAWN_EGG, dispenseSpawnEgg);
		DispenserBlock.registerDispenseBehavior(AetherItems.MIMIC_SPAWN_EGG, dispenseSpawnEgg);
		//DispenserBlock.registerDispenseBehavior(AetherItems.VALKYRIE_SPAWN_EGG, dispenseSpawnEgg);
		//DispenserBlock.registerDispenseBehavior(AetherItems.FIRE_MINION_SPAWN_EGG, dispenseSpawnEgg);
		DispenserBlock.registerDispenseBehavior(Items.FIRE_CHARGE, new OptionalDispenseBehavior() {
			/**
			 * Dispense the specified stack, play the dispense sound and spawn
			 * particles.
			 */
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				if (world.getDimension().getType() == AetherDimensions.THE_AETHER) {
					this.successful = false;
				}
				else {
					this.successful = true;
					Direction direction = source.getBlockState().get(DispenserBlock.FACING);
					IPosition iposition = DispenserBlock.getDispensePosition(source);
					double d0 = iposition.getX() + direction.getXOffset() * 0.3F;
					double d1 = iposition.getY() + direction.getYOffset() * 0.3F;
					double d2 = iposition.getZ() + direction.getZOffset() * 0.3F;
					Random random = world.rand;
					double d3 = random.nextGaussian() * 0.05 + direction.getXOffset();
					double d4 = random.nextGaussian() * 0.05 + direction.getYOffset();
					double d5 = random.nextGaussian() * 0.05 + direction.getZOffset();
					world.addEntity(Util.make(new SmallFireballEntity(world, d0, d1, d2, d3, d4, d5), (entity) -> entity.setStack(stack)));
					stack.shrink(1);
				}
				return stack;
			}

			/**
			 * Play the dispense sound from the specified block.
			 */
			@Override
			protected void playDispenseSound(IBlockSource source) {
				source.getWorld().playEvent(this.successful? 1018 : 1001, source.getBlockPos(), 0);
			}
		});
		DispenserBlock.registerDispenseBehavior(Items.FLINT_AND_STEEL, new OptionalDispenseBehavior() {
			/**
			 * Dispense the specified stack, play the dispense sound and spawn
			 * particles.
			 */
			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				if (world.getDimension().getType() == AetherDimensions.THE_AETHER) {
					this.successful = false;
				}
				else {
					this.successful = true;
					BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
					BlockState blockstate = world.getBlockState(blockpos);
					if (FlintAndSteelItem.canSetFire(blockstate, world, blockpos)) {
						world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
					}
					else if (FlintAndSteelItem.isUnlitCampfire(blockstate)) {
						world.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, Boolean.valueOf(true)));
					}
					else if (blockstate.isFlammable(world, blockpos, source.getBlockState().get(DispenserBlock.FACING).getOpposite())) {
						blockstate.catchFire(world, blockpos, source.getBlockState().get(DispenserBlock.FACING).getOpposite(), null);
						if (blockstate.getBlock() instanceof TNTBlock) {
							world.removeBlock(blockpos, false);
						}
					}
					else {
						this.successful = false;
					}

					if (this.successful && stack.attemptDamageItem(1, world.rand, (ServerPlayerEntity)null)) {
						stack.setCount(0);
					}
				}

				return stack;
			}
		});
	}
	
	protected void registerAxeStrippingBlocks() {
		AxeItem.BLOCK_STRIPPING_MAP = ImmutableMap.<Block, Block>builder()
			.putAll(AxeItem.BLOCK_STRIPPING_MAP)
			.put(AetherBlocks.SKYROOT_LOG, AetherBlocks.STRIPPED_SKYROOT_LOG)
			.put(AetherBlocks.GOLDEN_OAK_LOG, AetherBlocks.STRIPPED_GOLDEN_OAK_LOG)
			.put(AetherBlocks.SKYROOT_WOOD, AetherBlocks.STRIPPED_SKYROOT_WOOD)
			.put(AetherBlocks.GOLDEN_OAK_WOOD, AetherBlocks.STRIPPED_GOLDEN_OAK_WOOD)
			.build();
	}
	
	@SubscribeEvent
	public void checkBlockBanned(PlayerInteractEvent.RightClickBlock event) {
		PlayerEntity player = event.getPlayer();
		
		if (player.dimension != AetherDimensions.THE_AETHER) {
			return;
		}
		
		if (event.getWorld().getBlockState(event.getPos()).isIn(BlockTags.BEDS)) {
			event.setCanceled(true);
			return;
		}

		if (event.getItemStack().getItem().isIn(AetherItemTags.BANNED_IN_AETHER)) {
			if (AetherEventHooks.isItemBanned(event.getItemStack())) {
				AetherEventHooks.onItemBanned(event.getWorld(), event.getPos(), event.getFace(), event.getItemStack());			
				event.setCanceled(true);
			}
			return;
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBlockBanned(AetherBannedItemEvent.SpawnParticles event) {
		IWorld world = event.getWorld();
		double x, y, z;
		x = event.getPos().getX() + 0.5;
		y = event.getPos().getY() + 1;
		z = event.getPos().getZ() + 0.5;
		for (int i = 0; i < 10; i++) {
			world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, 0.0, 0.0); 
		}
		world.playSound(null, event.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
	}
	
	@SubscribeEvent
	public void doSkyrootDoubleDrops(LivingDropsEvent event) {
		if (!(event.getSource() instanceof EntityDamageSource)) {
			return;
		}
		
		LivingEntity entity = event.getEntityLiving();
		EntityDamageSource source = (EntityDamageSource) event.getSource();
		
		if (!(source.getImmediateSource() instanceof PlayerEntity)) {
			return;
		}
		
		PlayerEntity player = (PlayerEntity) source.getImmediateSource();
		ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
		Item item = stack.getItem();
		
		if (item == AetherItems.SKYROOT_SWORD && !entity.getType().isContained(AetherEntityTypeTags.NO_SKYROOT_DOUBLE_DROPS)) {
			ArrayList<ItemEntity> newDrops = new ArrayList<>(event.getDrops().size());
			for (ItemEntity drop : event.getDrops()) {
				ItemStack droppedStack = drop.getItem();
				if (!droppedStack.getItem().isIn(AetherItemTags.NO_SKYROOT_DOUBLE_DROPS)) {
					newDrops.add(new ItemEntity(entity.world, drop.getPosX(), drop.getPosY(), drop.getPosZ(), droppedStack.copy()));
				}
			}
			event.getDrops().addAll(newDrops);
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		IAetherPlayer original = event.getOriginal().getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY).orElseThrow(() -> new IllegalStateException("Player " + event.getOriginal().getName().getUnformattedComponentText() + " has no AetherPlayer capability!"));
		IAetherPlayer newPlayer = event.getPlayer().getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY).orElseThrow(() -> new IllegalStateException("Player " + event.getOriginal().getName().getUnformattedComponentText() + " has no AetherPlayer capability!"));
		
		newPlayer.copyFrom(original);
	}
	
	private Map<Item, Integer> burnTimes = null;
	
	protected Map<Item, Integer> getBurnTimes() {
		Map<Item, Integer> map = this.burnTimes;
		if (map != null) {
			return map;
		}
		addItemBurnTime(map, AetherBlocks.SKYROOT_BOOKSHELF, 300);
		addItemBurnTime(map, AetherItems.SKYROOT_SHOVEL, 200);
		addItemBurnTime(map, AetherItems.SKYROOT_SWORD, 200);
		addItemBurnTime(map, AetherItems.SKYROOT_AXE, 200);
		addItemBurnTime(map, AetherItems.SKYROOT_PICKAXE, 200);
		addItemBurnTime(map, AetherItems.SKYROOT_STICK, 100);
		addItemBurnTime(map, AetherItems.SKYROOT_BUCKET, 100);
		addItemBurnTime(map, AetherBlocks.BERRY_BUSH_STEM, 100);
		return this.burnTimes = map;
	}
	
	@SubscribeEvent
	public void onGetItemBurnTime(FurnaceFuelBurnTimeEvent event) {
		Item item = event.getItemStack().getItem();
		Integer burnTime = getBurnTimes().get(item);
		if (burnTime != null) {
			event.setBurnTime(burnTime);
		}
	}
	
//	@SubscribeEvent
//	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//		PlayerEntity player = event.getPlayer();
//		ItemStack itemStack = event.getItemStack();
//		BlockPos pos = event.getPos();
//		World world = event.getWorld();
//		BlockState blockstate = world.getBlockState(pos);
//		Block block = blockstate.getBlock();
//		
//		if (itemStack.getItem() instanceof AxeItem) {
//			if (block == AetherBlocks.SKYROOT_LOG) {
//				
//				event.setCanceled(true);
//				event.setCancellationResult(ActionResultType.SUCCESS);
//			}
//		}
//	}
	
}
