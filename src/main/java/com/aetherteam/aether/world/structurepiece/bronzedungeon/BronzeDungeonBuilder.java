package com.aetherteam.aether.world.structurepiece.bronzedungeon;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.world.BlockLogicUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A directed graph used for assembling the Bronze Dungeon. This allows us to keep track of how far away a room is
 * from the starting point by counting along the path.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Directed_graph">https://en.wikipedia.org/wiki/Directed_graph</a>
 */
public class BronzeDungeonBuilder {
    public static final Map<String, SimpleWeightedRandomList.Builder<RoomProvider<?>>> ROOM_OPTIONS_BUILDER = Map.ofEntries(
        Map.entry("boss_room", new SimpleWeightedRandomList.Builder<>()),
        Map.entry("chest_room", new SimpleWeightedRandomList.Builder<>()),
        Map.entry("end_corridor", new SimpleWeightedRandomList.Builder<>()),
        Map.entry("entrance", new SimpleWeightedRandomList.Builder<>()),
        Map.entry("lobby", new SimpleWeightedRandomList.Builder<>()),
        Map.entry("square_tunnel", new SimpleWeightedRandomList.Builder<>())
    );
    private static Map<String, SimpleWeightedRandomList<RoomProvider<?>>> ROOM_OPTIONS;

    private final Structure.GenerationContext context;
    private final StructureTemplateManager manager;
    private final RandomSource random;
    private final BronzeProcessorSettings processors;

    private final int nodeWidth;
    private final int edgeWidth;
    private final int edgeLength;
    private final int maxSize;

    private final List<StructurePiece> nodes = new ArrayList<>();
    private final Map<StructurePiece, Map<Direction, Connection>> edges = new HashMap<>();

