package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.Slider;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.processor.NoReplaceProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.world.PieceBeardifierModifier;

import java.util.ArrayList;
import java.util.function.Function;

// TODO: The beardifier is not going to be our long-term solution. I'm using PieceBeardifierModifier mainly for testing purposes.
public class BronzeDungeonPieces {
    public static RuleProcessor LOCKED_SENTRY_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_CARVED_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_SENTRY_STONE.get().defaultBlockState())
    ));
    public static RuleProcessor BRONZE_DUNGEON_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.CARVED_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.SENTRY_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.TRAPPED_CARVED_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.TRAPPED_SENTRY_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.HOLYSTONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState())
    ));

    /**
     * Starting piece for the bronze dungeon. Has the slider.
     */
    public static class BossRoom extends BronzeDungeonPiece implements PieceBeardifierModifier {

        public BossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), 0, manager, name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(LOCKED_SENTRY_STONE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
            if (name.equals("Slider")) {
                Slider slider = new Slider(AetherEntityTypes.SLIDER.get(), level.getLevel());
                slider.setPersistenceRequired();
                double xPos = pos.getX();
                double zPos = pos.getZ();
                switch (this.placeSettings.getRotation()) {
                    case NONE -> {
                        xPos += 1;
                        zPos += 1;
                    }
                    case CLOCKWISE_90 -> zPos += 1;
                    case COUNTERCLOCKWISE_90 -> xPos += 1;
                }
                slider.setPos(xPos, pos.getY(), zPos);
                slider.setDungeon(new DungeonTracker<>(slider,
                        slider.position(),
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ()),
                        new ArrayList<>()));
                slider.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.getLevel().addFreshEntity(slider);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            } else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.BRONZE_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "bronze"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        @Override
        public BoundingBox getBeardifierBox() {
            return this.boundingBox;
        }

        @Override
        public TerrainAdjustment getTerrainAdjustment() {
            return TerrainAdjustment.BURY;
        }

        @Override
        public int getGroundLevelDelta() {
            return 8;
        }
    }

    public static class DungeonRoom extends BronzeDungeonPiece implements PieceBeardifierModifier {
        public DungeonRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_DUNGEON_ROOM.get(), 0, manager, name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        }

        public DungeonRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_DUNGEON_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(BRONZE_DUNGEON_STONE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            if (name.equals("Chest")) {
                if (random.nextBoolean()) {
                    level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 2);
                    if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
                        chest.setLootTable(AetherLoot.BRONZE_DUNGEON, random.nextLong());
                    }
                } else {
                    level.setBlock(pos, AetherBlocks.CHEST_MIMIC.get().defaultBlockState(), 3);
                }
            }
        }

        @Override
        public BoundingBox getBeardifierBox() {
            return this.boundingBox;
        }

        @Override
        public TerrainAdjustment getTerrainAdjustment() {
            return TerrainAdjustment.BURY;
        }

        @Override
        public int getGroundLevelDelta() {
            return 8;
        }
    }

    /**
     * The entrance to the bronze dungeon. It shouldn't replace air so that it matches the landscape.
     */
    public static class HolystoneTunnel extends BronzeDungeonPiece implements PieceBeardifierModifier {

        public HolystoneTunnel(StructureTemplateManager pStructureTemplateManager, String name, BlockPos pTemplatePosition, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), 0, pStructureTemplateManager, name, makeSettings().setRotation(rotation), pTemplatePosition);
        }

        public HolystoneTunnel(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(NoReplaceProcessor.AIR).addProcessor(BRONZE_DUNGEON_STONE);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }

        @Override
        public BoundingBox getBeardifierBox() {
            return this.boundingBox;
        }

        @Override
        public TerrainAdjustment getTerrainAdjustment() {
            return TerrainAdjustment.NONE;
        }

        @Override
        public int getGroundLevelDelta() {
            return 0;
        }
    }

    public static abstract class BronzeDungeonPiece extends TemplateStructurePiece {

        public BronzeDungeonPiece(StructurePieceType type, int genDepth, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos) {
            super(type, genDepth, manager, new ResourceLocation(Aether.MODID, "bronze_dungeon/" + name), name, settings, pos);
        }

        public BronzeDungeonPiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
            super(type, tag, manager, settingsFactory.andThen(settings -> settings.setRotation(Rotation.valueOf(tag.getString("Rotation")))));
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rotation", this.placeSettings.getRotation().name());
        }
    }
}
