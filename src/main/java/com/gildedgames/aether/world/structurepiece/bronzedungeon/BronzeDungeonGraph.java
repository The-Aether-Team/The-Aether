package com.gildedgames.aether.world.structurepiece.bronzedungeon;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.util.BlockLogicUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.*;

/**
 * A directed graph used for assembling the bronze dungeon. This allows us to keep track of how far away a room is
 * from the starting point by counting along the path.
 *
 * https://en.wikipedia.org/wiki/Directed_graph
 */
public class BronzeDungeonGraph {
    public final Structure.GenerationContext context;
    public final StructureTemplateManager manager;
    public final RandomSource random;

    public final int nodeWidth;
    public final int edgeWidth;
    public final int edgeLength;
    public final int maxSize;

    public final List<StructurePiece> nodes = new ArrayList<>();
    public final Map<StructurePiece, Map<Direction, Connection>> edges = new HashMap<>();


    public BronzeDungeonGraph(Structure.GenerationContext context, int maxSize) {
        this.context = context;
        this.manager = context.structureTemplateManager();
        this.random = context.random();

        Vec3i nodeSize = context.structureTemplateManager().getOrCreate(new ResourceLocation(Aether.MODID, "bronze_dungeon/chest_room")).getSize();
        this.nodeWidth = nodeSize.getX();

        Vec3i edgeSize = context.structureTemplateManager().getOrCreate(new ResourceLocation(Aether.MODID, "bronze_dungeon/square_tunnel")).getSize();
        this.edgeWidth = edgeSize.getX();
        this.edgeLength = edgeSize.getZ();

        this.maxSize = Math.max(3, maxSize);
    }

    public void initializeDungeon(BlockPos startPos) {
        BronzeBossRoom bossRoom = new BronzeBossRoom(this.manager, "boss_room", startPos, Rotation.getRandom(this.random));
        Direction direction = bossRoom.getOrientation();

        BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(bossRoom.getBoundingBox().moved(0, 2, 0), direction, this.edgeWidth);
        BronzeDungeonRoom hallway = new BronzeDungeonRoom(this.manager, "square_tunnel", pos, bossRoom.getRotation());
        pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
        BronzeDungeonRoom chestRoom = new BronzeDungeonRoom(manager, "chest_room", pos, hallway.getRotation());

        this.nodes.add(bossRoom);
        this.nodes.add(chestRoom);
        new Connection(bossRoom, chestRoom, hallway, direction);

        for (int i = 2; i < this.maxSize - 1; ++i) {
            propagateRooms(chestRoom, false);
        }

        propagateRooms(chestRoom, true);
        StructurePiece lobby = this.nodes.get(this.nodes.size() - 1);
        this.buildEndTunnel(lobby, startPos);
    }