    public BronzeDungeonBuilder(Structure.GenerationContext context, int maxSize, BronzeProcessorSettings processors) {
        this.context = context;
        this.manager = context.structureTemplateManager();
        this.random = context.random();
        this.processors = processors;

        Vec3i nodeSize = context.structureTemplateManager().getOrCreate(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze_dungeon/chest_room")).getSize();
        this.nodeWidth = nodeSize.getX();

        Vec3i edgeSize = context.structureTemplateManager().getOrCreate(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze_dungeon/square_tunnel")).getSize();
        this.edgeWidth = edgeSize.getX();
        this.edgeLength = edgeSize.getZ();

        this.maxSize = Math.max(3, maxSize);

        ROOM_OPTIONS_BUILDER.get("boss_room").add((manager, pos, rotation, processorList) -> new BronzeBossRoom(manager, "boss_room", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("chest_room").add((manager, pos, rotation, processorList) -> new BronzeDungeonRoom(manager, "chest_room", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("end_corridor").add((manager, pos, rotation, processorList) -> new BronzeTunnel(manager, "end_corridor", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("entrance").add((manager, pos, rotation, processorList) -> new BronzeDungeonRoom(manager, "entrance", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("lobby").add((manager, pos, rotation, processorList) -> new BronzeDungeonRoom(manager, "lobby", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("square_tunnel").add((manager, pos, rotation, processorList) -> new BronzeDungeonRoom(manager, "square_tunnel", pos, rotation, processorList), 1);
    }

    public void initializeDungeon(BlockPos startPos, Structure.GenerationContext genContext, StructurePiecesBuilder builder) {
        ROOM_OPTIONS = ROOM_OPTIONS_BUILDER.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().build()));

        StructureTemplate bossTemplate = this.context.structureTemplateManager().getOrCreate(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze_dungeon/boss_room"));

        Rotation rotation = getBossRoomRotation(startPos, startPos.offset(bossTemplate.getSize()));
        if (rotation == null) { // The space may not be big enough for multiple rooms. If so, stop trying.
            return;
        }
        BronzeDungeonPiece bossRoom = this.chooseRoom("boss_room", startPos, rotation, this.processors.bossSettings());
        Direction direction = bossRoom.getOrientation();
        if (direction != null) {
            BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(bossRoom.getBoundingBox().moved(0, 2, 0), direction, this.edgeWidth);
            BronzeDungeonPiece hallway = this.chooseRoom("square_tunnel", pos, bossRoom.getRotation(), this.processors.roomSettings());
            pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
            BronzeDungeonPiece defaultRoom = this.chooseRoom("chest_room", pos, hallway.getRotation(), this.processors.roomSettings());

            this.nodes.add(bossRoom);
            this.nodes.add(defaultRoom);
            new Connection(bossRoom, defaultRoom, hallway, direction);

            ChunkPos chunkPos = genContext.chunkPos();

            for (int i = 2; i < this.maxSize - 1; ++i) {
                this.propagateRooms(defaultRoom, chunkPos, false);
            }

            this.propagateRooms(defaultRoom, chunkPos, true);
            StructurePiece lobby = this.nodes.get(this.nodes.size() - 1);
            this.buildEndTunnel(lobby, startPos);
            this.buildSurfaceTunnel(genContext.heightAccessor(), genContext.chunkGenerator(), genContext.randomState());

            this.populatePiecesBuilder(builder);
        }
    }

    /**
     * Recursively move through the graph of rooms to add new pieces.
     *
     * @param currentNode The current {@link StructurePiece} node to try to add.
     * @param chunkPos    The {@link ChunkPos} for the piece.
     * @param placeLobby  Whether to place a lobby or a chest room, as a {@link Boolean}.
     * @return Whether the new piece was successfully placed, as a {@link Boolean}.
     */
    private boolean propagateRooms(StructurePiece currentNode, ChunkPos chunkPos, boolean placeLobby) {
        Rotation rotation = currentNode.getRotation();
        List<Rotation> rotations = new ArrayList<>(3);
        rotations.add(rotation.getRotated(Rotation.COUNTERCLOCKWISE_90));
        rotations.add(rotation);
        rotations.add(rotation.getRotated(Rotation.CLOCKWISE_90));
        String roomName = placeLobby ? "lobby" : "chest_room";

        // Attempt to generate a room in each direction.
        for (int i = 3; i > 0; i--) {
            rotation = rotations.remove(this.random.nextInt(i));
            Direction direction = rotation.rotate(Direction.SOUTH);
            if (this.hasConnection(currentNode, direction)) {
                if (propagateRooms(this.edges.get(currentNode).get(direction).end, chunkPos, placeLobby)) {
                    return true;
                }
            } else {
                BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(currentNode.getBoundingBox(), direction, this.edgeWidth);

                BronzeDungeonPiece hallway = this.chooseRoom("square_tunnel", pos, rotation, this.processors.roomSettings());
                pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
                BronzeDungeonPiece room = this.chooseRoom(roomName, pos, rotation, this.processors.roomSettings());
                StructurePiece collisionPiece = StructurePiece.findCollisionPiece(this.nodes, room.getBoundingBox());

                if (this.isCloseToCenter(chunkPos, room.templatePosition()) && this.isCoveredAtPos(room.getBoundingBox())) {
                    if (collisionPiece == null) {
                        new Connection(currentNode, room, hallway, direction);
                        this.nodes.add(room);
                        return true;
                    } else if (!(collisionPiece instanceof BronzeBossRoom)) { // If there's a piece in the way, see if there's a connection already. If not, make one. Then continue the loop.
                        boolean flag = this.edges.computeIfAbsent(collisionPiece, piece -> new HashMap<>()).values().stream()
                                .map(Connection::endPiece).anyMatch(piece -> piece == currentNode);
                        if (!flag) {
                            new Connection(currentNode, room, hallway, direction);
                        }
                    }
                }
            }
        }
        return false;
    }

    private void buildEndTunnel(StructurePiece lobby, BlockPos origin) {
        Rotation rotation = lobby.getRotation();
        List<Rotation> rotations = new ArrayList<>(3);
        rotations.add(rotation.getRotated(Rotation.COUNTERCLOCKWISE_90));
        rotations.add(rotation);
        rotations.add(rotation.getRotated(Rotation.CLOCKWISE_90));
        List<StructurePiece> longestTunnel = null;
        for (int i = 3; i > 0; i--) {
            List<StructurePiece> tunnel = new ArrayList<>();
            rotation = rotations.remove(this.random.nextInt(i));
            Direction direction = rotation.rotate(Direction.SOUTH);
            if (buildTunnelFromRoom(lobby, tunnel, rotation, direction, origin)) {
                longestTunnel = tunnel;
                break;
            } else if (longestTunnel == null || tunnel.size() > longestTunnel.size()) {
                longestTunnel = tunnel;
            }
        }
        this.nodes.addAll(longestTunnel);
    }

    @Nullable
    @SuppressWarnings("SameParameterValue")
    private StructurePiece seekLastRoomNode(int minWidth) {
        for (int i = this.nodes.size() - 1; i >= 0; i--) {
            StructurePiece piece = this.nodes.get(i);
            BoundingBox box = piece.getBoundingBox();
            if (box.getXSpan() > minWidth && box.getZSpan() > minWidth) {
                return piece;
            }
        }

        return null;
    }

    private void buildSurfaceTunnel(LevelHeightAccessor level, ChunkGenerator chunkGenerator, RandomState randomState) {
        final int shrink = 3;
        StructurePiece lobby = this.seekLastRoomNode(shrink * 2);
        if (lobby == null) return; // Not likely to happen ever, but just in case for wackiness

        BoundingBox lobbyBounds = lobby.getBoundingBox();
        BlockPos entranceRoomCenter = lobbyBounds.getCenter();
        int topSurfaceY = chunkGenerator.getFirstOccupiedHeight(entranceRoomCenter.getX(), entranceRoomCenter.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, level, randomState);

        int roomCeiling = lobbyBounds.maxY() + 1; // Right above the lobby's ceiling blocks

        if (roomCeiling > topSurfaceY)
            return; // Room somehow clips through top surface of terrain, no room for generating ruins

        int ruinsTopY = Math.max(roomCeiling, topSurfaceY + 4); // A few extra blocks above the surface centered in this box

        int minX = lobbyBounds.minX() + shrink;
        int minZ = lobbyBounds.minZ() + shrink;
        int maxX = lobbyBounds.maxX() - shrink;
        int maxZ = lobbyBounds.maxZ() - shrink;
        // Corner-sorting in case any of the templates get customized out of default expectations
        BoundingBox upwardsTunnelBox = new BoundingBox(
            Math.min(minX, maxX),
            roomCeiling,
            Math.min(minZ, maxZ),
            Math.max(minX, maxX),
            ruinsTopY,
            Math.max(minZ, maxZ)
        );
        this.nodes.add(new BronzeDungeonSurfaceRuins(upwardsTunnelBox));
    }

    /**
     * Builds a tunnel from a symmetrical room to make an entrance.
     *
     * @param connectedRoom The {@link StructurePiece} for the room that the tunnel leads to.
     * @param list          The {@link List} of {@link StructurePiece}s to add to.
     * @param rotation      The {@link Rotation} of the template.
     * @param direction     The {@link Direction} to build in.
     * @param origin        The start {@link BlockPos} of the structure.
     * @return Whether the tunnel should stop generating, as a {@link Boolean}.
     */
    public boolean buildTunnelFromRoom(StructurePiece connectedRoom, List<StructurePiece> list, Rotation rotation, Direction direction, BlockPos origin) {
        StructureTemplate template = this.manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze_dungeon/entrance"));
        BlockPos startPos = BlockLogicUtil.tunnelFromEvenSquareRoom(connectedRoom.getBoundingBox(), direction, template.getSize().getX());
        BronzeDungeonPiece entrance = this.chooseRoom("entrance", startPos, rotation, this.processors.roomSettings());
        list.add(entrance);
        startPos = startPos.relative(direction);

        int length = template.getSize().getZ();
        boolean noOverlap = false;
        boolean reachedAir = false;
        BlockPos pos;
        int i = 0;
        do {
            pos = startPos.relative(direction, i);
            BronzeDungeonPiece tunnel = this.chooseRoom("end_corridor", pos, rotation, this.processors.tunnelSettings());

            // Skip the connected piece, since the tunnel will be digging into it.
            StructurePiece col = null;
            for (StructurePiece piece : this.nodes) {
                if (piece != null && piece != connectedRoom && piece.getBoundingBox().intersects(tunnel.getBoundingBox())) {
                    col = piece;
                    break;
                }
            }
            if (col != null) {
                break;
            } else {
                noOverlap = true;
                list.add(tunnel);
                connectedRoom = tunnel;
            }
            i += length;

            // If the tunnel doesn't find an opening, we can try making another one.
            if (this.checkForAirAtPos(pos.getX(), pos.getY(), pos.getZ()) && this.checkForAirAtPos(pos.getX(), tunnel.getBoundingBox().maxY(), pos.getZ())) {
                reachedAir = true;
                break;
            }

        } while (Math.abs(origin.getX() - pos.getX()) < 100 && Math.abs(origin.getZ() - pos.getZ()) < 100); // At some point, the tunnel should cut off to avoid issues.

        return noOverlap && reachedAir;
    }

    public BronzeDungeonPiece chooseRoom(String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        SimpleWeightedRandomList<RoomProvider<?>> list = ROOM_OPTIONS.get(name);
        if (list != null) {
            Optional<RoomProvider<?>> option = list.getRandomValue(this.random);
            if (option.isPresent()) {
                return option.get().provide(this.manager, pos, rotation, processors);
            }
        }
        return new BronzeDungeonRoom(this.manager, name, pos, rotation, processors);
    }

    /**
     * Adds all the pieces to the StructurePieceAccessor so that it can generate in the world.
     *
     * @param builder The {@link StructurePiecesBuilder} for the structure.
     */
    public void populatePiecesBuilder(StructurePiecesBuilder builder) {
        StructurePiece bossRoom = this.nodes.remove(0);
        this.nodes.forEach(builder::addPiece);
        this.edges.values().forEach(map -> map.values().forEach(connection -> builder.addPiece(connection.hallway)));
        // Add the tunnel at the end to make sure the tunnel doesn't dig into the boss room, since we have special doorway blocks.
        builder.addPiece(bossRoom);
    }

    /**
     * Checks if there is a hallway going in the given direction from the room.
     *
     * @param node      The {@link StructurePiece} node.
     * @param direction The {@link Direction} to check.
     * @return Whether there is a hallway, as a {@link Boolean}.
     */
    private boolean hasConnection(StructurePiece node, Direction direction) {
        Map<Direction, Connection> map = this.edges.get(node);
        return map != null && map.containsKey(direction);
    }

    private boolean checkForAirAtPos(int x, int y, int z) {
        NoiseColumn column = this.context.chunkGenerator().getBaseColumn(x, z, this.context.heightAccessor(), this.context.randomState());
        return column.getBlock(y).isAir();
    }

    /**
     * Checks whether the room position at the current chunk is less than three chunks away from the structure's starting chunk.
     *
     * @param chunkPos The starting {@link ChunkPos}.
     * @param pos      The {@link BlockPos} for the current room.
     * @return Whether the room position is close enough to the starting chunk, as a {@link Boolean}.
     */
    private boolean isCloseToCenter(ChunkPos chunkPos, BlockPos pos) {
        ChunkPos currentChunk = new ChunkPos(pos);
        return chunkPos.getChessboardDistance(currentChunk) <= 3;
    }

    /**
     * Checks whether the room is covered at all four corner columns.
     *
     * @param room The {@link BoundingBox} of the room.
     * @return Whether the room is covered, as a {@link Boolean}.
     */
    private boolean isCoveredAtPos(BoundingBox room) {
        ChunkGenerator chunkGenerator = this.context.chunkGenerator();
        LevelHeightAccessor heightAccessor = this.context.heightAccessor();
        RandomState randomState = this.context.randomState();
        int minX = room.minX() - 1;
        int minZ = room.minZ() - 1;
        int maxX = room.maxX() + 1;
        int maxZ = room.maxZ() + 1;

        NoiseColumn[] columns = {
                chunkGenerator.getBaseColumn(minX, minZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(minX, maxZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(maxX, minZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(maxX, maxZ, heightAccessor, randomState)
        };

        return isSolidInColumns(columns, room.minY() - 1, room.maxY() + 1);
    }

    /**
     * Find a viable direction for the boss room to face.
     *
     * @param minPos The starting corner {@link BlockPos} for the boss room.
     * @param maxPos The ending corner {@link BlockPos} for the boss room.
     * @return A viable {@link Rotation} direction.
     */
    @Nullable
    private Rotation getBossRoomRotation(BlockPos minPos, BlockPos maxPos) {
        StructureTemplate template = this.context.structureTemplateManager().getOrCreate(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze_dungeon/chest_room"));
        RandomSource random = this.context.random();
        BoundingBox bossBox = new BoundingBox(minPos.getX(), minPos.getY(), minPos.getZ(), maxPos.getX(), maxPos.getY(), maxPos.getZ());

        for (Rotation rotation : Rotation.getShuffled(random)) {
            Direction direction = rotation.rotate(Direction.SOUTH);
            BlockPos.MutableBlockPos neighbor = BlockLogicUtil.tunnelFromEvenSquareRoom(bossBox, direction, this.nodeWidth).mutable();
            neighbor = neighbor.move(direction.getStepX() * (this.edgeLength + bossBox.getXSpan()), 0, direction.getStepZ() * (this.edgeLength + bossBox.getZSpan()));
            if (isCoveredAtPos(template.getBoundingBox(neighbor, rotation, BlockPos.ZERO, Mirror.NONE))) {
                return rotation;
            }
        }

        return null; // Returns null if there isn't a viable direction for the boss room.
    }

    /**
     * Iterates through an array of noise columns and checks if any of them have air in the range specified.
     *
     * @param columns The {@link NoiseColumn NoiseColumn[]} array to check.
     * @param minY    The minimum y {@link Integer} for the range.
     * @param maxY    The maximum y {@link Integer} for the range.
     * @return If there is no air in the range, as a {@link Boolean}.
     */
    private static boolean isSolidInColumns(NoiseColumn[] columns, int minY, int maxY) {
        for (NoiseColumn column : columns) {
            for (int y = minY; y <= maxY; ++y) {
                if (column.getBlock(y).isAir() || column.getBlock(y).is(AetherTags.Blocks.NON_BRONZE_DUNGEON_SPAWNABLE)) {
                    return false;
                }
            }
        }
        return true;
    }

    @FunctionalInterface
    public interface RoomProvider<T extends BronzeDungeonPiece> {
        T provide(StructureTemplateManager manager, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors);
    }

    /**
     * An edge going in one direction. When iterating through the graph, you cannot go backward through these.
     */
    private class Connection {
        public final StructurePiece start;
        public final StructurePiece end;
        public final StructurePiece hallway;

        /**
         * Creates a new Connection and adds it to the map.
         *
         * @param start     The {@link StructurePiece} at the start.
         * @param end       The {@link StructurePiece} at the end.
         * @param hallway   The {@link StructurePiece} for the hallway connecting the rooms.
         * @param direction The {@link Direction} of the connection.
         */
        public Connection(StructurePiece start, StructurePiece end, StructurePiece hallway, Direction direction) {
            this.start = start;
            this.end = end;
            this.hallway = hallway;
            BronzeDungeonBuilder.this.edges.computeIfAbsent(start, piece -> new HashMap<>()).put(direction, this);
        }

        public StructurePiece endPiece() {
            return this.end;
        }
    }
}
