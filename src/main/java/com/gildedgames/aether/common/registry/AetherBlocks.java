package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.common.block.construction.*;
import com.gildedgames.aether.common.block.dungeon.ChestMimicBlock;
import com.gildedgames.aether.common.block.dungeon.TrappedBlock;
import com.gildedgames.aether.common.block.dungeon.TreasureChestBlock;
import com.gildedgames.aether.common.block.miscellaneous.AetherPortalBlock;
import com.gildedgames.aether.common.block.natural.*;
import com.gildedgames.aether.common.block.util.*;
import com.gildedgames.aether.common.block.utility.*;
import com.gildedgames.aether.common.block.entity.ChestMimicBlockEntity;
import com.gildedgames.aether.common.block.entity.SkyrootBedBlockEntity;
import com.gildedgames.aether.common.block.entity.TreasureChestBlockEntity;
import com.gildedgames.aether.common.item.block.BurnableBlockItem;
import com.gildedgames.aether.common.item.block.EntityBlockItem;
import com.gildedgames.aether.common.world.gen.tree.GoldenOakTree;
import com.gildedgames.aether.common.world.gen.tree.SkyrootTree;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Aether.MODID);

    public static final RegistryObject<AetherPortalBlock> AETHER_PORTAL = BLOCKS.register("aether_portal", () -> new AetherPortalBlock(Block.Properties.copy(Blocks.NETHER_PORTAL)));

    public static final RegistryObject<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", () -> new AetherGrassBlock(Block.Properties.of(Material.GRASS, MaterialColor.WARPED_WART_BLOCK).randomTicks().strength(0.6F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> ENCHANTED_AETHER_GRASS_BLOCK = register("enchanted_aether_grass_block", () -> new EnchantedAetherGrassBlock(Block.Properties.of(Material.GRASS, MaterialColor.GOLD).randomTicks().strength(0.6F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> AETHER_DIRT = register("aether_dirt", () -> new AetherDoubleDropBlock(Block.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_CYAN).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> QUICKSOIL = register("quicksoil", () -> new AetherDoubleDropBlock(Block.Properties.of(Material.SAND, MaterialColor.COLOR_YELLOW).strength(0.5F).friction(1.1F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> HOLYSTONE = register("holystone", () -> new AetherDoubleDropBlock(Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(0.5F, 6.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOSSY_HOLYSTONE = register("mossy_holystone", () -> new AetherDoubleDropBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE.get())));
    public static final RegistryObject<Block> AETHER_FARMLAND = register("aether_farmland", () -> new AetherFarmlandBlock(Block.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_CYAN).randomTicks().strength(0.6F).sound(SoundType.GRAVEL).isViewBlocking(AetherBlocks::always).isSuffocating(AetherBlocks::always)));

    public static final RegistryObject<Block> COLD_AERCLOUD = register("cold_aercloud", () -> new AercloudBlock(Block.Properties.of(Material.ICE, MaterialColor.SNOW).strength(0.2F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> BLUE_AERCLOUD = register("blue_aercloud", () -> new BlueAercloudBlock(Block.Properties.of(Material.ICE, MaterialColor.COLOR_LIGHT_BLUE).strength(0.2F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> GOLDEN_AERCLOUD = register("golden_aercloud", () -> new AercloudBlock(Block.Properties.of(Material.ICE, MaterialColor.COLOR_YELLOW).strength(0.2F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> PINK_AERCLOUD = register("pink_aercloud", () -> new PinkAercloudBlock(Block.Properties.of(Material.ICE, MaterialColor.COLOR_PINK).strength(0.2F).sound(SoundType.WOOL).noOcclusion()));

    public static final RegistryObject<Block> ICESTONE = register("icestone", () -> new IcestoneBlock(Block.Properties.of(Material.STONE, MaterialColor.ICE).strength(3.0F).randomTicks().sound(SoundType.GLASS).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBROSIUM_ORE = register("ambrosium_ore", () -> new AetherDoubleDropsOreBlock(0, 2, Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ZANITE_ORE = register("zanite_ore", () -> new AetherOreBlock(3, 5, Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRAVITITE_ORE = register("gravitite_ore", () -> new FloatingBlock(false, Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(5.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SKYROOT_LEAVES = register("skyroot_leaves", () -> new AetherDoubleDropsLeaves(Block.Properties.of(Material.LEAVES, MaterialColor.GRASS).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final RegistryObject<Block> GOLDEN_OAK_LEAVES = register("golden_oak_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.GOLDEN_OAK_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.GOLD).strength(0.2F).randomTicks().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> CRYSTAL_LEAVES = register("crystal_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.CRYSTAL_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.DIAMOND).strength(0.2F).randomTicks().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> CRYSTAL_FRUIT_LEAVES = register("crystal_fruit_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.CRYSTAL_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.DIAMOND).strength(0.2F).randomTicks().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> HOLIDAY_LEAVES = register("holiday_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.COLOR_PURPLE).strength(0.2F).randomTicks().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> DECORATED_HOLIDAY_LEAVES = register("decorated_holiday_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, Block.Properties.of(Material.LEAVES, MaterialColor.COLOR_PURPLE).strength(0.2F).randomTicks().sound(SoundType.GRASS)));

    public static final RegistryObject<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", () -> new AetherLogBlock(Block.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> GOLDEN_OAK_LOG = register("golden_oak_log", () -> new AetherLogBlock(Block.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = register("stripped_skyroot_log", () -> new AetherLogBlock(Block.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> SKYROOT_WOOD = register("skyroot_wood", () -> new AetherDoubleDropsRotatedPillarBlock(Block.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<RotatedPillarBlock> GOLDEN_OAK_WOOD = register("golden_oak_wood", () -> new AetherDoubleDropsRotatedPillarBlock(Block.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = register("stripped_skyroot_wood", () -> new AetherDoubleDropsRotatedPillarBlock(Block.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final RegistryObject<Block> SKYROOT_PLANKS = register("skyroot_planks", () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> HOLYSTONE_BRICKS = register("holystone_bricks", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.WOOL).strength(0.5F, 10.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> QUICKSOIL_GLASS = register("quicksoil_glass", () -> new GlassBlock(Block.Properties.of(Material.GLASS, MaterialColor.COLOR_YELLOW).strength(0.3F).friction(1.1F).lightLevel((state) -> 11).sound(SoundType.GLASS).noOcclusion().isValidSpawn(AetherBlocks::never).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final RegistryObject<Block> AEROGEL = register("aerogel", () -> new AerogelBlock(Block.Properties.of(Material.STONE, MaterialColor.DIAMOND).strength(1.0F, 2000.0F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops().isViewBlocking(AetherBlocks::never)));

    public static final RegistryObject<Block> ZANITE_BLOCK = register("zanite_block", () -> new Block(Block.Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistryObject<Block> ENCHANTED_GRAVITITE = register("enchanted_gravitite", () -> new FloatingBlock(true, Block.Properties.of(Material.METAL, MaterialColor.COLOR_PINK).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final RegistryObject<Block> ALTAR = register("altar", () -> new AltarBlock(Block.Properties.of(Material.STONE, MaterialColor.WOOD).strength(2.0F)));
    public static final RegistryObject<Block> FREEZER = register("freezer", () -> new FreezerBlock(Block.Properties.of(Material.STONE, MaterialColor.WOOD).strength(2.0F)));
    public static final RegistryObject<Block> INCUBATOR = register("incubator", () -> new IncubatorBlock(Block.Properties.of(Material.STONE, MaterialColor.WOOD).strength(2.0F)));

    public static final RegistryObject<Block> AMBROSIUM_WALL_TORCH = BLOCKS.register("ambrosium_wall_torch", () -> new WallTorchBlock(Block.Properties.copy(Blocks.WALL_TORCH), ParticleTypes.SMOKE));
    public static final RegistryObject<Block> AMBROSIUM_TORCH = register("ambrosium_torch", () -> new TorchBlock(Block.Properties.copy(Blocks.TORCH), ParticleTypes.SMOKE));

    public static final RegistryObject<Block> SKYROOT_SIGN = register("skyroot_sign", () -> new SkyrootSignBlock(Block.Properties.of(Material.WOOD, MaterialColor.SAND).noCollission().strength(1.0F).sound(SoundType.WOOD), AetherWoodTypes.SKYROOT));
    public static final RegistryObject<Block> SKYROOT_WALL_SIGN = BLOCKS.register("skyroot_wall_sign", () -> new SkyrootWallSignBlock(Block.Properties.of(Material.WOOD, MaterialColor.SAND).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(SKYROOT_SIGN), AetherWoodTypes.SKYROOT));

    public static final RegistryObject<Block> BERRY_BUSH = register("berry_bush", () -> new BerryBushBlock(Block.Properties.of(Material.PLANT, MaterialColor.GRASS).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final RegistryObject<Block> BERRY_BUSH_STEM = register("berry_bush_stem", () -> new BerryBushStemBlock(Block.Properties.of(Material.PLANT, MaterialColor.GRASS).strength(0.2F).sound(SoundType.GRASS).noCollission()));

    public static final RegistryObject<Block> PURPLE_FLOWER = register("purple_flower", () -> new FlowerBlock(MobEffects.SLOW_FALLING, 4, Block.Properties.copy(Blocks.DANDELION)));
    public static final RegistryObject<Block> WHITE_FLOWER = register("white_flower", () -> new AetherFlowerBlock(AetherEffects.INEBRIATION, 12, Block.Properties.copy(Blocks.DANDELION)));
    public static final RegistryObject<FlowerPotBlock> POTTED_PURPLE_FLOWER = BLOCKS.register("potted_purple_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PURPLE_FLOWER, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<FlowerPotBlock> POTTED_WHITE_FLOWER = BLOCKS.register("potted_white_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHITE_FLOWER, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<SaplingBlock> SKYROOT_SAPLING = register("skyroot_sapling", () -> new SaplingBlock(new SkyrootTree(), Block.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<SaplingBlock> GOLDEN_OAK_SAPLING = register("golden_oak_sapling", () -> new SaplingBlock(new GoldenOakTree(), Block.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SKYROOT_SAPLING = BLOCKS.register("potted_skyroot_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYROOT_SAPLING, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<FlowerPotBlock> POTTED_GOLDEN_OAK_SAPLING = BLOCKS.register("potted_golden_oak_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GOLDEN_OAK_SAPLING, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> CARVED_STONE = register("carved_stone", () -> new Block(Block.Properties.of(Material.STONE).strength(0.5F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SENTRY_STONE = register("sentry_stone", () -> new Block(Block.Properties.copy(CARVED_STONE.get()).lightLevel((state) -> 11)));
    public static final RegistryObject<Block> ANGELIC_STONE = register("angelic_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.SAND).strength(0.5F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIGHT_ANGELIC_STONE = register("light_angelic_stone", () -> new Block(Block.Properties.copy(ANGELIC_STONE.get()).lightLevel((state) -> 11)));
    public static final RegistryObject<Block> HELLFIRE_STONE = register("hellfire_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(0.5F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIGHT_HELLFIRE_STONE = register("light_hellfire_stone", () -> new Block(Block.Properties.copy(HELLFIRE_STONE.get()).lightLevel((state) -> 11)));

    public static final RegistryObject<Block> LOCKED_CARVED_STONE = register("locked_carved_stone", () -> new Block(Block.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
    public static final RegistryObject<Block> LOCKED_SENTRY_STONE = register("locked_sentry_stone", () -> new Block(Block.Properties.copy(LOCKED_CARVED_STONE.get()).lightLevel((state) -> 11)));
    public static final RegistryObject<Block> LOCKED_ANGELIC_STONE = register("locked_angelic_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.SAND).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
    public static final RegistryObject<Block> LOCKED_LIGHT_ANGELIC_STONE = register("locked_light_angelic_stone", () -> new Block(Block.Properties.copy(LOCKED_ANGELIC_STONE.get()).lightLevel((state) -> 11)));
    public static final RegistryObject<Block> LOCKED_HELLFIRE_STONE = register("locked_hellfire_stone", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(-1.0F, 3600000.0F).isValidSpawn(AetherBlocks::never)));
    public static final RegistryObject<Block> LOCKED_LIGHT_HELLFIRE_STONE = register("locked_light_hellfire_stone", () -> new Block(Block.Properties.copy(LOCKED_HELLFIRE_STONE.get()).lightLevel((state) -> 11)));

    public static final RegistryObject<Block> TRAPPED_CARVED_STONE = register("trapped_carved_stone", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> LOCKED_CARVED_STONE.get().defaultBlockState(), Block.Properties.copy(LOCKED_CARVED_STONE.get())));
    public static final RegistryObject<Block> TRAPPED_SENTRY_STONE = register("trapped_sentry_stone", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> LOCKED_SENTRY_STONE.get().defaultBlockState(), Block.Properties.copy(LOCKED_SENTRY_STONE.get())));
    public static final RegistryObject<Block> TRAPPED_ANGELIC_STONE = register("trapped_angelic_stone", () -> new Block(Block.Properties.copy(LOCKED_ANGELIC_STONE.get()))); // TODO
    public static final RegistryObject<Block> TRAPPED_LIGHT_ANGELIC_STONE = register("trapped_light_angelic_stone", () -> new Block(Block.Properties.copy(LOCKED_LIGHT_ANGELIC_STONE.get()))); // TODO
    public static final RegistryObject<Block> TRAPPED_HELLFIRE_STONE = register("trapped_hellfire_stone", () -> new Block(Block.Properties.copy(LOCKED_HELLFIRE_STONE.get()))); // TODO
    public static final RegistryObject<Block> TRAPPED_LIGHT_HELLFIRE_STONE = register("trapped_light_hellfire_stone", () -> new Block(Block.Properties.copy(LOCKED_LIGHT_HELLFIRE_STONE.get()))); // TODO

    public static final RegistryObject<Block> CHEST_MIMIC = register("chest_mimic", () -> new ChestMimicBlock(Block.Properties.copy(Blocks.CHEST)));
    public static final RegistryObject<Block> TREASURE_CHEST = register("treasure_chest", () -> new TreasureChestBlock(Block.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F)));

    public static final RegistryObject<RotatedPillarBlock> PILLAR = register("pillar",
            () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, MaterialColor.QUARTZ).strength(0.5F).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final RegistryObject<RotatedPillarBlock> PILLAR_TOP = register("pillar_top",
            () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE, MaterialColor.QUARTZ).strength(0.5F).sound(SoundType.METAL).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> PRESENT = register("present",
            () -> new Block(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).strength(0.6F).sound(SoundType.GRASS)));

    public static final RegistryObject<FenceBlock> SKYROOT_FENCE = register("skyroot_fence", () -> new FenceBlock(Block.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceGateBlock> SKYROOT_FENCE_GATE = register("skyroot_fence_gate", () -> new FenceGateBlock(Block.Properties.copy(Blocks.OAK_FENCE_GATE)));
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
    public static final RegistryObject<WallBlock> AEROGEL_WALL = register("aerogel_wall", () -> new AerogelWallBlock(Block.Properties.copy(AetherBlocks.AEROGEL.get()).isViewBlocking((state, reader, pos) -> false)));

    public static final RegistryObject<StairBlock> SKYROOT_STAIRS = register("skyroot_stairs",
            () -> new StairBlock(() -> SKYROOT_PLANKS.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.SKYROOT_PLANKS.get())));
    public static final RegistryObject<StairBlock> CARVED_STAIRS = register("carved_stairs",
            () -> new StairBlock(() -> CARVED_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.CARVED_STONE.get())));
    public static final RegistryObject<StairBlock> ANGELIC_STAIRS = register("angelic_stairs",
            () -> new StairBlock(() -> ANGELIC_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get())));
    public static final RegistryObject<StairBlock> HELLFIRE_STAIRS = register("hellfire_stairs",
            () -> new StairBlock(() -> HELLFIRE_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get())));
    public static final RegistryObject<StairBlock> HOLYSTONE_STAIRS = register("holystone_stairs",
            () -> new StairBlock(() -> HOLYSTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HOLYSTONE.get())));
    public static final RegistryObject<StairBlock> MOSSY_HOLYSTONE_STAIRS = register("mossy_holystone_stairs",
            () -> new StairBlock(() -> MOSSY_HOLYSTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get())));
    public static final RegistryObject<StairBlock> ICESTONE_STAIRS = register("icestone_stairs",
            () -> new IcestoneStairsBlock(() -> ICESTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.ICESTONE.get())));
    public static final RegistryObject<StairBlock> HOLYSTONE_BRICK_STAIRS = register("holystone_brick_stairs",
            () -> new StairBlock(() -> HOLYSTONE_BRICKS.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get())));
    public static final RegistryObject<StairBlock> AEROGEL_STAIRS = register("aerogel_stairs",
            () -> new AerogelStairsBlock(() -> AEROGEL.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.AEROGEL.get()).isViewBlocking((state, reader, pos) -> false)));

    public static final RegistryObject<SlabBlock> SKYROOT_SLAB = register("skyroot_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.SKYROOT_PLANKS.get()).strength(2.0F, 3.0F)));
    public static final RegistryObject<SlabBlock> CARVED_SLAB = register("carved_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.CARVED_STONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<SlabBlock> ANGELIC_SLAB = register("angelic_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<SlabBlock> HELLFIRE_SLAB = register("hellfire_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get()).strength(0.5F, 6.0F)));
    public static final RegistryObject<SlabBlock> HOLYSTONE_SLAB = register("holystone_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<SlabBlock> MOSSY_HOLYSTONE_SLAB = register("mossy_holystone_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<SlabBlock> ICESTONE_SLAB = register("icestone_slab",
            () -> new IcestoneSlabBlock(Block.Properties.copy(AetherBlocks.ICESTONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<SlabBlock> HOLYSTONE_BRICK_SLAB = register("holystone_brick_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<SlabBlock> AEROGEL_SLAB = register("aerogel_slab",
            () -> new AerogelSlabBlock(Block.Properties.copy(AetherBlocks.AEROGEL.get()).strength(1.5F, 2000.0F).isViewBlocking((state, reader, pos) -> false)));

    public static final RegistryObject<Block> SUN_ALTAR = register("sun_altar",
            () -> new SunAltarBlock(Block.Properties.of(Material.STONE, MaterialColor.NETHER).strength(2.5F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> SKYROOT_BOOKSHELF = register("skyroot_bookshelf", () -> new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF)));

    public static final RegistryObject<BedBlock> SKYROOT_BED = register("skyroot_bed", () -> new SkyrootBedBlock(Block.Properties.copy(Blocks.CYAN_BED)));


    public static void registerPots() {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;

        pot.addPlant(AetherBlocks.PURPLE_FLOWER.getId(), AetherBlocks.POTTED_PURPLE_FLOWER);
        pot.addPlant(AetherBlocks.WHITE_FLOWER.getId(), AetherBlocks.POTTED_WHITE_FLOWER);
        pot.addPlant(AetherBlocks.SKYROOT_SAPLING.getId(), AetherBlocks.POTTED_SKYROOT_SAPLING);
        pot.addPlant(AetherBlocks.GOLDEN_OAK_SAPLING.getId(), AetherBlocks.POTTED_GOLDEN_OAK_SAPLING);
    }

    // See: https://github.com/MinecraftForge/MinecraftForge/blob/eb1a9b57c6cc4d5cb4ad3c4305c2a73015d28917/src/main/java/net/minecraftforge/event/world/BlockEvent.java#L431
    public static void registerAxeStrippingBlocks() {
//		AxeItem.STRIPPABLES = ImmutableMap.<Block, Block>builder()
//				.putAll(AxeItem.STRIPABLES)
//				.put(AetherBlocks.SKYROOT_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
//				.put(AetherBlocks.GOLDEN_OAK_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
//				.put(AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
//				.put(AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
//				.build();
    }

    public static void registerHoeTillingBlocks() {
//		HoeItem.TILLABLES = ImmutableMap.<Block, BlockState>builder()
//				.putAll(HoeItem.TILLABLES)
//				.put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_FARMLAND.get().defaultBlockState())
//				.put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get().defaultBlockState())
//				.put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get().defaultBlockState())
//				.build();
    }

    //See: https://github.com/MinecraftForge/MinecraftForge/blob/eb1a9b57c6cc4d5cb4ad3c4305c2a73015d28917/src/main/java/net/minecraftforge/common/extensions/IForgeBlock.java#L579
    public static void registerFlammability() {
//		FireBlock fireblock = (FireBlock) Blocks.FIRE;
//		fireblock.fla(AetherBlocks.SKYROOT_LEAVES, 30, 60);
//		fireblock.setFlammable(AetherBlocks.GOLDEN_OAK_LEAVES.get(), 30, 60);
//		fireblock.setFlammable(AetherBlocks.CRYSTAL_LEAVES.get(), 30, 60);
//		fireblock.setFlammable(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(), 30, 60);
//		fireblock.setFlammable(AetherBlocks.HOLIDAY_LEAVES.get(), 30, 60);
//		fireblock.setFlammable(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(), 30, 60);
//		fireblock.setFlammable(AetherBlocks.SKYROOT_LOG.get(), 5, 5);
//		fireblock.setFlammable(AetherBlocks.GOLDEN_OAK_LOG.get(), 5, 5);
//		fireblock.setFlammable(AetherBlocks.STRIPPED_SKYROOT_LOG.get(), 5, 5);
//		fireblock.setFlammable(AetherBlocks.SKYROOT_WOOD.get(), 5, 5);
//		fireblock.setFlammable(AetherBlocks.GOLDEN_OAK_WOOD.get(), 5, 5);
//		fireblock.setFlammable(AetherBlocks.STRIPPED_SKYROOT_WOOD.get(), 5, 5);
//		fireblock.setFlammable(AetherBlocks.SKYROOT_PLANKS.get(), 5, 20);
//		fireblock.setFlammable(AetherBlocks.BERRY_BUSH.get(), 30, 60);
//		fireblock.setFlammable(AetherBlocks.BERRY_BUSH_STEM.get(), 60, 100);
//		fireblock.setFlammable(AetherBlocks.PURPLE_FLOWER.get(), 60, 100);
//		fireblock.setFlammable(AetherBlocks.WHITE_FLOWER.get(), 60, 100);
//		fireblock.setFlammable(AetherBlocks.SKYROOT_FENCE_GATE.get(), 5, 20);
//		fireblock.setFlammable(AetherBlocks.SKYROOT_FENCE.get(), 5, 20);
//		fireblock.setFlammable(AetherBlocks.SKYROOT_STAIRS.get(), 5, 20);
//		fireblock.setFlammable(AetherBlocks.SKYROOT_SLAB.get(), 5, 20);
//		fireblock.setFlammable(AetherBlocks.SKYROOT_BOOKSHELF.get(), 30, 20);
    }

    public static void registerWoodTypes() {
        WoodType.register(AetherWoodTypes.SKYROOT);
    }

    public static void registerFreezables() {
        FreezingBehavior.registerDefaultFreezables();
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

    private static <B extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<B> blockRegistryObject) {
        return () -> {
            B block = Objects.requireNonNull(blockRegistryObject.get());
            if (block == ENCHANTED_AETHER_GRASS_BLOCK.get()
                    || block == QUICKSOIL_GLASS.get()
                    || block == ENCHANTED_GRAVITITE.get()) {
                return new BlockItem(block, new Item.Properties().rarity(Rarity.RARE).tab(AetherItemGroups.AETHER_BLOCKS));
            } else if (block == AEROGEL.get()
                    || block == AEROGEL_WALL.get()
                    || block == AEROGEL_STAIRS.get()
                    || block == AEROGEL_SLAB.get()) {
                return new BlockItem(block, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_BLOCKS));
            } else if (block == AMBROSIUM_TORCH.get()) {
                return new StandingAndWallBlockItem(AMBROSIUM_TORCH.get(), AMBROSIUM_WALL_TORCH.get(), new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
            } else if (block == SKYROOT_SIGN.get()) {
                return new SignItem((new Item.Properties()).stacksTo(16).tab(AetherItemGroups.AETHER_BLOCKS), SKYROOT_SIGN.get(), SKYROOT_WALL_SIGN.get());
            } else if (block == CHEST_MIMIC.get()) {
                return new EntityBlockItem(block, ChestMimicBlockEntity::new, new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
            } else if (block == TREASURE_CHEST.get()) {
                return new EntityBlockItem(block, TreasureChestBlockEntity::new, new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
            } else if (block == SKYROOT_PLANKS.get()
                    || block == SKYROOT_FENCE_GATE.get()
                    || block == SKYROOT_FENCE.get()
                    || block == SKYROOT_BOOKSHELF.get()) {
                return new BurnableBlockItem(block, new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
            } else if (block == SUN_ALTAR.get()) {
                return new BlockItem(block, new Item.Properties().fireResistant().tab(AetherItemGroups.AETHER_BLOCKS));
            } else if (block == SKYROOT_BED.get()) {
                return new EntityBlockItem(block, SkyrootBedBlockEntity::new, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_BLOCKS));
            } else {
                return new BlockItem(block, new Item.Properties().tab(AetherItemGroups.AETHER_BLOCKS));
            }
        };
    }

    private static boolean never(BlockState p_test_1_, BlockGetter p_test_2_, BlockPos p_test_3_) {
        return false;
    }

    private static boolean always(BlockState p_test_1_, BlockGetter p_test_2_, BlockPos p_test_3_) {
        return true;
    }

    private static <A> boolean never(BlockState p_test_1_, BlockGetter p_test_2_, BlockPos p_test_3_, A p_test_4_) {
        return false;
    }

    private static boolean ocelotOrParrot(BlockState p_235441_0_, BlockGetter p_235441_1_, BlockPos p_235441_2_, EntityType<?> p_235441_3_) {
        return p_235441_3_ == EntityType.OCELOT || p_235441_3_ == EntityType.PARROT;
    }
}