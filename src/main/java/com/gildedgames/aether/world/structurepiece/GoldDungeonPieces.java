package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.data.resources.registries.AetherConfiguredFeatures;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.SunSpirit;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.processor.NoReplaceProcessor;
import com.gildedgames.aether.world.processor.VerticalGradientProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Class for all the pieces of the gold dungeon.
 */
public class GoldDungeonPieces {
    public static final RuleProcessor LOCKED_HELLFIRE_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_HELLFIRE_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get().defaultBlockState())
    ));
    public static final RuleProcessor MOSSY_HOLYSTONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.HOLYSTONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState())
    ));

    /**
     * A room inside the island. This should contain the sun spirit.
     */
    public static class BossRoom extends GoldDungeonPiece {

        public BossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), manager, name, makeSettingsWithPivot(manager, name).setRotation(rotation), pos);
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettingsWithPivot(StructureTemplateManager templateManager, String name) {
            StructurePlaceSettings settings = makeSettings();
            StructureTemplate template = templateManager.getOrCreate(new ResourceLocation(Aether.MODID, "gold_dungeon/" + name));
            Vec3i size = template.getSize();
            int xOffset = ((size.getX()) >> 1);
            int zOffset = ((size.getZ()) >> 1);
            BlockPos pivot = new BlockPos(xOffset, 0, zOffset);
            settings.setRotationPivot(pivot);
            return settings;
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(LOCKED_HELLFIRE_STONE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB) {
            if (name.equals("Sun Spirit")) {
                SunSpirit sunSpirit = new SunSpirit(AetherEntityTypes.SUN_SPIRIT.get(), level.getLevel());
                sunSpirit.setPersistenceRequired();
                sunSpirit.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                sunSpirit.setDungeon(new DungeonTracker<>(sunSpirit,
                        sunSpirit.position(),
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() + 1, this.boundingBox.maxY(), this.boundingBox.maxZ() + 1),
                        new ArrayList<>()));
                sunSpirit.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.getLevel().addFreshEntity(sunSpirit);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            } else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.GOLD_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "gold"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    /**
     * The chunks of land surrounding the boss room to form an island.
     */
    public static class Island extends GoldDungeonPiece {

        public Island(StructureTemplateManager manager, String name, BlockPos pos) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), manager, name, makeSettings(), pos);
        }

        public Island(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(VerticalGradientProcessor.INSTANCE);
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos pChunkPos, BlockPos pPos) {
            super.postProcess(level, structureManager, generator, random, chunkBox, pChunkPos, pPos);
            placeGoldenOaks(level, generator, random, this.getBoundingBox(), chunkBox, 48, 2, 1);
        }

    }

    /**
     * The chunks of land surrounding the boss room to form an island.
     */
    public static class Stub extends GoldDungeonPiece {

        public Stub(StructureTemplateManager manager, String name, BlockPos pos) {
            super(AetherStructurePieceTypes.GOLD_STUB.get(), manager, name, makeSettings(), pos);
        }

        public Stub(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_STUB.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(VerticalGradientProcessor.INSTANCE);
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos pChunkPos, BlockPos pPos) {
            super.postProcess(level, structureManager, generator, random, chunkBox, pChunkPos, pPos);
            placeGoldenOaks(level, generator, random, this.getBoundingBox(), chunkBox, 64, 1, 0);
        }
    }

    /**
     * The tunnel leading from the side of the island to the boss room.
     */
    public static class Tunnel extends GoldDungeonPiece {

        public Tunnel(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.GOLD_TUNNEL.get(), manager, name, makeSettings().setRotation(rotation), pos);
        }

        public Tunnel(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_TUNNEL.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(MOSSY_HOLYSTONE).addProcessor(NoReplaceProcessor.AIR);
        }
    }

    public static abstract class GoldDungeonPiece extends TemplateStructurePiece {

        public GoldDungeonPiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos) {
            super(type, 0, manager, new ResourceLocation(Aether.MODID, "gold_dungeon/" + name), name, settings, pos);
            this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        }

        public GoldDungeonPiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
            super(type, tag, manager, settingsFactory.andThen(settings -> settings.setRotation(Rotation.valueOf(tag.getString("Rotation")))));
            this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rotation", this.placeSettings.getRotation().name());
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }
    }

    /**
     * Randomly place golden oak trees and flowers on top of a structure piece.
     * @param boundingBox - The structure piece's bounding box
     * @param chunkBox - The current chunk's bounding box
     * @param randomBounds - The parameter for random.nextInt()
     * @param treeWeight - The chance out of randomBounds of placing a tree
     * @param flowerWeight - The chance out of randomBounds of placing a flower
     */
    private static void placeGoldenOaks(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BoundingBox boundingBox, BoundingBox chunkBox, int randomBounds, int treeWeight, int flowerWeight) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int minX = Math.max(chunkBox.minX(), boundingBox.minX());
        int minZ = Math.max(chunkBox.minZ(), boundingBox.minZ());
        int maxX = Math.min(chunkBox.maxX(), boundingBox.maxX());
        int maxZ = Math.min(chunkBox.maxZ(), boundingBox.maxZ());
        int minY = boundingBox.minY() + Mth.floor((boundingBox.maxY() - boundingBox.minY()) * 0.75);
        int maxY = boundingBox.maxY();

        for (int x = minX; x < maxX; ++x) {
            for (int z = minZ; z < maxZ; ++z) {
                int featureType = random.nextInt(randomBounds);
                if (featureType < treeWeight + flowerWeight) {
                    mutable.set(x, maxY, z);
                    if (iterateColumn(level, mutable, minY, maxY)) {
                        if (featureType < treeWeight) {
                            PlacedFeature tree = PlacementUtils.inlinePlaced(level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION)).get();
                            tree.place(level, generator, random, mutable);
                        } else {
                            Block flower = random.nextBoolean() ? Blocks.DANDELION : Blocks.POPPY;
                            level.setBlock(mutable, flower.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns true if there is a solid block in the column. MutableBlockPos is set to the first empty block.
     * @param level - The level to check for blocks.
     * @param pos - This MutableBlockPos is set to the first empty block in the column.
     */
    private static boolean iterateColumn(WorldGenLevel level, BlockPos.MutableBlockPos pos, int minY, int maxY) {
        int y;
        for (y = maxY; y > minY; --y) {
            pos.setY(y);
            if (level.getBlockState(pos).is(AetherTags.Blocks.AETHER_DIRT)) {
                pos.setY(++y);
                return true;
            }
        }
        return false;
    }
}
