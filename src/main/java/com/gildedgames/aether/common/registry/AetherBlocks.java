package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.block.construction.*;
import com.gildedgames.aether.common.block.dungeon.ChestMimicBlock;
import com.gildedgames.aether.common.block.dungeon.TrappedBlock;
import com.gildedgames.aether.common.block.dungeon.TreasureChestBlock;
import com.gildedgames.aether.common.block.miscellaneous.AetherPortalBlock;
import com.gildedgames.aether.common.block.util.*;
import com.gildedgames.aether.client.AetherRendering;
import com.gildedgames.aether.common.item.block.BurnableBlockItem;
import com.gildedgames.aether.common.world.gen.tree.GoldenOakTree;
import com.gildedgames.aether.common.world.gen.tree.SkyrootTree;
import com.gildedgames.aether.common.block.natural.*;
import com.gildedgames.aether.common.block.utility.*;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
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

	public static final RegistryObject<AetherPortalBlock> AETHER_PORTAL = BLOCKS.register("aether_portal", () -> new AetherPortalBlock(AbstractBlock.Properties.copy(Blocks.NETHER_PORTAL)));

	public static final RegistryObject<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", () -> new AetherGrassBlock(AbstractBlock.Properties.copy(Blocks.GRASS_BLOCK).harvestTool(ToolType.SHOVEL)));
	public static final RegistryObject<Block> ENCHANTED_AETHER_GRASS_BLOCK  = register("enchanted_aether_grass_block", () -> new EnchantedAetherGrassBlock(AbstractBlock.Properties.copy(Blocks.GRASS_BLOCK).harvestTool(ToolType.SHOVEL)));
	public static final RegistryObject<Block> AETHER_DIRT = register("aether_dirt", () -> new AetherDoubleDropBlock(AbstractBlock.Properties.copy(Blocks.DIRT).harvestTool(ToolType.SHOVEL)));
	public static final RegistryObject<Block> QUICKSOIL = register("quicksoil", () -> new AetherDoubleDropBlock(AbstractBlock.Properties.copy(Blocks.SAND).harvestTool(ToolType.SHOVEL).friction(1.1F)));
	public static final RegistryObject<Block> HOLYSTONE = register("holystone", () -> new AetherDoubleDropBlock(AbstractBlock.Properties.copy(Blocks.STONE).strength(0.5F, 6.0f).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> MOSSY_HOLYSTONE = register("mossy_holystone", () -> new AetherDoubleDropBlock(AbstractBlock.Properties.copy(AetherBlocks.HOLYSTONE.get())));

	public static final RegistryObject<Block> COLD_AERCLOUD = register("cold_aercloud",
			() -> new AercloudBlock(AbstractBlock.Properties.of(Material.ICE).strength(0.2F).sound(SoundType.WOOL).harvestTool(ToolType.HOE).noOcclusion()));
	public static final RegistryObject<Block> BLUE_AERCLOUD = register("blue_aercloud", () -> new BlueAercloudBlock(AbstractBlock.Properties.copy(AetherBlocks.COLD_AERCLOUD.get())));
	public static final RegistryObject<Block> GOLDEN_AERCLOUD = register("golden_aercloud", () -> new AercloudBlock(AbstractBlock.Properties.copy(AetherBlocks.COLD_AERCLOUD.get())));
	public static final RegistryObject<Block> PINK_AERCLOUD = register("pink_aercloud", () -> new PinkAercloudBlock(AbstractBlock.Properties.copy(AetherBlocks.COLD_AERCLOUD.get())));

	public static final RegistryObject<Block> ICESTONE = register("icestone",
			() -> new IcestoneBlock(AbstractBlock.Properties.of(Material.STONE).strength(3.0F).randomTicks().sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> AMBROSIUM_ORE = register("ambrosium_ore",
			() -> new AetherDoubleDropsOreBlock(0, 2, AbstractBlock.Properties.of(Material.STONE).strength(3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> ZANITE_ORE = register("zanite_ore",
			() -> new AetherOreBlock(3, 5, AbstractBlock.Properties.of(Material.STONE).strength(3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> GRAVITITE_ORE = register("gravitite_ore",
			() -> new FloatingBlock(false, AbstractBlock.Properties.of(Material.STONE).strength(5.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(2).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> SKYROOT_LEAVES = register("skyroot_leaves", () -> new AetherDoubleDropsLeaves(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).harvestTool(ToolType.HOE)
			.isValidSpawn((state, reader, pos, entity) -> (entity == EntityType.OCELOT || entity == EntityType.PARROT)).isSuffocating((state, reader, pos) -> false).isViewBlocking((state, reader, pos) -> false)));
	public static final RegistryObject<Block> GOLDEN_OAK_LEAVES = register("golden_oak_leaves",
			() -> new LeavesWithParticlesBlock(AetherParticleTypes.GOLDEN_OAK_LEAVES, AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).harvestTool(ToolType.HOE)));
	public static final RegistryObject<Block> CRYSTAL_LEAVES = register("crystal_leaves",
			() -> new LeavesWithParticlesBlock(AetherParticleTypes.CRYSTAL_LEAVES, AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).harvestTool(ToolType.HOE)));
	public static final RegistryObject<Block> CRYSTAL_FRUIT_LEAVES = register("crystal_fruit_leaves",
			() -> new LeavesWithParticlesBlock(AetherParticleTypes.CRYSTAL_LEAVES, AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).harvestTool(ToolType.HOE)));
	public static final RegistryObject<Block> HOLIDAY_LEAVES = register("holiday_leaves",
			() -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).harvestTool(ToolType.HOE)));
	public static final RegistryObject<Block> DECORATED_HOLIDAY_LEAVES = register("decorated_holiday_leaves",
			() -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).harvestTool(ToolType.HOE)));

	public static final RegistryObject<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", () -> new AetherLogBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> GOLDEN_OAK_LOG = register("golden_oak_log", () -> new AetherLogBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = register("stripped_skyroot_log", () -> new AetherLogBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> SKYROOT_WOOD = register("skyroot_wood", () -> new AetherDoubleDropsRotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> GOLDEN_OAK_WOOD = register("golden_oak_wood", () -> new AetherDoubleDropsRotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = register("stripped_skyroot_wood", () -> new AetherDoubleDropsRotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD).harvestTool(ToolType.AXE)));

	public static final RegistryObject<Block> SKYROOT_PLANKS = register("skyroot_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> HOLYSTONE_BRICKS = register("holystone_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).strength(0.5F, 10.0F).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> QUICKSOIL_GLASS = register("quicksoil_glass", () -> new GlassBlock(AbstractBlock.Properties.copy(Blocks.GLASS).friction(1.1F).lightLevel((state) -> 11)
			.isValidSpawn((state, reader, pos, entity) -> false).isRedstoneConductor((state, reader, pos) -> false).isSuffocating((state, reader, pos) -> false).isViewBlocking((state, reader, pos) -> false)));
	public static final RegistryObject<Block> AEROGEL = register("aerogel", () -> new AerogelBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.0F, 2000.0F).sound(SoundType.METAL).noOcclusion().harvestTool(ToolType.PICKAXE).harvestLevel(3).requiresCorrectToolForDrops()
			.isViewBlocking((state, reader, pos) -> false)));

	public static final RegistryObject<Block> ZANITE_BLOCK = register("zanite_block", () -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> ENCHANTED_GRAVITITE = register("enchanted_gravitite", () -> new FloatingBlock(true, AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> ALTAR = register("altar",
			() -> new AltarBlock(AbstractBlock.Properties.of(Material.STONE).strength(2.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> FREEZER = register("freezer",
			() -> new FreezerBlock(AbstractBlock.Properties.of(Material.STONE).strength(2.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> INCUBATOR = register("incubator",
			() -> new IncubatorBlock(AbstractBlock.Properties.of(Material.STONE).strength(2.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));

	public static final RegistryObject<Block> AMBROSIUM_WALL_TORCH = BLOCKS.register("ambrosium_wall_torch", () -> new WallTorchBlock(AbstractBlock.Properties.copy(Blocks.WALL_TORCH), ParticleTypes.SMOKE));
	public static final RegistryObject<Block> AMBROSIUM_TORCH = register("ambrosium_torch", () -> new TorchBlock(AbstractBlock.Properties.copy(Blocks.TORCH), ParticleTypes.SMOKE));

	public static final RegistryObject<Block> SKYROOT_SIGN = register("skyroot_sign", () -> new SkyrootSignBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), AetherWoodTypes.SKYROOT));
	public static final RegistryObject<Block> SKYROOT_WALL_SIGN = BLOCKS.register("skyroot_wall_sign", () -> new SkyrootWallSignBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(SKYROOT_SIGN.get()), AetherWoodTypes.SKYROOT));

	public static final RegistryObject<Block> BERRY_BUSH = register("berry_bush", () -> new BerryBushBlock(AbstractBlock.Properties.of(Material.PLANT).strength(0.2F).sound(SoundType.GRASS).harvestTool(ToolType.HOE).noOcclusion()
			.isValidSpawn((state, reader, pos, entity) -> (entity == EntityType.OCELOT || entity == EntityType.PARROT)).isSuffocating((state, reader, pos) -> false).isViewBlocking((state, reader, pos) -> false)));
	public static final RegistryObject<Block> BERRY_BUSH_STEM = register("berry_bush_stem", () -> new BerryBushStemBlock(AbstractBlock.Properties.of(Material.PLANT).strength(0.2F).harvestTool(ToolType.AXE).sound(SoundType.GRASS).noCollission()));

	public static final RegistryObject<Block> PURPLE_FLOWER = register("purple_flower", () -> new FlowerBlock(Effects.SLOW_FALLING, 4, AbstractBlock.Properties.copy(Blocks.DANDELION)));
	public static final RegistryObject<Block> WHITE_FLOWER = register("white_flower", () -> new AetherFlowerBlock(AetherEffects.INEBRIATION, 12, AbstractBlock.Properties.copy(Blocks.DANDELION)));
	public static final RegistryObject<FlowerPotBlock> POTTED_PURPLE_FLOWER = BLOCKS.register("potted_purple_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PURPLE_FLOWER, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_WHITE_FLOWER = BLOCKS.register("potted_white_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHITE_FLOWER, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)));

	public static final RegistryObject<SaplingBlock> SKYROOT_SAPLING = register("skyroot_sapling",
			() -> new SaplingBlock(new SkyrootTree(), AbstractBlock.Properties.copy(Blocks.OAK_SAPLING)));
	public static final RegistryObject<SaplingBlock> GOLDEN_OAK_SAPLING = register("golden_oak_sapling",
			() -> new SaplingBlock(new GoldenOakTree(), AbstractBlock.Properties.copy(Blocks.OAK_SAPLING)));
	public static final RegistryObject<FlowerPotBlock> POTTED_SKYROOT_SAPLING = BLOCKS.register("potted_skyroot_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYROOT_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_GOLDEN_OAK_SAPLING = BLOCKS.register("potted_golden_oak_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GOLDEN_OAK_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)));

	public static final RegistryObject<Block> CARVED_STONE = register("carved_stone",
			() -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(0.5F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> SENTRY_STONE = register("sentry_stone", () -> new Block(AbstractBlock.Properties.copy(AetherBlocks.CARVED_STONE.get()).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> ANGELIC_STONE = register("angelic_stone",
			() -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(0.5F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> LIGHT_ANGELIC_STONE = register("light_angelic_stone", () -> new Block(AbstractBlock.Properties.copy(AetherBlocks.ANGELIC_STONE.get()).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> HELLFIRE_STONE  = register("hellfire_stone",
			() -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(0.5F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> LIGHT_HELLFIRE_STONE = register("light_hellfire_stone", () -> new Block(AbstractBlock.Properties.copy(AetherBlocks.HELLFIRE_STONE.get()).lightLevel((state) -> 11)));

	public static final RegistryObject<Block> LOCKED_CARVED_STONE = register("locked_carved_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK)));
	public static final RegistryObject<Block> LOCKED_SENTRY_STONE = register("locked_sentry_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> LOCKED_ANGELIC_STONE = register("locked_angelic_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK)));
	public static final RegistryObject<Block> LOCKED_LIGHT_ANGELIC_STONE = register("locked_light_angelic_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> LOCKED_HELLFIRE_STONE = register("locked_hellfire_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK)));
	public static final RegistryObject<Block> LOCKED_LIGHT_HELLFIRE_STONE = register("locked_light_hellfire_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK).lightLevel((state) -> 11)));

	public static final RegistryObject<Block> TRAPPED_CARVED_STONE = register("trapped_carved_stone",
			() -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> LOCKED_CARVED_STONE.get().defaultBlockState(), AbstractBlock.Properties.copy(Blocks.BEDROCK)));
	public static final RegistryObject<Block> TRAPPED_SENTRY_STONE = register("trapped_sentry_stone",
			() -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> LOCKED_SENTRY_STONE.get().defaultBlockState(), AbstractBlock.Properties.copy(Blocks.BEDROCK).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> TRAPPED_ANGELIC_STONE = register("trapped_angelic_stone",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK)));
	//new TrappedBlock(() -> AetherEntityTypes.VALKYRIE, () -> LOCKED_ANGELIC_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> TRAPPED_LIGHT_ANGELIC_STONE = register("trapped_light_angelic_stone",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK).lightLevel((state) -> 11)));
	//new TrappedBlock(() -> AetherEntityTypes.VALKYRIE, () -> LOCKED_LIGHT_ANGELIC_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));
	public static final RegistryObject<Block> TRAPPED_HELLFIRE_STONE = register("trapped_hellfire_stone",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK)));
	//new TrappedBlock(() -> AetherEntityTypes.FIRE_MINION, () -> LOCKED_HELLFIRE_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> TRAPPED_LIGHT_HELLFIRE_STONE = register("trapped_light_hellfire_stone",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.BEDROCK).lightLevel((state) -> 11)));
	//new TrappedBlock(() -> AetherEntityTypes.FIRE_MINION, () -> LOCKED_LIGHT_HELLFIRE_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));

	public static final RegistryObject<Block> CHEST_MIMIC = register("chest_mimic", () -> new ChestMimicBlock(AbstractBlock.Properties.copy(Blocks.CHEST).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> TREASURE_CHEST = register("treasure_chest", () -> new TreasureChestBlock(AbstractBlock.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F)));

	public static final RegistryObject<RotatedPillarBlock> PILLAR = register("pillar",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.QUARTZ).strength(0.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<RotatedPillarBlock> PILLAR_TOP = register("pillar_top",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.QUARTZ).strength(0.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> PRESENT = register("present",
			() -> new Block(AbstractBlock.Properties.of(Material.GRASS).strength(0.6F).sound(SoundType.GRASS)));

	public static final RegistryObject<FenceBlock> SKYROOT_FENCE = register("skyroot_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(Blocks.OAK_FENCE).harvestTool(ToolType.AXE)));
	public static final RegistryObject<FenceGateBlock> SKYROOT_FENCE_GATE = register("skyroot_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(Blocks.OAK_FENCE_GATE).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> SKYROOT_DOOR = register("skyroot_door", () -> new DoorBlock(AbstractBlock.Properties.copy(Blocks.OAK_DOOR)));
	public static final RegistryObject<Block> SKYROOT_TRAPDOOR = register("skyroot_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(Blocks.OAK_TRAPDOOR)));
	public static final RegistryObject<Block> SKYROOT_BUTTON = register("skyroot_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(Blocks.OAK_BUTTON)));
	public static final RegistryObject<Block> SKYROOT_PRESSURE_PLATE = register("skyroot_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(Blocks.OAK_BUTTON)));

	public static final RegistryObject<Block> HOLYSTONE_BUTTON = register("holystone_button", () -> new StoneButtonBlock(AbstractBlock.Properties.copy(Blocks.STONE_BUTTON)));
	public static final RegistryObject<Block> HOLYSTONE_PRESSURE_PLATE = register("holystone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, AbstractBlock.Properties.copy(Blocks.STONE_BUTTON)));

	public static final RegistryObject<WallBlock> CARVED_WALL = register("carved_wall", () -> new WallBlock(AbstractBlock.Properties.copy(AetherBlocks.CARVED_STONE.get())));
	public static final RegistryObject<WallBlock> ANGELIC_WALL = register("angelic_wall", () -> new WallBlock(AbstractBlock.Properties.copy(AetherBlocks.ANGELIC_STONE.get())));
	public static final RegistryObject<WallBlock> HELLFIRE_WALL = register("hellfire_wall", () -> new WallBlock(AbstractBlock.Properties.copy(AetherBlocks.HELLFIRE_STONE.get())));
	public static final RegistryObject<WallBlock> HOLYSTONE_WALL = register("holystone_wall", () -> new WallBlock(AbstractBlock.Properties.copy(AetherBlocks.HOLYSTONE.get())));
	public static final RegistryObject<WallBlock> MOSSY_HOLYSTONE_WALL = register("mossy_holystone_wall", () -> new WallBlock(AbstractBlock.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get())));
	public static final RegistryObject<WallBlock> HOLYSTONE_BRICK_WALL = register("holystone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get())));
	public static final RegistryObject<WallBlock> AEROGEL_WALL = register("aerogel_wall", () -> new AerogelWallBlock(AbstractBlock.Properties.copy(AetherBlocks.AEROGEL.get()).isViewBlocking((state, reader, pos) -> false)));

	public static final RegistryObject<StairsBlock> SKYROOT_STAIRS = register("skyroot_stairs",
			() -> new StairsBlock(() -> SKYROOT_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(AetherBlocks.SKYROOT_PLANKS.get())));
	public static final RegistryObject<StairsBlock> CARVED_STAIRS = register("carved_stairs",
			() -> new StairsBlock(() -> CARVED_STONE.get().defaultBlockState(), AbstractBlock.Properties.copy(AetherBlocks.CARVED_STONE.get())));
	public static final RegistryObject<StairsBlock> ANGELIC_STAIRS = register("angelic_stairs",
			() -> new StairsBlock(() -> ANGELIC_STONE.get().defaultBlockState(), AbstractBlock.Properties.copy(AetherBlocks.ANGELIC_STONE.get())));
	public static final RegistryObject<StairsBlock> HELLFIRE_STAIRS = register("hellfire_stairs",
			() -> new StairsBlock(() -> HELLFIRE_STONE.get().defaultBlockState(), AbstractBlock.Properties.copy(AetherBlocks.HELLFIRE_STONE.get())));
	public static final RegistryObject<StairsBlock> HOLYSTONE_STAIRS = register("holystone_stairs",
			() -> new StairsBlock(() -> HOLYSTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(AetherBlocks.HOLYSTONE.get())));
	public static final RegistryObject<StairsBlock> MOSSY_HOLYSTONE_STAIRS = register("mossy_holystone_stairs",
			() -> new StairsBlock(() -> MOSSY_HOLYSTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get())));
	public static final RegistryObject<StairsBlock> HOLYSTONE_BRICK_STAIRS = register("holystone_brick_stairs",
			() -> new StairsBlock(() -> HOLYSTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get())));
	public static final RegistryObject<StairsBlock> AEROGEL_STAIRS = register("aerogel_stairs",
			() -> new AerogelStairsBlock(() -> AEROGEL.get().defaultBlockState(), AbstractBlock.Properties.copy(AetherBlocks.AEROGEL.get()).isViewBlocking((state, reader, pos) -> false)));

	public static final RegistryObject<SlabBlock> SKYROOT_SLAB = register("skyroot_slab",
			() -> new SlabBlock(AbstractBlock.Properties.copy(AetherBlocks.SKYROOT_PLANKS.get())));
	public static final RegistryObject<SlabBlock> CARVED_SLAB = register("carved_slab",
			() -> new SlabBlock(AbstractBlock.Properties.copy(AetherBlocks.CARVED_STONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> ANGELIC_SLAB = register("angelic_slab",
			() -> new SlabBlock(AbstractBlock.Properties.copy(AetherBlocks.ANGELIC_STONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> HELLFIRE_SLAB = register("hellfire_slab",
			() -> new SlabBlock(AbstractBlock.Properties.copy(AetherBlocks.HELLFIRE_STONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> HOLYSTONE_SLAB = register("holystone_slab",
			() -> new SlabBlock(AbstractBlock.Properties.copy(AetherBlocks.HOLYSTONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> MOSSY_HOLYSTONE_SLAB = register("mossy_holystone_slab",
			() -> new SlabBlock(AbstractBlock.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> HOLYSTONE_BRICK_SLAB = register("holystone_brick_slab",
			() -> new SlabBlock(AbstractBlock.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> AEROGEL_SLAB = register("aerogel_slab",
			() -> new AerogelSlabBlock(AbstractBlock.Properties.copy(AetherBlocks.AEROGEL.get()).strength(1.5F, 2000.0F).isViewBlocking((state, reader, pos) -> false)));

	public static final RegistryObject<Block> SUN_ALTAR = register("sun_altar",
			() -> new SunAltarBlock(AbstractBlock.Properties.of(Material.STONE).strength(2.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE)));

	public static final RegistryObject<Block> SKYROOT_BOOKSHELF = register("skyroot_bookshelf", () -> new BookshelfBlock(AbstractBlock.Properties.copy(Blocks.BOOKSHELF).harvestTool(ToolType.AXE)));

	public static final RegistryObject<BedBlock> SKYROOT_BED = register("skyroot_bed", () -> new SkyrootBedBlock(AbstractBlock.Properties.copy(Blocks.WHITE_BED).harvestTool(ToolType.AXE)));


	public static void registerPots() {
		FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;

		pot.addPlant(AetherBlocks.PURPLE_FLOWER.getId(), AetherBlocks.POTTED_PURPLE_FLOWER);
		pot.addPlant(AetherBlocks.WHITE_FLOWER.getId(), AetherBlocks.POTTED_WHITE_FLOWER);
		pot.addPlant(AetherBlocks.SKYROOT_SAPLING.getId(), AetherBlocks.POTTED_SKYROOT_SAPLING);
		pot.addPlant(AetherBlocks.GOLDEN_OAK_SAPLING.getId(), AetherBlocks.POTTED_GOLDEN_OAK_SAPLING);
	}

	public static void registerAxeStrippingBlocks() {
		AxeItem.STRIPABLES = ImmutableMap.<Block, Block>builder()
				.putAll(AxeItem.STRIPABLES)
				.put(AetherBlocks.SKYROOT_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
				.put(AetherBlocks.GOLDEN_OAK_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
				.put(AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
				.put(AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
				.build();
	}

	public static void registerFlammability() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFlammable(AetherBlocks.SKYROOT_LEAVES.get(), 30, 60);
		fireblock.setFlammable(AetherBlocks.GOLDEN_OAK_LEAVES.get(), 30, 60);
		fireblock.setFlammable(AetherBlocks.CRYSTAL_LEAVES.get(), 30, 60);
		fireblock.setFlammable(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(), 30, 60);
		fireblock.setFlammable(AetherBlocks.HOLIDAY_LEAVES.get(), 30, 60);
		fireblock.setFlammable(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(), 30, 60);
		fireblock.setFlammable(AetherBlocks.SKYROOT_LOG.get(), 5, 5);
		fireblock.setFlammable(AetherBlocks.GOLDEN_OAK_LOG.get(), 5, 5);
		fireblock.setFlammable(AetherBlocks.STRIPPED_SKYROOT_LOG.get(), 5, 5);
		fireblock.setFlammable(AetherBlocks.SKYROOT_WOOD.get(), 5, 5);
		fireblock.setFlammable(AetherBlocks.GOLDEN_OAK_WOOD.get(), 5, 5);
		fireblock.setFlammable(AetherBlocks.STRIPPED_SKYROOT_WOOD.get(), 5, 5);
		fireblock.setFlammable(AetherBlocks.SKYROOT_PLANKS.get(), 5, 20);
		fireblock.setFlammable(AetherBlocks.BERRY_BUSH.get(), 30, 60);
		fireblock.setFlammable(AetherBlocks.BERRY_BUSH_STEM.get(), 60, 100);
		fireblock.setFlammable(AetherBlocks.PURPLE_FLOWER.get(), 60, 100);
		fireblock.setFlammable(AetherBlocks.WHITE_FLOWER.get(), 60, 100);
		fireblock.setFlammable(AetherBlocks.SKYROOT_FENCE_GATE.get(), 5, 20);
		fireblock.setFlammable(AetherBlocks.SKYROOT_FENCE.get(), 5, 20);
		fireblock.setFlammable(AetherBlocks.SKYROOT_STAIRS.get(), 5, 20);
		fireblock.setFlammable(AetherBlocks.SKYROOT_SLAB.get(), 5, 20);
		fireblock.setFlammable(AetherBlocks.SKYROOT_BOOKSHELF.get(), 30, 20);
	}

	public static void registerWoodTypes() {
		WoodType.register(AetherWoodTypes.SKYROOT);
	}


	private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
		RegistryObject<T> register = BLOCKS.register(name, block);
		AetherItems.ITEMS.register(name, item.apply(register));
		return register;
	}

	@SuppressWarnings("unchecked")
	private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
		return (RegistryObject<B>) baseRegister(name, block, AetherBlocks::registerBlockItem);
	}

	private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
		return () -> {
			if (Objects.requireNonNull(block.get()) == ENCHANTED_AETHER_GRASS_BLOCK.get()
					|| Objects.requireNonNull(block.get()) == QUICKSOIL_GLASS.get()
					|| Objects.requireNonNull(block.get()) == ENCHANTED_GRAVITITE.get()) {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().rarity(Rarity.RARE).tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (Objects.requireNonNull(block.get()) == AEROGEL.get()
					|| Objects.requireNonNull(block.get()) == AEROGEL_WALL.get()
					|| Objects.requireNonNull(block.get()) == AEROGEL_STAIRS.get()
					|| Objects.requireNonNull(block.get()) == AEROGEL_SLAB.get()) {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (Objects.requireNonNull(block.get()) == AMBROSIUM_TORCH.get()) {
				return new WallOrFloorItem(AMBROSIUM_TORCH.get(), AMBROSIUM_WALL_TORCH.get(), new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (Objects.requireNonNull(block.get()) == SKYROOT_SIGN.get()) {
				return new SignItem((new Item.Properties()).stacksTo(16).tab(AetherItemGroups.AETHER_BLOCKS), SKYROOT_SIGN.get(), SKYROOT_WALL_SIGN.get());
			}
			else if (Objects.requireNonNull(block.get()) == CHEST_MIMIC.get()) {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS).setISTER(() -> AetherRendering::chestMimicRenderer));
			}
			else if (Objects.requireNonNull(block.get()) == TREASURE_CHEST.get()) {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS).setISTER(() -> AetherRendering::treasureChestRenderer));
			}
			else if (Objects.requireNonNull(block.get()) == SKYROOT_PLANKS.get()
					|| Objects.requireNonNull(block.get()) == SKYROOT_FENCE_GATE.get()
					|| Objects.requireNonNull(block.get()) == SKYROOT_FENCE.get()
					|| Objects.requireNonNull(block.get()) == SKYROOT_BOOKSHELF.get()) {
				return new BurnableBlockItem(Objects.requireNonNull(block.get()), new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (Objects.requireNonNull(block.get()) == SUN_ALTAR.get()) {
				return new BedItem(Objects.requireNonNull(block.get()), new Item.Properties().fireResistant().tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (Objects.requireNonNull(block.get()) == SKYROOT_BED.get()) {
				return new BedItem(Objects.requireNonNull(block.get()), new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_BLOCKS).setISTER(() -> AetherRendering::skyrootBedRenderer));
			}
			else {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
			}
		};
	}
}