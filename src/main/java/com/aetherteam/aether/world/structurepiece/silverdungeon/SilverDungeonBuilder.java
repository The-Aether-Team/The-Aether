package com.aetherteam.aether.world.structurepiece.silverdungeon;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class randomly assembles the Silver Dungeon rooms using a 3x3x3 grid.
 */
public class SilverDungeonBuilder {
    private static final int CHEST_ROOM = 0b1;
    private static final int STAIRS = 0b10;
    private static final int FINAL_STAIRS = 0b100;
    private static final int STAIRS_MIDDLE = 0b1000;
    private static final int STAIRS_TOP = 0b10000;
    private static final int NORTH_DOOR = 0b100000;
    private static final int WEST_DOOR = 0b1000000;
    private static final int VISITED = 0b10000000;

    private final RandomSource random;
    private final int[][][] grid;
    private final int width;
    private final int height;
    private final int length;

    public SilverDungeonBuilder(RandomSource random, int x, int y, int z) {
        this.random = random;
        this.grid = new int[x][y][z];
        this.width = x;
        this.height = y;
        this.length = z;

        this.populateGrid();
    }

    /**
     * Set up all rooms in the dungeon.
     */
    private void populateGrid() {
        // Place the stairs.
        int finalStairsX = this.random.nextInt(this.width);
        this.grid[finalStairsX][0][0] = FINAL_STAIRS;
        this.grid[finalStairsX][1][0] = STAIRS_MIDDLE;
        this.grid[finalStairsX][2][0] = STAIRS_TOP;

        int firstStairsX = this.random.nextInt(this.width);
        this.grid[firstStairsX][0][1] = STAIRS;
        this.grid[firstStairsX][1][1] = STAIRS_TOP;

        int secondStairsX = this.random.nextInt(this.width);
        this.grid[secondStairsX][1][2] = STAIRS;
        this.grid[secondStairsX][2][2] = STAIRS_TOP;


        for (int y = 0; y < this.height; y++) {
            this.traverseRooms(1, y, 1, 0);

            for (int z = 0; z < this.length; z++) {
                for (int x = 0; x < this.width; x++) {
                    if ((this.grid[x][y][z] & 0b11111) == 0 && this.random.nextInt(3) != 0) { // Check for an empty room
                        this.grid[x][y][z] |= CHEST_ROOM;
                    }
                }
            }
        }
    }

    /**
     * Recursively traverse through the rooms to build a path through them.
     * @param x The x {@link Integer} offset for the room position.
     * @param y The y {@link Integer} offset for the room position.
     * @param z The z {@link Integer} offset for the room position.
     * @param typesToAvoid An {@link Integer} bitmask of the types of rooms that should not be connected to.
     * @return Whether the path can be made between the rooms, as a {@link Boolean}.
     */
    private boolean traverseRooms(int x, int y, int z, int typesToAvoid) {
        // Check if out of bounds of the array
        if (x < 0 || x >= this.width || z < 0 || z >= this.length) {
            return false;
        }

        int room = this.grid[x][y][z];
        if ((room & typesToAvoid) > 0) { // Make sure the stairs are not next to each other.
            return false;
        }
        if ((room & VISITED) == VISITED) {
            return random.nextInt(3) == 0;
        }
        this.grid[x][y][z] |= VISITED;

        int blacklist = this.setNeighborBlacklist(room);

        List<Direction> directions = new ArrayList<>(4);
        Collections.addAll(directions, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST);

        for (int i = directions.size(); i > 0; i--) {
            int index = this.random.nextInt(i);
            switch (directions.remove(index)) {
                case NORTH -> {
                    if (this.traverseRooms(x, y, z - 1, blacklist)) {
                        this.grid[x][y][z] |= NORTH_DOOR;
                    }
                }
                case SOUTH -> {
                    if (this.traverseRooms(x, y, z + 1, blacklist)) {
                        this.grid[x][y][z + 1] |= NORTH_DOOR;
                    }
                }
                case WEST -> {
                    if (this.traverseRooms(x - 1, y, z, blacklist)) {
                        this.grid[x][y][z] |= WEST_DOOR;
                    }
                }
                case EAST -> {
                    if (this.traverseRooms(x + 1, y, z, blacklist)) {
                        this.grid[x + 1][y][z] |= WEST_DOOR;
                    }
                }
            }
        }

        return true;
    }

    private int setNeighborBlacklist(int roomType) {
        int blacklist = FINAL_STAIRS | STAIRS_MIDDLE;
        if ((roomType & STAIRS_TOP) == STAIRS_TOP) {
            blacklist |= STAIRS;
        }
        if ((roomType & STAIRS) == STAIRS) {
            blacklist |= STAIRS_TOP;
        }
        return blacklist;
    }

    /**
     * Build the rooms based on the grid.
     * @param builder The {@link StructurePiecesBuilder}.
     * @param templateManager The {@link StructureTemplateManager}.
     * @param startPos The starting {@link BlockPos} for the structure.
     * @param rotation The structure's {@link Rotation}.
     * @param direction The structure's {@link Direction}.
     */
    public void assembleDungeon(StructurePiecesBuilder builder, StructureTemplateManager templateManager, BlockPos startPos, Rotation rotation, Direction direction) {
        startPos = startPos.offset(direction.getStepZ() * 5 - direction.getStepX(), 5, -direction.getStepX() * 5 - direction.getStepZ());
        BlockPos.MutableBlockPos offset = new BlockPos.MutableBlockPos();
        Rotation sideways = rotation.getRotated(Rotation.CLOCKWISE_90);

        for (int y = this.height - 1; y >= 0; y--) {
            offset.setY(startPos.getY() + y * 5);

            for (int z = 0; z < this.length; z++) {
                for (int x = 0; x < this.width; x++) {
                    int xOffset = startPos.getX() + (direction.getStepZ() * x * 7) + (direction.getStepX() * z * 7);
                    int zOffset = startPos.getZ() + (direction.getStepZ() * z * 7) - (direction.getStepX() * x * 7);
                    offset.set(xOffset, offset.getY(), zOffset);

                    int room = this.grid[x][y][z];
                    builder.addPiece(new SilverFloorPiece(templateManager, "floor",
                            offset.offset(direction.getStepX() + direction.getStepZ(), -1, direction.getStepZ() - direction.getStepX()), rotation));
                    builder.addPiece(new SilverTemplePiece(templateManager, (room & NORTH_DOOR) == NORTH_DOOR ? "door" : "wall",
                            offset.offset(direction.getStepZ(), 0, -direction.getStepX()), rotation));
                    builder.addPiece(new SilverTemplePiece(templateManager, (room & WEST_DOOR) == WEST_DOOR ? "door" : "wall",
                            offset.relative(direction), sideways));

                    if ((room & FINAL_STAIRS) == FINAL_STAIRS) {
                        builder.addPiece(new SilverDungeonRoom(templateManager, "tall_staircase", offset.offset(2, 0, 2), rotation));
                        builder.addPiece(new SilverTemplePiece(templateManager, "boss_door",
                                offset.offset(direction.getStepZ() * 3, 0, -direction.getStepX() * 3), rotation));
                    } else if ((room & STAIRS) == STAIRS) {
                        builder.addPiece(new SilverDungeonRoom(templateManager, "staircase", offset.offset(2, 0, 2), rotation));
                    } else if ((room & CHEST_ROOM) == CHEST_ROOM) {
                        builder.addPiece(new SilverDungeonRoom(templateManager, "chest_room", offset.offset(3, 0, 3), rotation));
                    }
                }
            }
        }
    }
}