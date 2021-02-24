package com.aether;

import com.aether.advancement.AetherAdvancements;
import com.aether.capability.AetherCapabilities;
import com.aether.client.AetherRendering;

import com.aether.data.*;
import com.aether.data.AetherLootTables;
import com.aether.loot.functions.DoubleDrops;
import com.aether.network.AetherPacketHandler;
import com.aether.registry.*;
import com.aether.world.dimension.AetherDimensions;
import com.aether.world.gen.feature.AetherFeatures;
import net.minecraft.block.*;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.data.DataGenerator;
import net.minecraft.dispenser.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.Random;

@Mod(Aether.MODID)
public class Aether
{
	public static final String MODID = "aether";
	public static final Logger LOGGER = LogManager.getLogger();
	
	public Aether() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::curiosSetup);
		modEventBus.addListener(this::dataSetup);

		DeferredRegister<?>[] registers = {
				AetherBlocks.BLOCKS,
				AetherFeatures.FEATURES,
				AetherEntityTypes.ENTITIES,
				AetherItems.ITEMS,
				AetherParticleTypes.PARTICLES,
				AetherPOI.POI,
				AetherSoundEvents.SOUNDS,
				AetherContainerTypes.CONTAINERS,
				AetherTileEntityTypes.TILE_ENTITIES,
		};

		for (DeferredRegister<?> register : registers) {
			register.register(modEventBus);
		}

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AetherConfig.COMMON_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AetherConfig.CLIENT_SPEC);
	}

	public void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AetherPacketHandler.register();
			AetherCapabilities.register();

			AetherBlocks.registerPots();
			AetherBlocks.registerAxeStrippingBlocks();

			AetherEntityTypes.registerSpawnPlacements();
			AetherEntityTypes.registerEntityAttributes();

			registerDispenserBehaviors();
			registerComposting();

			AetherFeatures.registerConfiguredFeatures();
			AetherAdvancements.init();
		});
	}

	public void clientSetup(FMLClientSetupEvent event) {
		AetherRendering.registerBlockRenderLayers();
		AetherRendering.registerEntityRenderers(event);
		AetherRendering.registerTileEntityRenderers();
		AetherRendering.registerGuiFactories();
		AetherRendering.registerItemModelProperties();

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

	public void curiosSetup(InterModEnqueueEvent event)
	{
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().build());
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
		//InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("shield").build());
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().build());
		//InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("misc").size(2).build());
		//.setIcon(Resource Location(MODID, "textures/slots/pendant.png
	}

	public void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();
		if (event.includeClient()) {
			generator.addProvider(new AetherBlockStates(generator, helper));
			generator.addProvider(new AetherItemModels(generator, helper));
		}
		if (event.includeServer()) {
			generator.addProvider(new AetherRecipes(generator));
			generator.addProvider(new AetherLootTables(generator));
			AetherBlockTags blockTags = new AetherBlockTags(generator, helper);
			generator.addProvider(blockTags);
			generator.addProvider(new AetherItemTags(generator, blockTags, helper));
			generator.addProvider(new AetherEntityTags(generator, helper));
		}
	}

	private void registerDispenserBehaviors() {
		IDispenseItemBehavior dispenseSpawnEgg = new DefaultDispenseItemBehavior() {
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> entitytype = ((SpawnEggItem)stack.getItem()).getType(stack.getTag());
				entitytype.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		};

		for (RegistryObject<Item> item : AetherItems.ITEMS.getEntries())
		{
			if (item.get() instanceof SpawnEggItem)
			{
				DispenserBlock.registerDispenseBehavior(item.get(), dispenseSpawnEgg);
			}
		}

		DispenserBlock.registerDispenseBehavior(Items.FIRE_CHARGE, new OptionalDispenseBehavior() {
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				if (world.getDimensionKey() == AetherDimensions.AETHER_WORLD) {
					this.setSuccessful(false);
				}
				else {
					this.setSuccessful(true);
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

			@Override
			protected void playDispenseSound(IBlockSource source) {
				source.getWorld().playEvent(this.isSuccessful()? 1018 : 1001, source.getBlockPos(), 0);
			}
		});
		DispenserBlock.registerDispenseBehavior(Items.FLINT_AND_STEEL, new OptionalDispenseBehavior() {
			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				if (world.getDimensionKey() == AetherDimensions.AETHER_WORLD) {
					this.setSuccessful(false);
				}
				else {
					this.setSuccessful(true);
					BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
					BlockState blockstate = world.getBlockState(blockpos);
					Direction direction = source.getBlockState().get(DispenserBlock.FACING);
					if (AbstractFireBlock.canLightBlock(world, blockpos, direction)) {
						world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
					}
					else if (CampfireBlock.canBeLit(blockstate)) {
						world.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, true));
					}
					else if (blockstate.isFlammable(world, blockpos, source.getBlockState().get(DispenserBlock.FACING).getOpposite())) {
						blockstate.catchFire(world, blockpos, source.getBlockState().get(DispenserBlock.FACING).getOpposite(), null);
						if (blockstate.getBlock() instanceof TNTBlock) {
							world.removeBlock(blockpos, false);
						}
					}
					else {
						this.setSuccessful(false);
					}

					if (this.isSuccessful() && stack.attemptDamageItem(1, world.rand, null)) {
						stack.setCount(0);
					}
				}

				return stack;
			}
		});
	}

	private void registerComposting() {
		ComposterBlock.registerCompostable(0.3F, AetherBlocks.SKYROOT_LEAVES.get());
		ComposterBlock.registerCompostable(0.3F, AetherBlocks.SKYROOT_SAPLING.get());
		ComposterBlock.registerCompostable(0.3F, AetherBlocks.GOLDEN_OAK_LEAVES.get());
		ComposterBlock.registerCompostable(0.3F, AetherBlocks.GOLDEN_OAK_SAPLING.get());
		ComposterBlock.registerCompostable(0.3F, AetherBlocks.CRYSTAL_LEAVES.get());
		ComposterBlock.registerCompostable(0.3F, AetherBlocks.CRYSTAL_FRUIT_LEAVES.get());
		ComposterBlock.registerCompostable(0.3F, AetherBlocks.HOLIDAY_LEAVES.get());
		ComposterBlock.registerCompostable(0.3F, AetherBlocks.DECORATED_HOLIDAY_LEAVES.get());
		ComposterBlock.registerCompostable(0.3F, AetherItems.BLUE_BERRY.get());
		ComposterBlock.registerCompostable(0.5F, AetherItems.ENCHANTED_BERRY.get());
		ComposterBlock.registerCompostable(0.5F, AetherBlocks.BERRY_BUSH.get());
		ComposterBlock.registerCompostable(0.5F, AetherBlocks.BERRY_BUSH_STEM.get());
		ComposterBlock.registerCompostable(0.65F, AetherBlocks.WHITE_FLOWER.get());
		ComposterBlock.registerCompostable(0.65F, AetherBlocks.PURPLE_FLOWER.get());
		ComposterBlock.registerCompostable(0.65F, AetherItems.WHITE_APPLE.get());
	}
}
