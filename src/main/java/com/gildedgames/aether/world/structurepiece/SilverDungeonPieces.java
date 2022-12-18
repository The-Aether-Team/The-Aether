package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.gildedgames.aether.loot.AetherLoot;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;

public class SilverDungeonPieces {
    public static final RuleProcessor LOCKED_ANGELIC_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_ANGELIC_STONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.TRAPPED_ANGELIC_STONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.HOLYSTONE.get(), 0.3F), AlwaysTrueTest.INSTANCE, AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState())
    ));

    /**
     * For testing the silver dungeon grid.
     */
    public static void main(String[] args) {
        RandomSource randomsource = RandomSource.create();
        long i = randomsource.nextLong();
        System.out.println("Seed: " + i);
        randomsource.setSeed(i);
        SilverDungeonGrid grid = new SilverDungeonGrid(randomsource, 3, 3, 3);
        grid.print();
    }

    public static class SilverDungeonGrid {
        public static final int EMPTY_ROOM = 0b1;
        public static final int CHEST_ROOM = 0b10;
        public static final int STAIRS = 0b100;
        public static final int STAIRS_TOP = 0b101;
        public static final int FINAL_STAIRS = 0b111;
        public static final int NORTH_DOOR = 0b1000;
        public static final int WEST_DOOR = 0b10000;
        public static final int EAST_DOOR = 0b100000;
        public static final int SOUTH_DOOR = 0b1000000;



        public final RandomSource random;
        public final int[][][] grid;

        public SilverDungeonGrid(RandomSource random, int x, int y, int z) {
            this.random = random;
            this.grid = new int[x][y][z];

            this.grid[1][0][0] = (this.random.nextBoolean() ? CHEST_ROOM : EMPTY_ROOM) | SOUTH_DOOR;

            // Place the stairs
            int finalStairsX = random.nextInt(this.grid.length);

            this.grid[finalStairsX][0][2] = FINAL_STAIRS;
            this.grid[finalStairsX][1][2] = FINAL_STAIRS;
            this.grid[finalStairsX][2][2] = FINAL_STAIRS;

//            int firstStairsX = random.nextInt(this.grid);


        }

        public void assembleDungeon(StructurePieceAccessor builder) {

        }

        public void print() {
            StringBuilder output = new StringBuilder();
            for (int y = 0; y < this.grid[0].length; y++) {
                for (int z = 0; z < this.grid[0][0].length; z++) {

                    for (int i = 0; i < 3; i++) {
                        for (int x = 0; x < this.grid.length; x++) {
                            if ((this.grid[x][y][z] & NORTH_DOOR) == NORTH_DOOR) {
                                output.append("+ +");
                            } else {
                                output.append("+-+");
                            }

                        }
                    }

                }

                output.append("\n");
            }

            output.append("\n");
            System.out.println(output);
        }
    }

    public static class TemplePiece extends SilverDungeonPiece {

        public TemplePiece(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.SILVER_DUNGEON_PIECE.get(), manager, name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(rotation.rotate(Direction.SOUTH));
        }

        public TemplePiece(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.SILVER_DUNGEON_PIECE.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(LOCKED_ANGELIC_STONE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            if (name.equals("Chest")) {
                if (random.nextBoolean()) {
                    level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 2);
                    if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
                        chest.setLootTable(AetherLoot.SILVER_DUNGEON, random.nextLong());
                    }
                } else {
                    level.setBlock(pos, AetherBlocks.CHEST_MIMIC.get().defaultBlockState(), 1 | 2);
                }
            }
        }
    }

    public static class BossRoom extends SilverDungeonPiece {

        public BossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.SILVER_BOSS_ROOM.get(), manager, name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(rotation.rotate(Direction.SOUTH));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.SILVER_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(LOCKED_ANGELIC_STONE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB) {
            if (name.equals("Valkyrie Queen")) {
                ValkyrieQueen queen = new ValkyrieQueen(AetherEntityTypes.VALKYRIE_QUEEN.get(), level.getLevel());
                queen.setPersistenceRequired();
                queen.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                queen.setDungeon(new DungeonTracker<>(queen,
                        queen.position(),
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() + 1, this.boundingBox.maxY() + 1, this.boundingBox.maxZ() + 1),
                        new ArrayList<>()));
                queen.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.getLevel().addFreshEntity(queen);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            } else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.SILVER_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "silver"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    public static class LegacyCloudBed extends StructurePiece {
        private final Set<BlockPos> positions;

        public LegacyCloudBed(Set<BlockPos> positions, BoundingBox pBox, Direction direction) {
            super(AetherStructurePieceTypes.LEGACY_CLOUD_BED.get(), 0, pBox);
            this.positions = positions;
            this.setOrientation(direction);
        }

        public LegacyCloudBed(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.LEGACY_CLOUD_BED.get(), tag);
            ListTag positions = tag.getList("Positions", Tag.TAG_COMPOUND);
            this.positions = new HashSet<>();
            for (Tag position : positions) {
                this.positions.add(NbtUtils.readBlockPos((CompoundTag) position));
            }
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            ListTag positions = new ListTag();
            for (BlockPos position : this.positions) {
                positions.add(NbtUtils.writeBlockPos(position));
            }
            tag.put("Positions", positions);
        }

        @Override
        public void postProcess(@Nonnull WorldGenLevel level, @Nonnull StructureManager manager, @Nonnull ChunkGenerator generator, @Nonnull RandomSource random, @Nonnull BoundingBox bounds, @Nonnull ChunkPos chunkPos, @Nonnull BlockPos blockPos) {
            if (!this.positions.isEmpty()) {
                this.positions.removeIf(pos -> this.placeBlock(level, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState(), pos.offset(0, blockPos.getY(), 0), bounds));
            }
        }

        protected boolean placeBlock(WorldGenLevel level, BlockState state, BlockPos pos, BoundingBox bounds) {
            if (bounds.isInside(pos)) {
                if (this.canBeReplaced(level, pos)) {
                    level.setBlock(pos, state, 2);
                }
                return true;
            }
            return false;
        }

        protected boolean canBeReplaced(LevelReader level, BlockPos pos) {
            return level.isEmptyBlock(pos);
        }
    }

    public static abstract class SilverDungeonPiece extends TemplateStructurePiece {

        public SilverDungeonPiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos) {
            super(type, 0, manager, new ResourceLocation(Aether.MODID, "silver_dungeon/" + name), name, settings, pos);
        }

        public SilverDungeonPiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
            super(type, tag, manager, settingsFactory.andThen(settings -> settings.setRotation(Rotation.valueOf(tag.getString("Rotation")))));
            this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rotation", this.placeSettings.getRotation().name());
        }
    }
}
