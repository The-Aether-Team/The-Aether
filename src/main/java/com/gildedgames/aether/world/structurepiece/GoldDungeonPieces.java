package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.SunSpirit;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.processor.VegetationProcessor;
import com.gildedgames.aether.world.processor.VerticalGradientProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
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

    /**
     * A room inside the island. This should contain the sun spirit.
     */
    public static class BossRoom extends GoldDungeonPiece {

        public BossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), manager, name, makeSettings().setRotation(rotation), pos);
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
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
            }
            else if (name.equals("Treasure Chest")) {
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

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(VerticalGradientProcessor.INSTANCE).addProcessor(VegetationProcessor.INSTANCE);
        }

        @Override
        protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) {}
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
            return new StructurePlaceSettings().addProcessor(VerticalGradientProcessor.INSTANCE).addProcessor(VegetationProcessor.STUB_PROCESSOR);
        }

        @Override
        protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) {}
    }

    public static class LegacyTunnelPiece extends StructurePiece {

        public LegacyTunnelPiece(BoundingBox pBox) {
            super(AetherStructurePieceTypes.GOLD_LEGACY_TUNNEL.get(), 0, pBox);
            this.setOrientation(Direction.NORTH);
        }

        public LegacyTunnelPiece(StructurePieceSerializationContext context, CompoundTag pTag) {
            super(AetherStructurePieceTypes.GOLD_LEGACY_TUNNEL.get(), pTag);
            this.setOrientation(Direction.NORTH);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {

        }

        @Override
        public void postProcess(WorldGenLevel world, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource random, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {

            int radius = 13;
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for(int z = radius; z < radius + 32; ++z) { // loop: 13 - 44 [z offset]
                boolean var18 = false;

                for (int y = -3; y < 2; ++y) { // y offset
                    for (int x = -3; x < 4; ++x) { // x offset

                        if (world.isEmptyBlock(mutable.set(x, y, pPos.getZ() + z))) {
                            var18 = true;
                            if (y == -3) {
                                this.placeBlock(world, AetherBlocks.HOLYSTONE.get().defaultBlockState(), x, y, z, pBox);
                            } else if (y < 1) {
                                if (z == radius) {
                                    if (x < 2 && x > -2 && y < 0) {
                                        this.placeBlock(world, Blocks.AIR.defaultBlockState(), x, y, z, pBox);
                                    } else {
                                        this.placeBlock(world, AetherBlocks.HELLFIRE_STONE.get().defaultBlockState(), x, y, z, pBox);
                                    }
                                } else if (x != 3 && x != -3) {
                                    this.placeBlock(world, Blocks.AIR.defaultBlockState(), x, y, z, pBox);
                                    if (y == -1 && (x == 2 || x == -2) && (z - radius - 2) % 3 == 0) {
                                        this.placeBlock(world, Blocks.AIR.defaultBlockState(), x, y, z, pBox);
                                    }
                                } else {
                                    this.placeBlock(world, AetherBlocks.HOLYSTONE.get().defaultBlockState(), x, y, z, pBox);
                                }
                            } else if (z == radius) {
                                this.placeBlock(world, AetherBlocks.HELLFIRE_STONE.get().defaultBlockState(), x, y, z, pBox);
                            } else {
                                this.placeBlock(world, AetherBlocks.HOLYSTONE.get().defaultBlockState(), x, y, z, pBox);
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
            this.setOrientation(this.getRotation().rotate(Direction.NORTH));
        }

        public GoldDungeonPiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
            super(type, tag, manager, settingsFactory.andThen(settings -> settings.setRotation(Rotation.valueOf(tag.getString("Rotation")))));
            this.setOrientation(this.getRotation().rotate(Direction.NORTH));
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
