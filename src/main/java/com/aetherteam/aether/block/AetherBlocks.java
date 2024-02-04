package com.aetherteam.aether.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.construction.*;
import com.aetherteam.aether.block.dungeon.*;
import com.aetherteam.aether.block.miscellaneous.AetherFrostedIceBlock;
import com.aetherteam.aether.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aether.block.miscellaneous.FloatingBlock;
import com.aetherteam.aether.block.miscellaneous.UnstableObsidianBlock;
import com.aetherteam.aether.block.natural.*;
import com.aetherteam.aether.block.portal.AetherPortalBlock;
import com.aetherteam.aether.block.utility.*;
import com.aetherteam.aether.blockentity.*;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.block.AmbrosiumBlockItem;
import com.aetherteam.aether.item.block.BurnableBlockItem;
import com.aetherteam.aether.mixin.mixins.common.accessor.FireBlockAccessor;
import com.aetherteam.aether.world.treegrower.GoldenOakTree;
import com.aetherteam.aether.world.treegrower.SkyrootTree;
import com.aetherteam.nitrogen.item.block.EntityBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Aether.MODID);

    public static final Supplier<AetherPortalBlock> AETHER_PORTAL = BLOCKS.register("aether_portal", () -> new AetherPortalBlock(Block.Properties.of().noCollission().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(AetherBlocks::lightLevel11).pushReaction(PushReaction.BLOCK).forceSolidOn()));

    public static final Supplier<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", () -> new AetherGrassBlock(Block.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).randomTicks().strength(0.2F).sound(SoundType.GRASS)));
    public static final Supplier<Block> ENCHANTED_AETHER_GRASS_BLOCK = register("enchanted_aether_grass_block", () -> new EnchantedAetherGrassBlock(Block.Properties.of().mapColor(MapColor.GOLD).randomTicks().strength(0.2F).sound(SoundType.GRASS)));
    public static final Supplier<Block> AETHER_DIRT = register("aether_dirt", () -> new AetherDoubleDropBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.2F).sound(SoundType.GRAVEL)));
    public static final Supplier<Block> QUICKSOIL = register("quicksoil", () -> new QuicksoilBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5F).friction(1.1F).sound(SoundType.SAND)));
    public static final Supplier<Block> HOLYSTONE = register("holystone", () -> new AetherDoubleDropBlock(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F).requiresCorrectToolForDrops()));
    public static final Supplier<Block> MOSSY_HOLYSTONE = register("mossy_holystone", () -> new AetherDoubleDropBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE.get())));
    public static final Supplier<Block> AETHER_FARMLAND = register("aether_farmland", () -> new AetherFarmBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).randomTicks().strength(0.2F).sound(SoundType.GRAVEL).isViewBlocking(AetherBlocks::always).isSuffocating(AetherBlocks::always)));
    public static final Supplier<Block> AETHER_DIRT_PATH = register("aether_dirt_path", () -> new AetherDirtPathBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.2F).sound(SoundType.GRASS).isViewBlocking(AetherBlocks::always).isSuffocating(AetherBlocks::always)));

    public static final Supplier<Block> COLD_AERCLOUD = register("cold_aercloud", () -> new AercloudBlock(Block.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.FLUTE).strength(0.3F).sound(SoundType.WOOL).noOcclusion().dynamicShape().isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<Block> BLUE_AERCLOUD = register("blue_aercloud", () -> new BlueAercloudBlock(Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.FLUTE).strength(0.3F).sound(SoundType.WOOL).noOcclusion().dynamicShape().isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<Block> GOLDEN_AERCLOUD = register("golden_aercloud", () -> new AercloudBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.FLUTE).strength(0.3F).sound(SoundType.WOOL).noOcclusion().dynamicShape().isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));

    public static final Supplier<Block> ICESTONE = register("icestone", () -> new IcestoneBlock(Block.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).strength(0.5F).randomTicks().sound(SoundType.GLASS).requiresCorrectToolForDrops()));
    public static final Supplier<Block> AMBROSIUM_ORE = register("ambrosium_ore", () -> new AetherDoubleDropsOreBlock(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
    public static final Supplier<Block> ZANITE_ORE = register("zanite_ore", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).requiresCorrectToolForDrops(), UniformInt.of(3, 5)));
    public static final Supplier<Block> GRAVITITE_ORE = register("gravitite_ore", () -> new FloatingBlock(false, Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).randomTicks().requiresCorrectToolForDrops()));

    public static final Supplier<Block> SKYROOT_LEAVES = register("skyroot_leaves", () -> new AetherDoubleDropsLeaves(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<Block> GOLDEN_OAK_LEAVES = register("golden_oak_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.GOLDEN_OAK_LEAVES, Block.Properties.of().mapColor(MapColor.GOLD).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<Block> CRYSTAL_LEAVES = register("crystal_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.CRYSTAL_LEAVES, Block.Properties.of().mapColor(MapColor.DIAMOND).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<Block> CRYSTAL_FRUIT_LEAVES = register("crystal_fruit_leaves", () -> new CrystalFruitLeavesBlock(AetherParticleTypes.CRYSTAL_LEAVES, Block.Properties.of().mapColor(MapColor.DIAMOND).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<Block> HOLIDAY_LEAVES = register("holiday_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<Block> DECORATED_HOLIDAY_LEAVES = register("decorated_holiday_leaves", () -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));

    public static final Supplier<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", () -> new AetherLogBlock(Block.Properties.copy(Blocks.OAK_LOG)));
    public static final Supplier<RotatedPillarBlock> GOLDEN_OAK_LOG = register("golden_oak_log", () -> new AetherLogBlock(Block.Properties.copy(Blocks.OAK_LOG)));
    public static final Supplier<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = register("stripped_skyroot_log", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final Supplier<RotatedPillarBlock> SKYROOT_WOOD = register("skyroot_wood", () -> new AetherLogBlock(Block.Properties.copy(Blocks.OAK_WOOD)));
    public static final Supplier<RotatedPillarBlock> GOLDEN_OAK_WOOD = register("golden_oak_wood", () -> new AetherLogBlock(Block.Properties.copy(Blocks.OAK_WOOD)));
    public static final Supplier<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = register("stripped_skyroot_wood", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final Supplier<Block> SKYROOT_PLANKS = register("skyroot_planks", () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> HOLYSTONE_BRICKS = register("holystone_bricks", () -> new Block(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F, 10.0F).requiresCorrectToolForDrops()));
    public static final Supplier<GlassBlock> QUICKSOIL_GLASS = register("quicksoil_glass", () -> new QuicksoilGlassBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion().isValidSpawn(AetherBlocks::never).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<IronBarsBlock> QUICKSOIL_GLASS_PANE = register("quicksoil_glass_pane", () -> new QuicksoilGlassPaneBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion()));
    public static final Supplier<Block> AEROGEL = register("aerogel", () -> new AerogelBlock(Block.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(1.0F, 2000.0F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops().isViewBlocking(AetherBlocks::never)));

    public static final Supplier<Block> AMBROSIUM_BLOCK = register("ambrosium_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final Supplier<Block> ZANITE_BLOCK = register("zanite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BIT).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final Supplier<Block> ENCHANTED_GRAVITITE = register("enchanted_gravitite", () -> new FloatingBlock(true, Block.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final Supplier<Block> ALTAR = register("altar", () -> new AltarBlock(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5F)));
    public static final Supplier<Block> FREEZER = register("freezer", () -> new FreezerBlock(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F)));
    public static final Supplier<Block> INCUBATOR = register("incubator", () -> new IncubatorBlock(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F)));

    public static final Supplier<Block> AMBROSIUM_WALL_TORCH = BLOCKS.register("ambrosium_wall_torch", () -> new WallTorchBlock(Block.Properties.copy(Blocks.WALL_TORCH), ParticleTypes.SMOKE));
    public static final Supplier<Block> AMBROSIUM_TORCH = register("ambrosium_torch", () -> new TorchBlock(Block.Properties.copy(Blocks.TORCH), ParticleTypes.SMOKE));

    public static final Supplier<StandingSignBlock> SKYROOT_SIGN = register("skyroot_sign", () -> new SkyrootSignBlock(Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), AetherWoodTypes.SKYROOT));
    public static final Supplier<WallSignBlock> SKYROOT_WALL_SIGN = BLOCKS.register("skyroot_wall_sign", () -> new SkyrootWallSignBlock(Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(SKYROOT_SIGN), AetherWoodTypes.SKYROOT));
    public static final Supplier<CeilingHangingSignBlock> SKYROOT_HANGING_SIGN = register("skyroot_hanging_sign", () -> new SkyrootCeilingHangingSignBlock(BlockBehaviour.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), AetherWoodTypes.SKYROOT));
    public static final Supplier<WallHangingSignBlock> SKYROOT_WALL_HANGING_SIGN = BLOCKS.register("skyroot_wall_hanging_sign", () -> new SkyrootWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), AetherWoodTypes.SKYROOT));

    public static final Supplier<Block> BERRY_BUSH = register("berry_bush", () -> new BerryBushBlock(Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never)));
    public static final Supplier<Block> BERRY_BUSH_STEM = register("berry_bush_stem", () -> new BerryBushStemBlock(Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.2F).sound(SoundType.GRASS).noCollission()));
    public static final Supplier<FlowerPotBlock> POTTED_BERRY_BUSH = BLOCKS.register("potted_berry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BERRY_BUSH, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final Supplier<FlowerPotBlock> POTTED_BERRY_BUSH_STEM = BLOCKS.register("potted_berry_bush_stem", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BERRY_BUSH_STEM, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static final Supplier<Block> PURPLE_FLOWER = register("purple_flower", () -> new FlowerBlock(AetherEffects.INEBRIATION, 12, Block.Properties.copy(Blocks.DANDELION)));
    public static final Supplier<Block> WHITE_FLOWER = register("white_flower", () -> new FlowerBlock(() -> MobEffects.SLOW_FALLING, 4, Block.Properties.copy(Blocks.DANDELION)));
    public static final Supplier<FlowerPotBlock> POTTED_PURPLE_FLOWER = BLOCKS.register("potted_purple_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PURPLE_FLOWER, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final Supplier<FlowerPotBlock> POTTED_WHITE_FLOWER = BLOCKS.register("potted_white_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHITE_FLOWER, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static final Supplier<SaplingBlock> SKYROOT_SAPLING = register("skyroot_sapling", () -> new SaplingBlock(new SkyrootTree(), Block.Properties.copy(Blocks.OAK_SAPLING)));
    public static final Supplier<SaplingBlock> GOLDEN_OAK_SAPLING = register("golden_oak_sapling", () -> new SaplingBlock(new GoldenOakTree(), Block.Properties.copy(Blocks.OAK_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_SKYROOT_SAPLING = BLOCKS.register("potted_skyroot_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYROOT_SAPLING, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final Supplier<FlowerPotBlock> POTTED_GOLDEN_OAK_SAPLING = BLOCKS.register("potted_golden_oak_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GOLDEN_OAK_SAPLING, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static final Supplier<Block> CARVED_STONE = register("carved_stone", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final Supplier<Block> SENTRY_STONE = register("sentry_stone", () -> new Block(Block.Properties.copy(CARVED_STONE.get()).lightLevel(AetherBlocks::lightLevel11)));
    public static final Supplier<Block> ANGELIC_STONE = register("angelic_stone", () -> new Block(Block.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final Supplier<Block> LIGHT_ANGELIC_STONE = register("light_angelic_stone", () -> new Block(Block.Properties.copy(ANGELIC_STONE.get()).lightLevel(AetherBlocks::lightLevel11)));
    public static final Supplier<Block> HELLFIRE_STONE = register("hellfire_stone", () -> new Block(Block.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final Supplier<Block> LIGHT_HELLFIRE_STONE = register("light_hellfire_stone", () -> new Block(Block.Properties.copy(HELLFIRE_STONE.get()).lightLevel(AetherBlocks::lightLevel11)));

    public static final Supplier<Block> LOCKED_CARVED_STONE = register("locked_carved_stone", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)));
    public static final Supplier<Block> LOCKED_SENTRY_STONE = register("locked_sentry_stone", () -> new Block(Block.Properties.copy(LOCKED_CARVED_STONE.get()).lightLevel(AetherBlocks::lightLevel11)));
    public static final Supplier<Block> LOCKED_ANGELIC_STONE = register("locked_angelic_stone", () -> new Block(Block.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)));
    public static final Supplier<Block> LOCKED_LIGHT_ANGELIC_STONE = register("locked_light_angelic_stone", () -> new Block(Block.Properties.copy(LOCKED_ANGELIC_STONE.get()).lightLevel(AetherBlocks::lightLevel11)));
    public static final Supplier<Block> LOCKED_HELLFIRE_STONE = register("locked_hellfire_stone", () -> new Block(Block.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)));
    public static final Supplier<Block> LOCKED_LIGHT_HELLFIRE_STONE = register("locked_light_hellfire_stone", () -> new Block(Block.Properties.copy(LOCKED_HELLFIRE_STONE.get()).lightLevel(AetherBlocks::lightLevel11)));

    public static final Supplier<Block> TRAPPED_CARVED_STONE = register("trapped_carved_stone", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> CARVED_STONE.get().defaultBlockState(), Block.Properties.copy(CARVED_STONE.get())));
    public static final Supplier<Block> TRAPPED_SENTRY_STONE = register("trapped_sentry_stone", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> SENTRY_STONE.get().defaultBlockState(), Block.Properties.copy(SENTRY_STONE.get())));
    public static final Supplier<Block> TRAPPED_ANGELIC_STONE = register("trapped_angelic_stone", () -> new TrappedBlock(AetherEntityTypes.VALKYRIE::get, () -> LOCKED_ANGELIC_STONE.get().defaultBlockState(), Block.Properties.copy(LOCKED_ANGELIC_STONE.get())));
    public static final Supplier<Block> TRAPPED_LIGHT_ANGELIC_STONE = register("trapped_light_angelic_stone", () -> new TrappedBlock(AetherEntityTypes.VALKYRIE::get, () -> LOCKED_LIGHT_ANGELIC_STONE.get().defaultBlockState(), Block.Properties.copy(LOCKED_LIGHT_ANGELIC_STONE.get())));
    public static final Supplier<Block> TRAPPED_HELLFIRE_STONE = register("trapped_hellfire_stone", () -> new TrappedBlock(AetherEntityTypes.FIRE_MINION::get, () -> LOCKED_HELLFIRE_STONE.get().defaultBlockState(), Block.Properties.copy(LOCKED_HELLFIRE_STONE.get())));
    public static final Supplier<Block> TRAPPED_LIGHT_HELLFIRE_STONE = register("trapped_light_hellfire_stone", () -> new TrappedBlock(AetherEntityTypes.FIRE_MINION::get, () -> LOCKED_LIGHT_HELLFIRE_STONE.get().defaultBlockState(), Block.Properties.copy(LOCKED_LIGHT_HELLFIRE_STONE.get())));

    public static final Supplier<Block> BOSS_DOORWAY_CARVED_STONE = register("boss_doorway_carved_stone", () -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).forceSolidOn()));
    public static final Supplier<Block> BOSS_DOORWAY_SENTRY_STONE = register("boss_doorway_sentry_stone", () -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, BlockBehaviour.Properties.copy(BOSS_DOORWAY_CARVED_STONE.get())));
    public static final Supplier<Block> BOSS_DOORWAY_ANGELIC_STONE = register("boss_doorway_angelic_stone", () -> new DoorwayBlock(AetherEntityTypes.VALKYRIE_QUEEN::get, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).forceSolidOn()));
    public static final Supplier<Block> BOSS_DOORWAY_LIGHT_ANGELIC_STONE = register("boss_doorway_light_angelic_stone", () -> new DoorwayBlock(AetherEntityTypes.VALKYRIE_QUEEN::get, BlockBehaviour.Properties.copy(BOSS_DOORWAY_ANGELIC_STONE.get())));
    public static final Supplier<Block> BOSS_DOORWAY_HELLFIRE_STONE = register("boss_doorway_hellfire_stone", () -> new DoorwayBlock(AetherEntityTypes.SUN_SPIRIT::get, BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).forceSolidOn()));
    public static final Supplier<Block> BOSS_DOORWAY_LIGHT_HELLFIRE_STONE = register("boss_doorway_light_hellfire_stone", () -> new DoorwayBlock(AetherEntityTypes.SUN_SPIRIT::get, BlockBehaviour.Properties.copy(BOSS_DOORWAY_HELLFIRE_STONE.get())));

    public static final Supplier<Block> TREASURE_DOORWAY_CARVED_STONE = register("treasure_doorway_carved_stone", () -> new TreasureDoorwayBlock(BlockBehaviour.Properties.copy(LOCKED_CARVED_STONE.get())));
    public static final Supplier<Block> TREASURE_DOORWAY_SENTRY_STONE = register("treasure_doorway_sentry_stone", () -> new TreasureDoorwayBlock(BlockBehaviour.Properties.copy(LOCKED_SENTRY_STONE.get())));
    public static final Supplier<Block> TREASURE_DOORWAY_ANGELIC_STONE = register("treasure_doorway_angelic_stone", () -> new TreasureDoorwayBlock(BlockBehaviour.Properties.copy(LOCKED_ANGELIC_STONE.get())));
    public static final Supplier<Block> TREASURE_DOORWAY_LIGHT_ANGELIC_STONE = register("treasure_doorway_light_angelic_stone", () -> new TreasureDoorwayBlock(BlockBehaviour.Properties.copy(LOCKED_LIGHT_ANGELIC_STONE.get())));
    public static final Supplier<Block> TREASURE_DOORWAY_HELLFIRE_STONE = register("treasure_doorway_hellfire_stone", () -> new TreasureDoorwayBlock(BlockBehaviour.Properties.copy(LOCKED_HELLFIRE_STONE.get())));
    public static final Supplier<Block> TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE = register("treasure_doorway_light_hellfire_stone", () -> new TreasureDoorwayBlock(BlockBehaviour.Properties.copy(LOCKED_LIGHT_HELLFIRE_STONE.get())));

    public static final Supplier<Block> CHEST_MIMIC = register("chest_mimic", () -> new ChestMimicBlock(Block.Properties.copy(Blocks.CHEST)));
    public static final Supplier<Block> TREASURE_CHEST = register("treasure_chest", () -> new TreasureChestBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).requiresCorrectToolForDrops()));

    public static final Supplier<RotatedPillarBlock> PILLAR = register("pillar",
            () -> new RotatedPillarBlock(Block.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final Supplier<FacingPillarBlock> PILLAR_TOP = register("pillar_top",
            () -> new FacingPillarBlock(Block.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F).sound(SoundType.METAL).requiresCorrectToolForDrops()));

    public static final Supplier<Block> PRESENT = register("present",
            () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BELL).strength(0.1F).sound(SoundType.WOOL)));

    public static final Supplier<FenceBlock> SKYROOT_FENCE = register("skyroot_fence", () -> new FenceBlock(Block.Properties.copy(Blocks.OAK_FENCE)));
    public static final Supplier<FenceGateBlock> SKYROOT_FENCE_GATE = register("skyroot_fence_gate", () -> new FenceGateBlock(Block.Properties.copy(Blocks.OAK_FENCE_GATE), AetherWoodTypes.SKYROOT));
    public static final Supplier<DoorBlock> SKYROOT_DOOR = register("skyroot_door", () -> new DoorBlock(Block.Properties.copy(Blocks.OAK_DOOR), AetherWoodTypes.SKYROOT_BLOCK_SET));
    public static final Supplier<TrapDoorBlock> SKYROOT_TRAPDOOR = register("skyroot_trapdoor", () -> new TrapDoorBlock(Block.Properties.copy(Blocks.OAK_TRAPDOOR), AetherWoodTypes.SKYROOT_BLOCK_SET));
    public static final Supplier<ButtonBlock> SKYROOT_BUTTON = register("skyroot_button", () -> new ButtonBlock(Block.Properties.copy(Blocks.OAK_BUTTON), AetherWoodTypes.SKYROOT_BLOCK_SET, 30, true));
    public static final Supplier<PressurePlateBlock> SKYROOT_PRESSURE_PLATE = register("skyroot_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(Blocks.OAK_PRESSURE_PLATE), AetherWoodTypes.SKYROOT_BLOCK_SET));

    public static final Supplier<ButtonBlock> HOLYSTONE_BUTTON = register("holystone_button", () -> new ButtonBlock(Block.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.STONE, 20, false));
    public static final Supplier<PressurePlateBlock> HOLYSTONE_PRESSURE_PLATE = register("holystone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(0.5F), BlockSetType.STONE));

    public static final Supplier<WallBlock> CARVED_WALL = register("carved_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.CARVED_STONE.get()).forceSolidOn()));
    public static final Supplier<WallBlock> ANGELIC_WALL = register("angelic_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get()).forceSolidOn()));
    public static final Supplier<WallBlock> HELLFIRE_WALL = register("hellfire_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get()).forceSolidOn()));
    public static final Supplier<WallBlock> HOLYSTONE_WALL = register("holystone_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE.get()).forceSolidOn()));
    public static final Supplier<WallBlock> MOSSY_HOLYSTONE_WALL = register("mossy_holystone_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).forceSolidOn()));
    public static final Supplier<WallBlock> ICESTONE_WALL = register("icestone_wall", () -> new IcestoneWallBlock(Block.Properties.copy(AetherBlocks.ICESTONE.get()).forceSolidOn()));
    public static final Supplier<WallBlock> HOLYSTONE_BRICK_WALL = register("holystone_brick_wall", () -> new WallBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get()).forceSolidOn()));
    public static final Supplier<WallBlock> AEROGEL_WALL = register("aerogel_wall", () -> new AerogelWallBlock(Block.Properties.of().mapColor(MapColor.DIAMOND).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).strength(1.0F, 2000.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().isViewBlocking(AetherBlocks::never)));

    public static final Supplier<StairBlock> SKYROOT_STAIRS = register("skyroot_stairs",
            () -> new StairBlock(() -> SKYROOT_PLANKS.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.SKYROOT_PLANKS.get())));
    public static final Supplier<StairBlock> CARVED_STAIRS = register("carved_stairs",
            () -> new StairBlock(() -> CARVED_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.CARVED_STONE.get())));
    public static final Supplier<StairBlock> ANGELIC_STAIRS = register("angelic_stairs",
            () -> new StairBlock(() -> ANGELIC_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get())));
    public static final Supplier<StairBlock> HELLFIRE_STAIRS = register("hellfire_stairs",
            () -> new StairBlock(() -> HELLFIRE_STONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get())));
    public static final Supplier<StairBlock> HOLYSTONE_STAIRS = register("holystone_stairs",
            () -> new StairBlock(() -> HOLYSTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HOLYSTONE.get())));
    public static final Supplier<StairBlock> MOSSY_HOLYSTONE_STAIRS = register("mossy_holystone_stairs",
            () -> new StairBlock(() -> MOSSY_HOLYSTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get())));
    public static final Supplier<StairBlock> ICESTONE_STAIRS = register("icestone_stairs",
            () -> new IcestoneStairsBlock(() -> ICESTONE.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.ICESTONE.get())));
    public static final Supplier<StairBlock> HOLYSTONE_BRICK_STAIRS = register("holystone_brick_stairs",
            () -> new StairBlock(() -> HOLYSTONE_BRICKS.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get())));
    public static final Supplier<StairBlock> AEROGEL_STAIRS = register("aerogel_stairs",
            () -> new AerogelStairsBlock(() -> AEROGEL.get().defaultBlockState(), Block.Properties.copy(AetherBlocks.AEROGEL.get()).isViewBlocking(AetherBlocks::never)));

    public static final Supplier<SlabBlock> SKYROOT_SLAB = register("skyroot_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.SKYROOT_PLANKS.get()).strength(2.0F, 3.0F)));
    public static final Supplier<SlabBlock> CARVED_SLAB = register("carved_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.CARVED_STONE.get()).strength(0.5F, 6.0F)));
    public static final Supplier<SlabBlock> ANGELIC_SLAB = register("angelic_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.ANGELIC_STONE.get()).strength(0.5F, 6.0F)));
    public static final Supplier<SlabBlock> HELLFIRE_SLAB = register("hellfire_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HELLFIRE_STONE.get()).strength(0.5F, 6.0F)));
    public static final Supplier<SlabBlock> HOLYSTONE_SLAB = register("holystone_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE.get()).strength(0.5F, 6.0F)));
    public static final Supplier<SlabBlock> MOSSY_HOLYSTONE_SLAB = register("mossy_holystone_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).strength(0.5F, 6.0F)));
    public static final Supplier<SlabBlock> ICESTONE_SLAB = register("icestone_slab",
            () -> new IcestoneSlabBlock(Block.Properties.copy(AetherBlocks.ICESTONE.get()).strength(0.5F, 6.0F)));
    public static final Supplier<SlabBlock> HOLYSTONE_BRICK_SLAB = register("holystone_brick_slab",
            () -> new SlabBlock(Block.Properties.copy(AetherBlocks.HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F)));
    public static final Supplier<SlabBlock> AEROGEL_SLAB = register("aerogel_slab",
            () -> new AerogelSlabBlock(Block.Properties.copy(AetherBlocks.AEROGEL.get()).strength(1.0F, 2000.0F).isViewBlocking(AetherBlocks::never)));

    public static final Supplier<Block> SUN_ALTAR = register("sun_altar",
            () -> new SunAltarBlock(Block.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F).sound(SoundType.METAL)));

    public static final Supplier<Block> SKYROOT_BOOKSHELF = register("skyroot_bookshelf", () -> new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF)));

    public static final Supplier<BedBlock> SKYROOT_BED = register("skyroot_bed", () -> new SkyrootBedBlock(Block.Properties.copy(Blocks.CYAN_BED)));

    public static final Supplier<Block> FROSTED_ICE = BLOCKS.register("frosted_ice", () -> new AetherFrostedIceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((state, level, pos, entityType) -> entityType == EntityType.POLAR_BEAR).isRedstoneConductor(AetherBlocks::never)));
    public static final Supplier<Block> UNSTABLE_OBSIDIAN = BLOCKS.register("unstable_obsidian", () -> new UnstableObsidianBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).randomTicks().requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));

    public static void registerFuels() {
        AltarBlockEntity.addItemEnchantingTime(AetherItems.AMBROSIUM_SHARD.get(), 250);
        AltarBlockEntity.addItemEnchantingTime(AetherBlocks.AMBROSIUM_BLOCK.get(), 2500);
        FreezerBlockEntity.addItemFreezingTime(AetherBlocks.ICESTONE.get(), 400);
        FreezerBlockEntity.addItemFreezingTime(AetherBlocks.ICESTONE_SLAB.get(), 200);
        FreezerBlockEntity.addItemFreezingTime(AetherBlocks.ICESTONE_STAIRS.get(), 400);
        FreezerBlockEntity.addItemFreezingTime(AetherBlocks.ICESTONE_WALL.get(), 400);
        IncubatorBlockEntity.addItemIncubatingTime(AetherBlocks.AMBROSIUM_TORCH.get(), 500);
    }

    public static void registerPots() {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(AetherBlocks.BERRY_BUSH.getId(), AetherBlocks.POTTED_BERRY_BUSH);
        pot.addPlant(AetherBlocks.BERRY_BUSH_STEM.getId(), AetherBlocks.POTTED_BERRY_BUSH_STEM);
        pot.addPlant(AetherBlocks.PURPLE_FLOWER.getId(), AetherBlocks.POTTED_PURPLE_FLOWER);
        pot.addPlant(AetherBlocks.WHITE_FLOWER.getId(), AetherBlocks.POTTED_WHITE_FLOWER);
        pot.addPlant(AetherBlocks.SKYROOT_SAPLING.getId(), AetherBlocks.POTTED_SKYROOT_SAPLING);
        pot.addPlant(AetherBlocks.GOLDEN_OAK_SAPLING.getId(), AetherBlocks.POTTED_GOLDEN_OAK_SAPLING);
    }

    public static void registerFlammability() {
        FireBlockAccessor fireBlockAccessor = (FireBlockAccessor) Blocks.FIRE;
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.GOLDEN_OAK_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.CRYSTAL_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.HOLIDAY_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.GOLDEN_OAK_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.STRIPPED_SKYROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.GOLDEN_OAK_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.STRIPPED_SKYROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.BERRY_BUSH.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.BERRY_BUSH_STEM.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherBlocks.PURPLE_FLOWER.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherBlocks.WHITE_FLOWER.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_FENCE_GATE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_FENCE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_STAIRS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_SLAB.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_BOOKSHELF.get(), 30, 20);
    }

    public static void registerFluidInteractions() {
        FluidInteractionRegistry.addInteraction(NeoForgeMod.WATER_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(AetherBlocks.QUICKSOIL.get()) && level.getBlockState(relativePos).is(Blocks.MAGMA_BLOCK),
                AetherBlocks.HOLYSTONE.get().defaultBlockState()
        ));
    }

    public static void registerWoodTypes() {
        WoodType.register(AetherWoodTypes.SKYROOT);
    }

    private static <T extends Block> Supplier<T> baseRegister(String name, Supplier<? extends T> block, Function<Supplier<T>, Supplier<? extends Item>> item) {
        Supplier<T> register = BLOCKS.register(name, block);
        AetherItems.ITEMS.register(name, item.apply(register));
        return register;
    }

    @SuppressWarnings("unchecked")
    private static <B extends Block> Supplier<B> register(String name, Supplier<? extends Block> block) {
        return (Supplier<B>) baseRegister(name, block, AetherBlocks::registerBlockItem);
    }

    private static <B extends Block> Supplier<BlockItem> registerBlockItem(final Supplier<B> blockSupplier) {
        return () -> {
            B block = Objects.requireNonNull(blockSupplier.get());
            if (block == ENCHANTED_AETHER_GRASS_BLOCK.get()
                    || block == QUICKSOIL_GLASS.get()
                    || block == QUICKSOIL_GLASS_PANE.get()
                    || block == ENCHANTED_GRAVITITE.get()) {
                return new BlockItem(block, new Item.Properties().rarity(Rarity.RARE));
            } else if (block == AMBROSIUM_BLOCK.get()) {
                return new AmbrosiumBlockItem(block, new Item.Properties());
            } else if (block == AEROGEL.get()
                    || block == AEROGEL_WALL.get()
                    || block == AEROGEL_STAIRS.get()
                    || block == AEROGEL_SLAB.get()) {
                return new BlockItem(block, new Item.Properties().rarity(AetherItems.AETHER_LOOT));
            } else if (block == AMBROSIUM_TORCH.get()) {
                return new StandingAndWallBlockItem(AMBROSIUM_TORCH.get(), AMBROSIUM_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN);
            } else if (block == SKYROOT_SIGN.get()) {
                return new SignItem(new Item.Properties().stacksTo(16), SKYROOT_SIGN.get(), SKYROOT_WALL_SIGN.get());
            } else if (block == SKYROOT_HANGING_SIGN.get()) {
                return new HangingSignItem(SKYROOT_HANGING_SIGN.get(), SKYROOT_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16));
            } else if (block == CHEST_MIMIC.get()) {
                return new EntityBlockItem(block, ChestMimicBlockEntity::new, new Item.Properties());
            } else if (block == TREASURE_CHEST.get()) {
                return new EntityBlockItem(block, TreasureChestBlockEntity::new, new Item.Properties());
            } else if (block == SKYROOT_PLANKS.get()
                    || block == SKYROOT_FENCE_GATE.get()
                    || block == SKYROOT_FENCE.get()
                    || block == SKYROOT_BOOKSHELF.get()) {
                return new BurnableBlockItem(block, new Item.Properties());
            } else if (block == SUN_ALTAR.get()) {
                return new BlockItem(block, new Item.Properties().fireResistant());
            } else if (block == SKYROOT_BED.get()) {
                return new EntityBlockItem(block, SkyrootBedBlockEntity::new, new Item.Properties().stacksTo(1));
            } else {
                return new BlockItem(block, new Item.Properties());
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

    private static int lightLevel11(BlockState state) {
        return 11;
    }
}