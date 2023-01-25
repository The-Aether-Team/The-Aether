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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
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

    /**
     * Little caves that are placed around the gold dungeon island.
     */
    public static class GumdropCave extends StructurePiece {

        public GumdropCave(BoundingBox box) {
            super(AetherStructurePieceTypes.GUMDROP_CAVE.get(), 0, box);
        }

        public GumdropCave(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GUMDROP_CAVE.get(), tag);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos startPos) {
            BlockPos center = this.boundingBox.getCenter();
            float f = random.nextFloat() * Mth.PI;
            int i = center.getX();
            int j = center.getY();
            int k = center.getZ();

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            double offset = 30.0 / 8.0;

            double lowerX = (i) + Mth.sin(f) * offset;
            double upperX = (i) - Mth.sin(f) * offset;
            double lowerZ = (k) + Mth.cos(f) * offset;
            double upperZ = (k) - Mth.cos(f) * offset;
            double lowerY = j + random.nextInt(3) + 2;
            double upperY = j + random.nextInt(3) + 2;

            for(int length = 0; length <= 30; ++length) {
                double radius = length / 30.0;

                double x = lowerX + (upperX - lowerX) * radius;
                double y = lowerY + (upperY - lowerY) * radius;
                double z = lowerZ + (upperZ - lowerZ) * radius;

                double magnitude = random.nextDouble() * 30 / 16.0D;
                double width = (Math.sin(radius * Mth.PI) + 1.0F) * magnitude + 1.0D;
                width /= 2;

                int minX = Mth.floor(x - width);
                int minY = Mth.floor(y - width);
                int minZ = Mth.floor(z - width);

                int maxX = Mth.floor(x + width);
                int maxY = Mth.floor(y + width);
                int maxZ = Mth.floor(z + width);

                for(int xOffset = minX; xOffset <= maxX; ++xOffset) {
                    double xDistance = Mth.square((xOffset + 0.5D - x) / (width));
                    if(xDistance < 1.0D) {
                        for(int yOffset = minY; yOffset <= maxY; ++yOffset) {
                            double yDistance = Mth.square((yOffset + 0.5D - y) / (width));
                            if(xDistance + yDistance < 1.0D) {
                                for(int zOffset = minZ; zOffset <= maxZ; ++zOffset) {
                                    double zDistance = Mth.square((zOffset + 0.5D - z) / (width));
                                    if(xDistance + yDistance + zDistance < 1.0D) {
                                        BlockState state = level.getBlockState(mutable.set(xOffset, yOffset, zOffset));
                                        if (state.is(AetherTags.Blocks.AETHER_DIRT) || state.is(AetherTags.Blocks.HOLYSTONE)) {
                                            level.setBlock(mutable, Blocks.AIR.defaultBlockState(), 2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
}
