package com.gildedgames.aether.common.registry;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.AetherRendering;
import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.common.block.construction.AerogelBlock;
import com.gildedgames.aether.common.block.construction.AerogelSlabBlock;
import com.gildedgames.aether.common.block.construction.AerogelStairsBlock;
import com.gildedgames.aether.common.block.construction.AerogelWallBlock;
import com.gildedgames.aether.common.block.construction.AetherFarmlandBlock;
import com.gildedgames.aether.common.block.construction.BookshelfBlock;
import com.gildedgames.aether.common.block.construction.IcestoneSlabBlock;
import com.gildedgames.aether.common.block.construction.IcestoneStairsBlock;
import com.gildedgames.aether.common.block.construction.IcestoneWallBlock;
import com.gildedgames.aether.common.block.construction.SkyrootSignBlock;
import com.gildedgames.aether.common.block.construction.SkyrootWallSignBlock;
import com.gildedgames.aether.common.block.dungeon.ChestMimicBlock;
import com.gildedgames.aether.common.block.dungeon.TrappedBlock;
import com.gildedgames.aether.common.block.dungeon.TreasureChestBlock;
import com.gildedgames.aether.common.block.miscellaneous.AetherPortalBlock;
import com.gildedgames.aether.common.block.natural.AercloudBlock;
import com.gildedgames.aether.common.block.natural.AetherFlowerBlock;
import com.gildedgames.aether.common.block.natural.AetherGrassBlock;
import com.gildedgames.aether.common.block.natural.AetherLogBlock;
import com.gildedgames.aether.common.block.natural.AetherOreBlock;
import com.gildedgames.aether.common.block.natural.BerryBushBlock;
import com.gildedgames.aether.common.block.natural.BerryBushStemBlock;
import com.gildedgames.aether.common.block.natural.BlueAercloudBlock;
import com.gildedgames.aether.common.block.natural.EnchantedAetherGrassBlock;
import com.gildedgames.aether.common.block.natural.IcestoneBlock;
import com.gildedgames.aether.common.block.natural.LeavesWithParticlesBlock;
import com.gildedgames.aether.common.block.natural.PinkAercloudBlock;
import com.gildedgames.aether.common.block.util.AetherDoubleDropBlock;
import com.gildedgames.aether.common.block.util.AetherDoubleDropsLeaves;
import com.gildedgames.aether.common.block.util.AetherDoubleDropsOreBlock;
import com.gildedgames.aether.common.block.util.AetherDoubleDropsRotatedPillarBlock;
import com.gildedgames.aether.common.block.util.FloatingBlock;
import com.gildedgames.aether.common.block.utility.AltarBlock;
import com.gildedgames.aether.common.block.utility.FreezerBlock;
import com.gildedgames.aether.common.block.utility.IncubatorBlock;
import com.gildedgames.aether.common.block.utility.SkyrootBedBlock;
import com.gildedgames.aether.common.block.utility.SunAltarBlock;
import com.gildedgames.aether.common.item.block.BurnableBlockItem;
import com.gildedgames.aether.common.world.gen.tree.GoldenOakTree;
import com.gildedgames.aether.common.world.gen.tree.SkyrootTree;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BedItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.item.SignItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Aether.MODID);

	public static final RegistryObject<AetherPortalBlock> AETHER_PORTAL = BLOCKS.register("aether_portal", () -> new AetherPortalBlock(Block.Properties.copy(Blocks.NETHER_PORTAL)));

	public static final RegistryObject<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", () -> new AetherGrassBlock(Block.Properties.of(Material.GRASS, MaterialColor.WARPED_WART_BLOCK).randomTicks().strength(0.6F).harvestTool(ToolType.SHOVEL).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> ENCHANTED_AETHER_GRASS_BLOCK = register("enchanted_aether_grass_block", () -> new EnchantedAetherGrassBlock(Block.Properties.of(Material.GRASS, MaterialColor.GOLD).randomTicks().strength(0.6F).harvestTool(ToolType.SHOVEL).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> AETHER_DIRT = register("aether_dirt", () -> new AetherDoubleDropBlock(Block.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_CYAN).strength(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.GRAVEL)));
	public static final RegistryObject<Block> QUICKSOIL = register("quicksoil", () -> new AetherDoubleDropBlock(Block.Properties.of(Material.SAND, MaterialColor.COLOR_YELLOW).strength(0.5F).harvestTool(ToolType.SHOVEL).friction(1.1F).sound(SoundType.SAND)));
	public static final RegistryObject<Block> HOLYSTONE = register("holystone", () -> new AetherDoubleDropBlock(Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(0.5F, 6.0f).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> MOSSY_HOLYSTONE = register("mossy_holystone", () -> new AetherDoubleDropBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE.get())));
	public static final RegistryObject<Block> AETHER_FARMLAND = register("aether_farmland", () -> new AetherFarmlandBlock(Block.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_CYAN).randomTicks().strength(0.6F).harvestTool(ToolType.SHOVEL).sound(SoundType.GRAVEL).isViewBlocking(AetherBlocks::always).isSuffocating(AetherBlocks::always)));

	public static final RegistryObject<Block> COLD_AERCLOUD = register("cold_aercloud", () -> new AercloudBlock(Block.Properties.of(Material.ICE, MaterialColor.SNOW).strength(0.2F).sound(SoundType.WOOL).harvestTool(ToolType.HOE).noOcclusion()));
	public static final RegistryObject<Block> BLUE_AERCLOUD = register("blue_aercloud", () -> new BlueAercloudBlock(Block.Properties.of(Material.ICE, MaterialColor.COLOR_LIGHT_BLUE).strength(0.2F).sound(SoundType.WOOL).harvestTool(ToolType.HOE).noOcclusion()));
	public static final RegistryObject<Block> GOLDEN_AERCLOUD = register("golden_aercloud", () -> new AercloudBlock(Block.Properties.of(Material.ICE, MaterialColor.COLOR_YELLOW).strength(0.2F).sound(SoundType.WOOL).harvestTool(ToolType.HOE).noOcclusion()));
	public static final RegistryObject<Block> PINK_AERCLOUD = register("pink_aercloud", () -> new PinkAercloudBlock(Block.Properties.of(Material.ICE, MaterialColor.COLOR_PINK).strength(0.2F).sound(SoundType.WOOL).harvestTool(ToolType.HOE).noOcclusion()));

	public static final RegistryObject<Block> ICESTONE = register("icestone", () -> new IcestoneBlock(Block.Properties.of(Material.STONE, MaterialColor.ICE).strength(3.0F).randomTicks().sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> AMBROSIUM_ORE = register("ambrosium_ore", () -> new AetherDoubleDropsOreBlock(0, 2, Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> ZANITE_ORE = register("zanite_ore", () -> new AetherOreBlock(3, 5, Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> GRAVITITE_ORE = register("gravitite_ore", () -> new FloatingBlock(false, Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(5.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(2).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> SKYROOT_LEAVES = register("skyroot_leaves", () -> new AetherDoubleDropsLeaves(Block.Properties.of(Material.LEAVES, MaterialColor.GRASS).strength(0.2F).randomTicks().harvestTool(ToolType.HOE).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
	public static final RegistryObject<Block> GOLDEN_OAK_LEAVES = register("golden_oak_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.GOLDEN_OAK_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.GOLD).strength(0.2F).randomTicks().harvestTool(ToolType.HOE).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> CRYSTAL_LEAVES = register("crystal_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.CRYSTAL_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.DIAMOND).strength(0.2F).randomTicks().harvestTool(ToolType.HOE).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> CRYSTAL_FRUIT_LEAVES = register("crystal_fruit_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.CRYSTAL_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.DIAMOND).strength(0.2F).randomTicks().harvestTool(ToolType.HOE).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> HOLIDAY_LEAVES = register("holiday_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.COLOR_PURPLE).strength(0.2F).randomTicks().harvestTool(ToolType.HOE).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> DECORATED_HOLIDAY_LEAVES = register("decorated_holiday_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.COLOR_PURPLE).strength(0.2F).randomTicks().harvestTool(ToolType.HOE).sound(SoundType.GRASS)));

	public static final RegistryObject<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", () -> new AetherLogBlock(Block.Properties.copy(Blocks.OAK_LOG).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> GOLDEN_OAK_LOG = register("golden_oak_log", () -> new AetherLogBlock(Block.Properties.copy(Blocks.OAK_LOG).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = register("stripped_skyroot_log", () -> new AetherLogBlock(Block.Properties.copy(Blocks.STRIPPED_OAK_LOG).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> SKYROOT_WOOD = register("skyroot_wood", () -> new AetherDoubleDropsRotatedPillarBlock(Block.Properties.copy(Blocks.OAK_WOOD).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> GOLDEN_OAK_WOOD = register("golden_oak_wood", () -> new AetherDoubleDropsRotatedPillarBlock(Block.Properties.copy(Blocks.OAK_WOOD).harvestTool(ToolType.AXE)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = register("stripped_skyroot_wood", () -> new AetherDoubleDropsRotatedPillarBlock(Block.Properties.copy(Blocks.STRIPPED_OAK_WOOD).harvestTool(ToolType.AXE)));

	public static final RegistryObject<Block> SKYROOT_PLANKS = register("skyroot_planks", () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> HOLYSTONE_BRICKS = register("holystone_bricks", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(0.5F, 10.0F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> QUICKSOIL_GLASS = register("quicksoil_glass", () -> new GlassBlock(Block.Properties.of(Material.GLASS, MaterialColor.COLOR_YELLOW).strength(0.3F).friction(1.1F).lightLevel((state) -> 11).sound(SoundType.GLASS).noOcclusion().isValidSpawn(AetherBlocks::never).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
	public static final RegistryObject<Block> AEROGEL = register("aerogel", () -> new AerogelBlock(Block.Properties.of(Material.STONE, MaterialColor.DIAMOND).strength(1.0F, 2000.0F).sound(SoundType.METAL).noOcclusion().harvestTool(ToolType.PICKAXE).harvestLevel(3).requiresCorrectToolForDrops().isViewBlocking(AetherBlocks::never)));

	public static final RegistryObject<Block> ZANITE_BLOCK = register("zanite_block", () -> new Block(Block.Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE).strength(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops().sound(SoundType.METAL)));
	public static final RegistryObject<Block> ENCHANTED_GRAVITITE = register("enchanted_gravitite", () -> new FloatingBlock(true, Block.Properties.of(Material.METAL, MaterialColor.COLOR_PINK).strength(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).requiresCorrectToolForDrops().sound(SoundType.METAL)));

	public static final RegistryObject<Block> ALTAR = register("altar", () -> new AltarBlock(Block.Properties.of(Material.STONE, MaterialColor.WOOD).strength(2.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> FREEZER = register("freezer", () -> new FreezerBlock(Block.Properties.of(Material.STONE, MaterialColor.WOOD).strength(2.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> INCUBATOR = register("incubator", () -> new IncubatorBlock(Block.Properties.of(Material.STONE, MaterialColor.WOOD).strength(2.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));

	public static final RegistryObject<Block> AMBROSIUM_WALL_TORCH = BLOCKS.register("ambrosium_wall_torch", () -> new WallTorchBlock(Block.Properties.copy(Blocks.WALL_TORCH), ParticleTypes.SMOKE));
	public static final RegistryObject<Block> AMBROSIUM_TORCH = register("ambrosium_torch", () -> new TorchBlock(Block.Properties.copy(Blocks.TORCH), ParticleTypes.SMOKE));

	public static final RegistryObject<Block> SKYROOT_SIGN = register("skyroot_sign", () -> new SkyrootSignBlock(Block.Properties.of(Material.WOOD, MaterialColor.SAND).noCollission().strength(1.0F).sound(SoundType.WOOD), AetherWoodTypes.SKYROOT));
	public static final RegistryObject<Block> SKYROOT_WALL_SIGN = BLOCKS.register("skyroot_wall_sign", () -> new SkyrootWallSignBlock(Block.Properties.of(Material.WOOD, MaterialColor.SAND).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(SKYROOT_SIGN::get), AetherWoodTypes.SKYROOT));

	public static final RegistryObject<Block> BERRY_BUSH = register("berry_bush", () -> new BerryBushBlock(Block.Properties.of(Material.PLANT, MaterialColor.GRASS).strength(0.2F).sound(SoundType.GRASS).harvestTool(ToolType.HOE).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
	public static final RegistryObject<Block> BERRY_BUSH_STEM = register("berry_bush_stem", () -> new BerryBushStemBlock(Block.Properties.of(Material.PLANT, MaterialColor.GRASS).strength(0.2F).harvestTool(ToolType.AXE).sound(SoundType.GRASS).noCollission()));

	public static final RegistryObject<Block> PURPLE_FLOWER = register("purple_flower", () -> new FlowerBlock(Effects.SLOW_FALLING, 4, Block.Properties.copy(Blocks.DANDELION)));
	public static final RegistryObject<Block> WHITE_FLOWER = register("white_flower", () -> new AetherFlowerBlock(AetherEffects.INEBRIATION, 12, Block.Properties.copy(Blocks.DANDELION)));
	public static final RegistryObject<FlowerPotBlock> POTTED_PURPLE_FLOWER = BLOCKS.register("potted_purple_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PURPLE_FLOWER, Block.Properties.copy(Blocks.FLOWER_POT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_WHITE_FLOWER = BLOCKS.register("potted_white_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHITE_FLOWER, Block.Properties.copy(Blocks.FLOWER_POT)));

	public static final RegistryObject<SaplingBlock> SKYROOT_SAPLING = register("skyroot_sapling", () -> new SaplingBlock(new SkyrootTree(), Block.Properties.copy(Blocks.OAK_SAPLING)));
	public static final RegistryObject<SaplingBlock> GOLDEN_OAK_SAPLING = register("golden_oak_sapling", () -> new SaplingBlock(new GoldenOakTree(), Block.Properties.copy(Blocks.OAK_SAPLING)));
	public static final RegistryObject<FlowerPotBlock> POTTED_SKYROOT_SAPLING = BLOCKS.register("potted_skyroot_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYROOT_SAPLING, Block.Properties.copy(Blocks.FLOWER_POT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_GOLDEN_OAK_SAPLING = BLOCKS.register("potted_golden_oak_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GOLDEN_OAK_SAPLING, Block.Properties.copy(Blocks.FLOWER_POT)));

	public static final RegistryObject<Block> CARVED_STONE = register("carved_stone", () -> new Block(Block.Properties.of(Material.STONE).strength(0.5F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> SENTRY_STONE = register("sentry_stone", () -> new Block(Block.Properties.copy(AetherBlocks.CARVED_STONE.get()).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> ANGELIC_STONE = register("angelic_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.SAND).strength(0.5F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> LIGHT_ANGELIC_STONE = register("light_angelic_stone", () -> new Block(Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get()).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> HELLFIRE_STONE = register("hellfire_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(0.5F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> LIGHT_HELLFIRE_STONE = register("light_hellfire_stone", () -> new Block(Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get()).lightLevel((state) -> 11)));

	public static final RegistryObject<Block> LOCKED_CARVED_STONE = register("locked_carved_stone", () -> new Block(Block.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
	public static final RegistryObject<Block> LOCKED_SENTRY_STONE = register("locked_sentry_stone", () -> new Block(Block.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> LOCKED_ANGELIC_STONE = register("locked_angelic_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.SAND).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
	public static final RegistryObject<Block> LOCKED_LIGHT_ANGELIC_STONE = register("locked_light_angelic_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.SAND).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> LOCKED_HELLFIRE_STONE = register("locked_hellfire_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
	public static final RegistryObject<Block> LOCKED_LIGHT_HELLFIRE_STONE = register("locked_light_hellfire_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never).lightLevel((state) -> 11)));

	public static final RegistryObject<Block> TRAPPED_CARVED_STONE = register("trapped_carved_stone", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> LOCKED_CARVED_STONE.get().defaultBlockState(), Block.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
	public static final RegistryObject<Block> TRAPPED_SENTRY_STONE = register("trapped_sentry_stone", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> LOCKED_SENTRY_STONE.get().defaultBlockState(), Block.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never).lightLevel((state) -> 11)));
	public static final RegistryObject<Block> TRAPPED_ANGELIC_STONE = register("trapped_angelic_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.SAND).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
	//new TrappedBlock(() -> AetherEntityTypes.VALKYRIE, () -> LOCKED_ANGELIC_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> TRAPPED_LIGHT_ANGELIC_STONE = register("trapped_light_angelic_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.SAND).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never).lightLevel((state) -> 11)));
	//new TrappedBlock(() -> AetherEntityTypes.VALKYRIE, () -> LOCKED_LIGHT_ANGELIC_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));
	public static final RegistryObject<Block> TRAPPED_HELLFIRE_STONE = register("trapped_hellfire_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
	//new TrappedBlock(() -> AetherEntityTypes.FIRE_MINION, () -> LOCKED_HELLFIRE_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK)));
	public static final RegistryObject<Block> TRAPPED_LIGHT_HELLFIRE_STONE = register("trapped_light_hellfire_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never).lightLevel((state) -> 11)));
	//new TrappedBlock(() -> AetherEntityTypes.FIRE_MINION, () -> LOCKED_LIGHT_HELLFIRE_STONE.getDefaultState(), Block.Properties.from(Blocks.BEDROCK).setLightLevel((state) -> 11)));

	public static final RegistryObject<Block> CHEST_MIMIC = register("chest_mimic", () -> new ChestMimicBlock(Block.Properties.copy(Blocks.CHEST).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> TREASURE_CHEST = register("treasure_chest", () -> new TreasureChestBlock(Block.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F)));

	public static final RegistryObject<RotatedPillarBlock> PILLAR = register("pillar", () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, MaterialColor.QUARTZ).strength(0.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));
	public static final RegistryObject<RotatedPillarBlock> PILLAR_TOP = register("pillar_top", () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, MaterialColor.QUARTZ).strength(0.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> PRESENT = register("present", () -> new Block(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).strength(0.6F).sound(SoundType.GRASS)));

	public static final RegistryObject<FenceBlock> SKYROOT_FENCE = register("skyroot_fence", () -> new FenceBlock(Block.Properties.copy(Blocks.OAK_FENCE).harvestTool(ToolType.AXE)));
	public static final RegistryObject<FenceGateBlock> SKYROOT_FENCE_GATE = register("skyroot_fence_gate", () -> new FenceGateBlock(Block.Properties.copy(Blocks.OAK_FENCE_GATE).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> SKYROOT_DOOR = register("skyroot_door", () -> new DoorBlock(Block.Properties.copy(Blocks.OAK_DOOR)));
	public static final RegistryObject<Block> SKYROOT_TRAPDOOR = register("skyroot_trapdoor", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR)));
	public static final RegistryObject<Block> SKYROOT_BUTTON = register("skyroot_button", () -> new WoodButtonBlock(Block.Properties.copy(Blocks.OAK_BUTTON)));
	public static final RegistryObject<Block> SKYROOT_PRESSURE_PLATE = register("skyroot_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));

	public static final RegistryObject<Block> HOLYSTONE_BUTTON = register("holystone_button", () -> new StoneButtonBlock(Block.Properties.copy(Blocks.STONE_BUTTON)));
	public static final RegistryObject<Block> HOLYSTONE_PRESSURE_PLATE = register("holystone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.of(Material.STONE, MaterialColor.WOOL).requiresCorrectToolForDrops().noCollission().strength(0.5F)));

	public static final RegistryObject<WallBlock> CARVED_WALL = register("carved_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.CARVED_STONE.get())));
	public static final RegistryObject<WallBlock> ANGELIC_WALL = register("angelic_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get())));
	public static final RegistryObject<WallBlock> HELLFIRE_WALL = register("hellfire_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get())));
	public static final RegistryObject<WallBlock> HOLYSTONE_WALL = register("holystone_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE.get())));
	public static final RegistryObject<WallBlock> MOSSY_HOLYSTONE_WALL = register("mossy_holystone_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get())));
	public static final RegistryObject<WallBlock> ICESTONE_WALL = register("icestone_wall", () -> new IcestoneWallBlock(Block.Properties.copy(AetherBlocks.ICESTONE.get())));
	public static final RegistryObject<WallBlock> HOLYSTONE_BRICK_WALL = register("holystone_brick_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get())));
	public static final RegistryObject<WallBlock> AEROGEL_WALL = register("aerogel_wall", () -> new AerogelWallBlock(Block.Properties.copy(AetherBlocks.AEROGEL.get()).isViewBlocking(AetherBlocks::never)));

	public static final RegistryObject<StairsBlock> SKYROOT_STAIRS = register("skyroot_stairs", () -> new StairsBlock(() -> SKYROOT_PLANKS.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.SKYROOT_PLANKS.get())));
	public static final RegistryObject<StairsBlock> CARVED_STAIRS = register("carved_stairs", () -> new StairsBlock(() -> CARVED_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.CARVED_STONE.get())));
	public static final RegistryObject<StairsBlock> ANGELIC_STAIRS = register("angelic_stairs", () -> new StairsBlock(() -> ANGELIC_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get())));
	public static final RegistryObject<StairsBlock> HELLFIRE_STAIRS = register("hellfire_stairs", () -> new StairsBlock(() -> HELLFIRE_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get())));
	public static final RegistryObject<StairsBlock> HOLYSTONE_STAIRS = register("holystone_stairs", () -> new StairsBlock(() -> HOLYSTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HOLYSTONE.get())));
	public static final RegistryObject<StairsBlock> MOSSY_HOLYSTONE_STAIRS = register("mossy_holystone_stairs", () -> new StairsBlock(() -> MOSSY_HOLYSTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get())));
	public static final RegistryObject<StairsBlock> ICESTONE_STAIRS = register("icestone_stairs", () -> new IcestoneStairsBlock(() -> ICESTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.ICESTONE.get())));
	public static final RegistryObject<StairsBlock> HOLYSTONE_BRICK_STAIRS = register("holystone_brick_stairs", () -> new StairsBlock(() -> HOLYSTONE_BRICKS.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get())));
	public static final RegistryObject<StairsBlock> AEROGEL_STAIRS = register("aerogel_stairs", () -> new AerogelStairsBlock(() -> AEROGEL.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.AEROGEL.get()).isViewBlocking(AetherBlocks::never)));

	public static final RegistryObject<SlabBlock> SKYROOT_SLAB = register("skyroot_slab", () -> new SlabBlock(Block.Properties.copy(AetherBlocks.SKYROOT_PLANKS.get())));
	public static final RegistryObject<SlabBlock> CARVED_SLAB = register("carved_slab", () -> new SlabBlock(Block.Properties.copy(AetherBlocks.CARVED_STONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> ANGELIC_SLAB = register("angelic_slab", () -> new SlabBlock(Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> HELLFIRE_SLAB = register("hellfire_slab", () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> HOLYSTONE_SLAB = register("holystone_slab", () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> MOSSY_HOLYSTONE_SLAB = register("mossy_holystone_slab", () -> new SlabBlock(Block.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> ICESTONE_SLAB = register("icestone_slab", () -> new IcestoneSlabBlock(Block.Properties.copy(AetherBlocks.ICESTONE.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> HOLYSTONE_BRICK_SLAB = register("holystone_brick_slab", () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get()).strength(2.0F)));
	public static final RegistryObject<SlabBlock> AEROGEL_SLAB = register("aerogel_slab", () -> new AerogelSlabBlock(Block.Properties.copy(AetherBlocks.AEROGEL.get()).strength(1.5F, 2000.0F).isViewBlocking(AetherBlocks::never)));

	public static final RegistryObject<Block> SUN_ALTAR = register("sun_altar", () -> new SunAltarBlock(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(2.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE)));

	public static final RegistryObject<Block> SKYROOT_BOOKSHELF = register("skyroot_bookshelf", () -> new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF).harvestTool(ToolType.AXE)));

	public static final RegistryObject<BedBlock> SKYROOT_BED = register("skyroot_bed", () -> new SkyrootBedBlock(Block.Properties.copy(Blocks.CYAN_BED).harvestTool(ToolType.AXE)));

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

	public static void registerHoeTillingBlocks() {
		HoeItem.TILLABLES = ImmutableMap.<Block, BlockState>builder()
				.putAll(HoeItem.TILLABLES)
				.put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_FARMLAND.get().defaultBlockState())
				.put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get().defaultBlockState())
				.put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(),
						AetherBlocks.AETHER_FARMLAND.get().defaultBlockState())
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

	private static <B extends Block> RegistryObject<B> baseRegister(String name, Supplier<? extends B> block,
			Function<RegistryObject<B>, Supplier<? extends Item>> item) {
		RegistryObject<B> register = BLOCKS.register(name, block);
		AetherItems.ITEMS.register(name, item.apply(register));
		return register;
	}

	@SuppressWarnings("unchecked")
	private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
		return (RegistryObject<B>) baseRegister(name, block, AetherBlocks::registerBlockItem);
	}

	private static <B extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<B> blockRegistryObject) {
		return () -> {
			B block = Objects.requireNonNull(blockRegistryObject.get());
			if (block == ENCHANTED_AETHER_GRASS_BLOCK.get() 
					|| block == QUICKSOIL_GLASS.get() 
					|| block == ENCHANTED_GRAVITITE.get()) {
				return new BlockItem(block, new Item.Properties().rarity(Rarity.RARE).tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (block == AEROGEL.get() 
					|| block == AEROGEL_WALL.get() 
					|| block == AEROGEL_STAIRS.get() 
					|| block == AEROGEL_SLAB.get()) {
				return new BlockItem(block, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (block == AMBROSIUM_TORCH.get()) {
				return new WallOrFloorItem(AMBROSIUM_TORCH.get(), AMBROSIUM_WALL_TORCH.get(), new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (block == SKYROOT_SIGN.get()) {
				return new SignItem((new Item.Properties()).stacksTo(16).tab(AetherItemGroups.AETHER_BLOCKS), SKYROOT_SIGN.get(), SKYROOT_WALL_SIGN.get());
			}
			else if (block == CHEST_MIMIC.get()) {
				return new BlockItem(block, new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS).setISTER(() -> AetherRendering::chestMimicRenderer));
			}
			else if (block == TREASURE_CHEST.get()) {
				return new BlockItem(block, new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS).setISTER(() -> AetherRendering::treasureChestRenderer));
			}
			else if (block == SKYROOT_PLANKS.get() 
					|| block == SKYROOT_FENCE_GATE.get() 
					|| block == SKYROOT_FENCE.get() 
					|| block == SKYROOT_BOOKSHELF.get()) {
				return new BurnableBlockItem(block, new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (block == SUN_ALTAR.get()) {
				return new BedItem(block, new Item.Properties().fireResistant().tab(AetherItemGroups.AETHER_BLOCKS));
			}
			else if (block == SKYROOT_BED.get()) {
				return new BedItem(block, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_BLOCKS).setISTER(() -> AetherRendering::skyrootBedRenderer));
			}
			else {
				return new BlockItem(block, new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
			}
		};
	}

	private static boolean never(BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_) {
		return false;
	}

	private static boolean always(BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_) {
		return true;
	}

	private static <A> boolean never(BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_, A p_test_4_) {
		return false;
	}

	private static boolean ocelotOrParrot(BlockState p_235441_0_, IBlockReader p_235441_1_, BlockPos p_235441_2_, EntityType<?> p_235441_3_) {
		return p_235441_3_ == EntityType.OCELOT || p_235441_3_ == EntityType.PARROT;
	}
}