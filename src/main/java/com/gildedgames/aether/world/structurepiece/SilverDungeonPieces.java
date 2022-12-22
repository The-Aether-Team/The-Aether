package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.processor.TrappedBlockProcessor;
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
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
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

    public static final TrappedBlockProcessor TRAPPED_ANGELIC_STONE = new TrappedBlockProcessor(AetherBlocks.LOCKED_ANGELIC_STONE.get(), AetherBlocks.TRAPPED_ANGELIC_STONE.get(), 0.05F);
    public static final TrappedBlockProcessor TRAPPED_LIGHT_ANGELIC_STONE = new TrappedBlockProcessor(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get(), AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get(), 0.05F);

    /**
     * This class is for randomly assembling the silver dungeon.
     */
    public static class SilverDungeonGrid {
        public static final int CHEST_ROOM = 0b1;
        public static final int STAIRS = 0b10;
        public static final int FINAL_STAIRS = 0b100;
        public static final int STAIRS_MIDDLE = 0b1000;
        public static final int STAIRS_TOP = 0b10000;
        public static final int NORTH_DOOR = 0b100000;
        public static final int WEST_DOOR = 0b1000000;
        public static final int VISITED = 0b10000000;

        public final RandomSource random;
        public final int[][][] grid;
        private final int width;
        private final int height;
        private final int length;

        public SilverDungeonGrid(RandomSource random, int x, int y, int z) {
            this.random = random;
            this.grid = new int[x][y][z];
            this.width = x;
            this.height = y;
            this.length = z;

            this.populateGrid();
        }

        /**
         * Set up all rooms in the dungeon
         */
        public void populateGrid() {
            // Place the stairs
            int finalStairsX = random.nextInt(this.width);

            this.grid[finalStairsX][0][0] = FINAL_STAIRS | VISITED;
            this.grid[finalStairsX][1][0] = STAIRS_MIDDLE | VISITED;
            this.grid[finalStairsX][2][0] = STAIRS_TOP;

            int firstStairsX = random.nextInt(this.width);

            this.grid[firstStairsX][0][1] = STAIRS;
            this.grid[firstStairsX][1][1] = STAIRS_TOP;

            int secondStairsX = this.random.nextInt(this.width);

            this.grid[secondStairsX][1][2] = STAIRS;
            this.grid[secondStairsX][2][2] = STAIRS_TOP;


            for (int y = 0; y < this.height; y++) {
                for (int z = 0; z < this.length; z++) {
                    for (int x = 0; x < this.width; x++) {
                        if ((this.grid[x][y][z] & 0b11111) == 0 && this.random.nextBoolean()) { // Check for an empty room
                            this.grid[x][y][z] |= CHEST_ROOM;
                        }

                        if ((this.grid[x][y][z] & VISITED) != VISITED) { // Check if there is a path to the room
                            this.traverseRooms(x, y, z, FINAL_STAIRS | STAIRS_MIDDLE);
                        }
                    }
                }
            }

            /*for (int y = 0; y < this.height; y++) {
                for (int z = 0; z < this.length; z++) {
                    for (int x = 0; x < this.width; x++) {
                        Aether.LOGGER.info(String.format("x: %d y: %d z: %d ", x, y, z)
                                + ((this.grid[x][y][z] & VISITED) == VISITED ? "Visited, " : "Not visited, ")
                                + ((this.grid[x][y][z] & NORTH_DOOR) == NORTH_DOOR ? "North door, " : "No north door, ")
                                + ((this.grid[x][y][z] & WEST_DOOR) == WEST_DOOR ? "West door" : "No west door"));
                    }
                }
            }*/
        }

        /**
         * Recursively traverse through the rooms to build a path through them.
         * Returns false if the path must not be made.
         * @param typesToAvoid - A bitmask of the types of rooms that should not be connected to.
         */
        private boolean traverseRooms(int x, int y, int z, int typesToAvoid) {
//            Aether.LOGGER.info(String.format("x: %d y: %d z: %d", x, y, z));
            int room = this.grid[x][y][z];

            if ((room & typesToAvoid) > 0) { // Make sure the stairs are not next to each other.
                return false;
            }

            if ((room & VISITED) == VISITED) {
                return true;
            }

            int blacklist = FINAL_STAIRS | STAIRS_MIDDLE;
            if ((room & STAIRS_TOP) == STAIRS_TOP) {
                blacklist |= STAIRS;
            }

            if ((room & STAIRS) == STAIRS) {
                blacklist |= STAIRS_TOP;
            }

            boolean hasPath = (x != 0 && (room & WEST_DOOR) == WEST_DOOR)  // Make sure the room has at least one path in.
                    || (x != this.width - 1 && ((this.grid[x + 1][y][z] & WEST_DOOR) == WEST_DOOR))
                    || (z != 0 && (room & NORTH_DOOR) == NORTH_DOOR)
                    || (z != this.length - 1 && ((this.grid[x][y][z + 1] & NORTH_DOOR) == NORTH_DOOR));

            this.grid[x][y][z] |= VISITED;

            int i = 0;
            do {
                boolean isDoor;
                if (z != 0 && this.random.nextBoolean()) {
                    isDoor = this.traverseRooms(x, y, z - 1, blacklist);
                    if (isDoor) {
                        this.grid[x][y][z] |= NORTH_DOOR;
                        hasPath = true;
                    }
                }

                if (z < this.length - 1 && this.random.nextBoolean()) {
                    isDoor = this.traverseRooms(x, y, z + 1, blacklist);
                    if (isDoor) {
                        this.grid[x][y][z + 1] |= NORTH_DOOR;
                        hasPath = true;
                    }
                }

                if (x != 0 && this.random.nextBoolean()) {
                    isDoor = this.traverseRooms(x - 1, y, z, blacklist);
                    if (isDoor) {
                        this.grid[x][y][z] |= WEST_DOOR;
                        hasPath = true;
                    }
                }

                if (x < this.width - 1 && this.random.nextBoolean()) {
                    isDoor = this.traverseRooms(x + 1, y, z, blacklist);
                    if (isDoor) {
                        this.grid[x + 1][y][z] |= WEST_DOOR;
                        hasPath = true;
                    }
                }

            } while (!hasPath && i++ < 5);

            if (!hasPath) {
                this.grid[x][y][z] &= ~VISITED;
            }
            return hasPath;
        }

        /**
         * Build the rooms based on the grid.
         */
        public void assembleDungeon(StructurePiecesBuilder builder, StructureTemplateManager manager, BlockPos startPos, Rotation rotation, Direction direction) {
            startPos = startPos.offset(direction.getStepZ() * 5 - direction.getStepX(), 5, -direction.getStepX() * 5 - direction.getStepZ());
            BlockPos.MutableBlockPos offset = new BlockPos.MutableBlockPos();
            Rotation sideways = rotation.getRotated(Rotation.CLOCKWISE_90);

            for (int y = 0; y < this.height; y++) {
                offset.setY(startPos.getY() + y * 5);

                for (int z = 0; z < this.length; z++) {
                    for (int x = 0; x < this.width; x++) {
                        int xOffset = startPos.getX() + (direction.getStepZ() * x * 7) + (direction.getStepX() * z * 7);
                        int zOffset = startPos.getZ() + (direction.getStepZ() * z * 7) - (direction.getStepX() * x * 7);
                        offset.set(xOffset, offset.getY(), zOffset);

                        int room = this.grid[x][y][z];
                        if ((room & FINAL_STAIRS) == FINAL_STAIRS) {
                            builder.addPiece(new DungeonRoom(manager, "tall_staircase", offset.offset(2, 0, 2), rotation));
                            builder.addPiece(new TemplePiece(manager, "boss_door",
                                    offset.offset(direction.getStepZ() * 3, 0, -direction.getStepX() * 3), rotation));
                        } else if ((room & STAIRS) == STAIRS) {
                            builder.addPiece(new DungeonRoom(manager, "staircase", offset.offset(2, 0, 2), rotation));
                        } else if ((room & CHEST_ROOM) == CHEST_ROOM) {
                            builder.addPiece(new DungeonRoom(manager, "chest_room", offset.offset(3, 0, 3), rotation));
                        }

                        if ((room & NORTH_DOOR) == NORTH_DOOR) {
                            builder.addPiece(new TemplePiece(manager, "door",
                                    offset.offset(direction.getStepZ() * 3, 0, -direction.getStepX() * 3), rotation));
                        }

                        if ((room & WEST_DOOR) == WEST_DOOR) {
                            builder.addPiece(new TemplePiece(manager, "door", offset.relative(direction, 3), sideways));
                        }
                    }
                }
            }
        }
    }

    public static class TemplePiece extends SilverDungeonPiece {

        public TemplePiece(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.SILVER_TEMPLE_PIECE.get(), manager, name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(rotation.rotate(Direction.SOUTH));
        }

        public TemplePiece(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.SILVER_TEMPLE_PIECE.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(LOCKED_ANGELIC_STONE)/*.addProcessor(TRAPPED_ANGELIC_STONE).addProcessor(TRAPPED_LIGHT_ANGELIC_STONE)*/;
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {

        }
    }

    public static class DungeonRoom extends SilverDungeonPiece {

        public DungeonRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.SILVER_DUNGEON_ROOM.get(), manager, name, makeSettings(manager, rotation, new ResourceLocation(Aether.MODID, "silver_dungeon/" + name)), pos);
            this.setOrientation(rotation.rotate(Direction.SOUTH));
        }

        public DungeonRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.SILVER_DUNGEON_ROOM.get(), tag, context.structureTemplateManager(), id -> makeSettings(context.structureTemplateManager(), id));
        }

        private static StructurePlaceSettings makeSettings(StructureTemplateManager manager, Rotation rotation, ResourceLocation id) {
            return makeSettings(manager, id).setRotation(rotation);
        }

        private static StructurePlaceSettings makeSettings(StructureTemplateManager manager, ResourceLocation id) {
            StructureTemplate template = manager.getOrCreate(id);
            BlockPos pivot = new BlockPos(template.getSize().getX() / 2 - 4, 0, template.getSize().getZ() / 2 - 4);
            return new StructurePlaceSettings().setRotationPivot(pivot).addProcessor(LOCKED_ANGELIC_STONE);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
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