    /**
     * Recursively move through the graph of rooms to add new pieces. Returns true if successful in placing a new piece.
     */
    private boolean propagateRooms(StructurePiece startNode, boolean placeLobby) {
        Rotation rotation = startNode.getRotation();
        List<Rotation> rotations = new ArrayList<>(3);
        rotations.add(rotation.getRotated(Rotation.COUNTERCLOCKWISE_90));
        rotations.add(rotation);
        rotations.add(rotation.getRotated(Rotation.CLOCKWISE_90));
        String roomName = placeLobby ? "lobby" : "chest_room";

        // Attempt to generate a room in each direction
        for (int i = 3; i > 0; i--) {
            rotation = rotations.remove(this.random.nextInt(i));
            Direction direction = rotation.rotate(Direction.SOUTH);
            if (this.hasConnection(startNode, direction)) {
                if (propagateRooms(this.edges.get(startNode).get(direction).end, placeLobby)) {
                    return true;
                }
            } else {
                BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(startNode.getBoundingBox(), direction, this.edgeWidth);
                BronzeDungeonRoom hallway = new BronzeDungeonRoom(this.manager, "square_tunnel", pos, rotation);
                pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
                BronzeDungeonRoom room = new BronzeDungeonRoom(this.manager, roomName, pos, rotation);
                StructurePiece collisionPiece = StructurePiece.findCollisionPiece(this.nodes, room.getBoundingBox());

                if (collisionPiece == null) {
                    new Connection(startNode, room, hallway, direction);
                    this.nodes.add(room);
                    return true;
                } else { // If there's a piece in the way, see if there's a connection already. If not, make one. Then continue the loop.
                    boolean flag = this.edges.computeIfAbsent(collisionPiece, piece -> new HashMap<>()).values().stream()
                            .map(Connection::endPiece).anyMatch(piece -> piece == startNode);
                    if (!flag) {
                        new Connection(startNode, room, hallway, direction);
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
        for (int i = 3; i > 0; i--) {
            rotation = rotations.remove(this.random.nextInt(i));
            Direction direction = rotation.rotate(Direction.SOUTH);
            if (buildTunnelFromRoom(lobby, rotation, direction, origin)) {
                break;
            }
        }
    }

    /**
     * Builds a tunnel from a symmetrical room to make an entrance.
     *
     * @param connectedRoom - The room the tunnel leads to
     * @param rotation      - The rotation of the template
     * @param direction     - The direction to build in
     * @param origin        - The start position of the structure
     */
    public boolean buildTunnelFromRoom(StructurePiece connectedRoom, Rotation rotation, Direction direction, BlockPos origin) {
        StructureTemplate template = this.manager.getOrCreate(new ResourceLocation(Aether.MODID, "bronze_dungeon/end_corridor"));
        BlockPos startPos = BlockLogicUtil.tunnelFromEvenSquareRoom(connectedRoom.getBoundingBox(), direction, template.getSize().getX());
        int length = template.getSize().getZ();
        boolean noOverlap = false;
        boolean reachedAir = false;
        BlockPos pos;
        int i = 0;
        do {
            pos = startPos.offset(direction.getStepX() * i, 0, direction.getStepZ() * i);
            BronzeTunnel tunnel = new BronzeTunnel(this.manager, "end_corridor", pos, rotation);

            //Skip the connected piece, since the tunnel will be digging into it.
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
                this.nodes.add(tunnel);
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

    /** Adds all the pieces to the StructurePieceAccessor so that it can generate in the world. */
    public void populatePiecesBuilder(StructurePiecesBuilder builder) {
        StructurePiece bossRoom = this.nodes.remove(0);
        this.nodes.forEach(builder::addPiece);
        this.edges.values().forEach(map -> map.values().forEach(connection -> builder.addPiece(connection.hallway)));
        // Add the tunnel at the end to make sure the tunnel doesn't dig into the boss room, since we have special doorway blocks.
        builder.addPiece(bossRoom);
    }

    /** Returns true if there is a hallway going in the given direction from the room. */
    private boolean hasConnection(StructurePiece node, Direction direction) {
        Map<Direction, Connection> map = this.edges.get(node);
        return map != null && map.containsKey(direction);
    }

    private boolean checkForAirAtPos(int x, int y, int z) {
        NoiseColumn column = this.context.chunkGenerator().getBaseColumn(x, z, this.context.heightAccessor(), this.context.randomState());
        return column.getBlock(y).isAir();
    }

    private boolean isCoveredAtPos(StructurePiece room, Direction direction) {
        return false;
    }

    /** An edge going in one direction. When iterating through the graph, you cannot go backward through these. */
    class Connection {
        public final StructurePiece start;
        public final StructurePiece end;
        public final StructurePiece hallway;

        /** Creates a new Connection and adds it to the map. */
        public Connection(StructurePiece start, StructurePiece end, StructurePiece hallway, Direction direction) {
            this.start = start;
            this.end = end;
            this.hallway = hallway;
            BronzeDungeonGraph.this.edges.computeIfAbsent(start, piece -> new HashMap<>()).put(direction, this);
        }

        public StructurePiece endPiece() {
            return end;
        }
    }
}