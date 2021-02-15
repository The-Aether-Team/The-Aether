package com.aether.registry;

import com.aether.Aether;

import com.aether.block.construction.*;
import com.aether.block.dungeon.ChestMimicBlock;
import com.aether.block.dungeon.TrappedBlock;
import com.aether.block.dungeon.TreasureChestBlock;
import com.aether.block.miscellaneous.AetherPortalBlock;
import com.aether.block.miscellaneous.PresentBlock;
import com.aether.block.natural.*;
import com.aether.block.util.*;
import com.aether.block.utility.EnchanterBlock;
import com.aether.block.utility.FreezerBlock;
import com.aether.block.utility.IncubatorBlock;
import com.aether.block.utility.SunAltarBlock;
import com.aether.client.ClientProxy;
import com.aether.item.block.TintedBlockItem;
import com.aether.world.gen.tree.GoldenOakTree;
import com.aether.world.gen.tree.SkyrootTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Aether.MODID);

	public static final RegistryObject<AetherPortalBlock> AETHER_PORTAL = BLOCKS.register("aether_portal", () -> new AetherPortalBlock(AbstractBlock.Properties.from(Blocks.NETHER_PORTAL)));

	public static final RegistryObject<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", () -> new AetherGrassBlock(AbstractBlock.Properties.from(Blocks.GRASS_BLOCK)));
	public static final RegistryObject<Block> ENCHANTED_AETHER_GRASS_BLOCK  = register("enchanted_aether_grass_block", () -> new EnchantedAetherGrassBlock(AbstractBlock.Properties.from(Blocks.GRASS_BLOCK)));
	public static final RegistryObject<Block> AETHER_DIRT = register("aether_dirt", () -> new DefaultAetherDoubleDropBlock(AbstractBlock.Properties.from(Blocks.DIRT)));
	public static final RegistryObject<Block> QUICKSOIL = register("quicksoil", () -> new DefaultAetherDoubleDropBlock(AbstractBlock.Properties.from(Blocks.SAND).slipperiness(1.1F)));
	public static final RegistryObject<Block> HOLYSTONE = register("holystone", () -> new DefaultAetherDoubleDropBlock(AbstractBlock.Properties.from(Blocks.STONE).hardnessAndResistance(0.5F)));
	public static final RegistryObject<Block> MOSSY_HOLYSTONE = register("mossy_holystone", () -> new DefaultAetherDoubleDropBlock(AbstractBlock.Properties.from(Blocks.STONE).hardnessAndResistance(0.5F)));

	public static final RegistryObject<Block> COLD_AERCLOUD = register("cold_aercloud",
			() -> new AercloudBlock(AbstractBlock.Properties.create(Material.ICE).hardnessAndResistance(0.2F).sound(SoundType.CLOTH).notSolid()));
	public static final RegistryObject<TintedAercloudBlock> BLUE_AERCLOUD = register("blue_aercloud",
			() -> new BouncyAercloudBlock(TintedAercloudBlock.COLOR_BLUE_OLD, TintedAercloudBlock.COLOR_BLUE_NEW, AbstractBlock.Properties.create(Material.ICE).hardnessAndResistance(0.2F).sound(SoundType.CLOTH).notSolid()));
	public static final RegistryObject<TintedAercloudBlock> GOLDEN_AERCLOUD = register("golden_aercloud",
			() -> new TintedAercloudBlock(TintedAercloudBlock.COLOR_GOLDEN_OLD, TintedAercloudBlock.COLOR_GOLDEN_NEW, AbstractBlock.Properties.create(Material.ICE).hardnessAndResistance(0.2F).sound(SoundType.CLOTH).notSolid()));
	public static final RegistryObject<Block> PINK_AERCLOUD = register("pink_aercloud",
			() -> new HealingAercloudBlock(AbstractBlock.Properties.create(Material.ICE).hardnessAndResistance(0.2F).sound(SoundType.CLOTH).notSolid()));

	public static final RegistryObject<Block> ICESTONE = register("icestone",
			() -> new IcestoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0F).tickRandomly().sound(SoundType.GLASS)));
	public static final RegistryObject<Block> AMBROSIUM_ORE = register("ambrosium_ore",
			() -> new AetherDoubleDropsOreBlock(0, 2, AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
	public static final RegistryObject<Block> ZANITE_ORE = register("zanite_ore",
			() -> new AetherOreBlock(3, 5, AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1)));
	public static final RegistryObject<Block> GRAVITITE_ORE = register("gravitite_ore",
			() -> new FloatingBlock(false, AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(5.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(2)));

	public static final RegistryObject<Block> SKYROOT_LEAVES = register("skyroot_leaves", () -> new LeavesBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block> GOLDEN_OAK_LEAVES = register("golden_oak_leaves",
			() -> new LeavesWithParticlesBlock(0.976F, 0.7450980392156863F, 0.0F, AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block> CRYSTAL_LEAVES = register("crystal_leaves",
			() -> new LeavesWithParticlesBlock(0.0F, 0.6450980392156863F, 0.7450980392156863F, AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block> CRYSTAL_FRUIT_LEAVES = register("crystal_fruit_leaves",
			() -> new LeavesWithParticlesBlock(0.0F, 0.6450980392156863F, 0.7450980392156863F, AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block> HOLIDAY_LEAVES = register("holiday_leaves",
			() -> new LeavesWithParticlesBlock(1.0F, 1.0F, 1.0F, AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block> DECORATED_HOLIDAY_LEAVES = register("decorated_holiday_leaves",
			() -> new LeavesWithParticlesBlock(1.0F, 1.0F, 1.0F, AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));

	public static final RegistryObject<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", () -> new AetherLogBlock(AbstractBlock.Properties.from(Blocks.OAK_LOG)));
	public static final RegistryObject<RotatedPillarBlock> GOLDEN_OAK_LOG = register("golden_oak_log", () -> new AetherLogBlock(AbstractBlock.Properties.from(Blocks.OAK_LOG)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = register("stripped_skyroot_log", () -> new AetherLogBlock(AbstractBlock.Properties.from(Blocks.STRIPPED_OAK_LOG)));
	public static final RegistryObject<RotatedPillarBlock> SKYROOT_WOOD = register("skyroot_wood", () -> new AetherDoubleDropsRotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_WOOD)));
	public static final RegistryObject<RotatedPillarBlock> GOLDEN_OAK_WOOD = register("golden_oak_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = register("stripped_skyroot_wood", () -> new AetherDoubleDropsRotatedPillarBlock(AbstractBlock.Properties.from(Blocks.STRIPPED_OAK_WOOD)));

	public static final RegistryObject<Block> SKYROOT_PLANKS = register("skyroot_planks", () -> new Block(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
	public static final RegistryObject<Block> HOLYSTONE_BRICKS = register("holystone_bricks", () -> new AetherGrassBlock(AbstractBlock.Properties.from(Blocks.STONE_BRICKS).hardnessAndResistance(0.5F, 10.0F)));
	public static final RegistryObject<Block> QUICKSOIL_GLASS = register("quicksoil_glass", () -> new GlassBlock(AbstractBlock.Properties.from(Blocks.GLASS).slipperiness(1.1F).setLightLevel((state) -> 11)));
	public static final RegistryObject<Block> AEROGEL = register("aerogel", () -> new AerogelBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 2000.0F).sound(SoundType.METAL).notSolid()));

	public static final RegistryObject<Block> ZANITE_BLOCK = register("zanite_block", () -> new Block(AbstractBlock.Properties.from(Blocks.IRON_BLOCK)));
	public static final RegistryObject<Block> ENCHANTED_GRAVITITE = register("enchanted_gravitite", () -> new FloatingBlock(true, AbstractBlock.Properties.from(Blocks.IRON_BLOCK)));

	public static final RegistryObject<Block> ENCHANTER = register("enchanter",
			() -> new EnchanterBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> FREEZER = register("freezer",
			() -> new FreezerBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> INCUBATOR = register("incubator",
			() -> new IncubatorBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.0F).sound(SoundType.STONE)));

	public static final RegistryObject<Block> AMBROSIUM_WALL_TORCH = BLOCKS.register("ambrosium_wall_torch", () -> new WallTorchBlock(AbstractBlock.Properties.from(Blocks.WALL_TORCH), ParticleTypes.FLAME));
	public static final RegistryObject<Block> AMBROSIUM_TORCH = register("ambrosium_torch", () -> new TorchBlock(AbstractBlock.Properties.from(Blocks.TORCH), ParticleTypes.FLAME));

	public static final RegistryObject<Block> BERRY_BUSH = register("berry_bush", () -> new BerryBushBlock(AbstractBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.2F).sound(SoundType.PLANT)));
	public static final RegistryObject<Block> BERRY_BUSH_STEM = register("berry_bush_stem", () -> new BerryBushStemBlock(AbstractBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.2F).sound(SoundType.PLANT)));

	public static final RegistryObject<Block> PURPLE_FLOWER = register("purple_flower", () -> new FlowerBlock(Effects.SATURATION, 7, AbstractBlock.Properties.from(Blocks.DANDELION)));
	public static final RegistryObject<Block> WHITE_FLOWER = register("white_flower", () -> new FlowerBlock(Effects.SATURATION, 7, AbstractBlock.Properties.from(Blocks.DANDELION)));
	public static final RegistryObject<FlowerPotBlock> POTTED_PURPLE_FLOWER = BLOCKS.register("potted_purple_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PURPLE_FLOWER, AbstractBlock.Properties.from(Blocks.FLOWER_POT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_WHITE_FLOWER = BLOCKS.register("potted_white_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHITE_FLOWER, AbstractBlock.Properties.from(Blocks.FLOWER_POT)));

	public static final RegistryObject<SaplingBlock> SKYROOT_SAPLING = register("skyroot_sapling",
			() -> new SaplingBlock(new SkyrootTree(), AbstractBlock.Properties.from(Blocks.OAK_SAPLING)));
	public static final RegistryObject<SaplingBlock> GOLDEN_OAK_SAPLING = register("golden_oak_sapling",
			() -> new SaplingBlock(new GoldenOakTree(), AbstractBlock.Properties.from(Blocks.OAK_SAPLING)));

	public static final RegistryObject<Block> CARVED_STONE = register("carved_stone",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> SENTRY_STONE = register("sentry_stone",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).setLightLevel((state) -> 11).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ANGELIC_STONE = register("angelic_stone",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> LIGHT_ANGELIC_STONE = register("light_angelic_stone",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).setLightLevel((state) -> 11).sound(SoundType.STONE)));
	public static final RegistryObject<Block> HELLFIRE_STONE  = register("hellfire_stone",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> LIGHT_HELLFIRE_STONE = register("light_hellfire_stone",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).setLightLevel((state) -> 11).sound(SoundType.STONE)));

	public static final RegistryObject<Block> LOCKED_CARVED_STONE = register("locked_carved_stone", () -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> LOCKED_SENTRY_STONE = register("locked_sentry_stone", () -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));
	public static final RegistryObject<Block> LOCKED_ANGELIC_STONE = register("locked_angelic_stone", () -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> LOCKED_LIGHT_ANGELIC_STONE = register("locked_light_angelic_stone", () -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));
	public static final RegistryObject<Block> LOCKED_HELLFIRE_STONE = register("locked_hellfire_stone", () -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> LOCKED_LIGHT_HELLFIRE_STONE = register("locked_light_hellfire_stone", () -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));

	public static final RegistryObject<Block> TRAPPED_CARVED_STONE = register("trapped_carved_stone",
			() -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> LOCKED_CARVED_STONE.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> TRAPPED_SENTRY_STONE = register("trapped_sentry_stone",
			() -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> LOCKED_SENTRY_STONE.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));
	public static final RegistryObject<Block> TRAPPED_ANGELIC_STONE = register("trapped_angelic_stone",
			() -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK)));
	//new TrappedBlock(() -> AetherEntityTypes.VALKYRIE, () -> LOCKED_ANGELIC_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> TRAPPED_LIGHT_ANGELIC_STONE = register("trapped_light_angelic_stone",
			() -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));
	//new TrappedBlock(() -> AetherEntityTypes.VALKYRIE, () -> LOCKED_LIGHT_ANGELIC_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));
	public static final RegistryObject<Block> TRAPPED_HELLFIRE_STONE = register("trapped_hellfire_stone",
			() -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK)));
	//new TrappedBlock(() -> AetherEntityTypes.FIRE_MINION, () -> LOCKED_HELLFIRE_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> TRAPPED_LIGHT_HELLFIRE_STONE = register("trapped_light_hellfire_stone",
			() -> new Block(AbstractBlock.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));
	//new TrappedBlock(() -> AetherEntityTypes.FIRE_MINION, () -> LOCKED_LIGHT_HELLFIRE_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));

	public static final RegistryObject<Block> CHEST_MIMIC = register("chest_mimic", () -> new ChestMimicBlock(AbstractBlock.Properties.from(Blocks.CHEST)));
	public static final RegistryObject<Block> TREASURE_CHEST = register("treasure_chest", () -> new TreasureChestBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F)));

	public static final RegistryObject<Block> PILLAR = register("pillar",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.QUARTZ_PILLAR).hardnessAndResistance(0.5F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> PILLAR_TOP = register("pillar_top",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.QUARTZ_PILLAR).hardnessAndResistance(0.5F).sound(SoundType.METAL)));

	public static final RegistryObject<Block> PRESENT = register("present",
			() -> new PresentBlock(AbstractBlock.Properties.create(Material.ORGANIC).hardnessAndResistance(0.6F).sound(SoundType.PLANT)));

	public static final RegistryObject<FenceBlock> SKYROOT_FENCE = register("skyroot_fence", () -> new FenceBlock(AbstractBlock.Properties.from(Blocks.OAK_FENCE)));
	public static final RegistryObject<FenceGateBlock> SKYROOT_FENCE_GATE = register("skyroot_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.from(Blocks.OAK_FENCE_GATE)));

	public static final RegistryObject<WallBlock> CARVED_WALL = register("carved_wall",
			() -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<WallBlock> ANGELIC_WALL = register("angelic_wall",
			() -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<WallBlock> HELLFIRE_WALL = register("hellfire_wall",
			() -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<WallBlock> HOLYSTONE_WALL = register("holystone_wall",
			() -> new WallBlock(AbstractBlock.Properties.from(Blocks.STONE).hardnessAndResistance(0.5F)));
	public static final RegistryObject<WallBlock> MOSSY_HOLYSTONE_WALL = register("mossy_holystone_wall",
			() -> new WallBlock(AbstractBlock.Properties.from(Blocks.STONE).hardnessAndResistance(0.5F)));
	public static final RegistryObject<WallBlock> HOLYSTONE_BRICK_WALL = register("holystone_brick_wall",
			() -> new WallBlock(AbstractBlock.Properties.from(Blocks.STONE).hardnessAndResistance(0.5F)));
	public static final RegistryObject<WallBlock> AEROGEL_WALL = register("aerogel_wall",
			() -> new AerogelWallBlock(AbstractBlock.Properties.from(Blocks.STONE).hardnessAndResistance(0.5F, 2000.0F).notSolid()));

	public static final RegistryObject<StairsBlock> SKYROOT_STAIRS = register("skyroot_stairs",
			() -> new StairsBlock(() -> SKYROOT_PLANKS.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
	public static final RegistryObject<StairsBlock> CARVED_STAIRS = register("carved_stairs",
			() -> new StairsBlock(() -> CARVED_STONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<StairsBlock> ANGELIC_STAIRS = register("angelic_stairs",
			() -> new StairsBlock(() -> ANGELIC_STONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<StairsBlock> HELLFIRE_STAIRS = register("hellfire_stairs",
			() -> new StairsBlock(() -> HELLFIRE_STONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE)));
	public static final RegistryObject<StairsBlock> HOLYSTONE_STAIRS = register("holystone_stairs",
			() -> new StairsBlock(() -> HOLYSTONE.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.STONE).hardnessAndResistance(0.5F)));
	public static final RegistryObject<StairsBlock> MOSSY_HOLYSTONE_STAIRS = register("mossy_holystone_stairs",
			() -> new StairsBlock(() -> MOSSY_HOLYSTONE.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.STONE).hardnessAndResistance(0.5F)));
	public static final RegistryObject<StairsBlock> HOLYSTONE_BRICK_STAIRS = register("holystone_brick_stairs",
			() -> new StairsBlock(() -> HOLYSTONE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.STONE_BRICKS).hardnessAndResistance(0.5F, 10.0F)));
	public static final RegistryObject<StairsBlock> AEROGEL_STAIRS = register("aerogel_stairs",
			() -> new AerogelStairsBlock(() -> AEROGEL.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 2000.0F).sound(SoundType.METAL).notSolid()));

	public static final RegistryObject<SlabBlock> SKYROOT_SLAB = register("skyroot_slab",
			() -> new SlabBlock(AbstractBlock.Properties.from(Blocks.OAK_SLAB)));
	public static final RegistryObject<SlabBlock> CARVED_SLAB = register("carved_slab",
			() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(2.0F, 5.0F)));
	public static final RegistryObject<SlabBlock> ANGELIC_SLAB = register("angelic_slab",
			() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(2.0F, 5.0F)));
	public static final RegistryObject<SlabBlock> HELLFIRE_SLAB = register("hellfire_slab",
			() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(2.0F, 5.0F)));
	public static final RegistryObject<SlabBlock> HOLYSTONE_SLAB = register("holystone_slab",
			() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(2.0F, 5.0F)));
	public static final RegistryObject<SlabBlock> MOSSY_HOLYSTONE_SLAB = register("mossy_holystone_slab",
			() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(2.0F, 5.0F)));
	public static final RegistryObject<SlabBlock> HOLYSTONE_BRICK_SLAB = register("holystone_brick_slab",
			() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(2.0F, 5.0F)));
	public static final RegistryObject<SlabBlock> AEROGEL_SLAB = register("aerogel_slab",
			() -> new AerogelSlabBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 2000.0F).sound(SoundType.METAL).notSolid()));

	public static final RegistryObject<Block> SUN_ALTAR = register("sun_altar",
			() -> new SunAltarBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).sound(SoundType.METAL)));

	public static final RegistryObject<Block> SKYROOT_BOOKSHELF = register("skyroot_bookshelf", () -> new BookshelfBlock(AbstractBlock.Properties.from(Blocks.BOOKSHELF)));

	private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
		RegistryObject<T> register = BLOCKS.register(name, block);
		AetherItems.ITEMS.register(name, item.apply(register));
		return register;
	}

	private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
		return (RegistryObject<B>) baseRegister(name, block, AetherBlocks::registerBlockItem);
	}

	private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
		return () ->
		{
			if (Objects.requireNonNull(block.get()) instanceof IAetherBlockColor) {
				IAetherBlockColor iaetherblockcolor = (IAetherBlockColor) Objects.requireNonNull(block.get());
				int hexColor = iaetherblockcolor.getColor(false);
				int updatedHexColor = iaetherblockcolor.getColor(true);
				return new TintedBlockItem(hexColor, updatedHexColor, Objects.requireNonNull(block.get()), new Item.Properties().group(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (Objects.requireNonNull(block.get()) == AMBROSIUM_TORCH.get()) {
				return new WallOrFloorItem(AMBROSIUM_TORCH.get(), AMBROSIUM_WALL_TORCH.get(), new Item.Properties().group(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (Objects.requireNonNull(block.get()) == CHEST_MIMIC.get()) {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().group(AetherItemGroups.AETHER_BLOCKS).setISTER(() -> ClientProxy::chestMimicRenderer));
			}
			else if (Objects.requireNonNull(block.get()) == TREASURE_CHEST.get()) {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().group(AetherItemGroups.AETHER_BLOCKS).setISTER(() -> ClientProxy::treasureChestRenderer));
			}
			else {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().group(AetherItemGroups.AETHER_BLOCKS));
			}
		};
	}
}