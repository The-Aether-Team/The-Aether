package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.util.BlockLogicUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.*;

/**
 * A directed graph used for assembling the bronze dungeon. This allows us to keep track of how far away a room is
 * from the starting point by counting along the path.
 *
 * https://en.wikipedia.org/wiki/Directed_graph
 */
public class BronzeDungeonGraph {
    public final StructurePiecesBuilder builder;
    public final Structure.GenerationContext context;
    public final StructureTemplateManager manager;
    public final RandomSource random;

    public final int nodeWidth;
    public final int edgeWidth;
    public final int edgeLength;

    public final int maxSize;
    public final List<StructurePiece> nodes = new ArrayList<>();

    public final Map<StructurePiece, Map<Direction, Connection>> edges = new HashMap<>();


    public BronzeDungeonGraph(StructurePiecesBuilder builder, Structure.GenerationContext context, int maxSize) {
        this.builder = builder;
        this.context = context;
        this.manager = context.structureTemplateManager();
        this.random = context.random();

        Vec3i nodeSize = context.structureTemplateManager().getOrCreate(new ResourceLocation(Aether.MODID, "bronze_dungeon/chest_room")).getSize();
        this.nodeWidth = nodeSize.getX();

        Vec3i edgeSize = context.structureTemplateManager().getOrCreate(new ResourceLocation(Aether.MODID, "bronze_dungeon/square_tunnel")).getSize();
        this.edgeWidth = edgeSize.getX();
        this.edgeLength = edgeSize.getZ();

        this.maxSize = maxSize;
    }

    public void initializeDungeon(BlockPos startPos) {
        BronzeDungeonPieces.BossRoom bossRoom = new BronzeDungeonPieces.BossRoom(this.manager, 0, "boss_room", startPos, Rotation.getRandom(this.random));
        Direction direction = bossRoom.getOrientation();

        BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(bossRoom.getBoundingBox().moved(0, 2, 0), direction, this.edgeWidth);
        BronzeDungeonPieces.DungeonRoom hallway = new BronzeDungeonPieces.DungeonRoom(this.manager, "square_tunnel", pos, bossRoom.getRotation());
        pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
        BronzeDungeonPieces.DungeonRoom chestRoom = new BronzeDungeonPieces.DungeonRoom(manager, "chest_room", pos, hallway.getRotation());

        this.nodes.add(bossRoom);
        this.nodes.add(chestRoom);
        new Connection(bossRoom, chestRoom, hallway, direction);

        for (int i = 2; i < this.maxSize; i++) {
            propagateRooms(chestRoom);
        }
    }

    /**
     * Recursively move through the graph of rooms to add new pieces. Returns true if successful in placing a new piece.
     */
    public boolean propagateRooms(StructurePiece startNode) {
        Rotation rotation = startNode.getRotation();
        switch (this.random.nextInt(3)) {
            case 0 -> rotation = rotation.getRotated(Rotation.COUNTERCLOCKWISE_90);
            case 2 -> rotation = rotation.getRotated(Rotation.CLOCKWISE_90);
        }

        Direction direction = rotation.rotate(Direction.SOUTH);
        if (this.hasConnection(startNode, direction)) {
            if (propagateRooms(this.edges.get(startNode).get(direction).end)) {
                return true;
            }
        } else {
            BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(startNode.getBoundingBox(), direction, this.edgeWidth);
            BronzeDungeonPieces.DungeonRoom hallway = new BronzeDungeonPieces.DungeonRoom(this.manager, "square_tunnel", pos, rotation);
            pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
            BronzeDungeonPieces.DungeonRoom chestRoom = new BronzeDungeonPieces.DungeonRoom(manager, "chest_room", pos, rotation);

            StructurePiece collisionPiece = StructurePiece.findCollisionPiece(this.nodes, chestRoom.getBoundingBox());
            if (collisionPiece == null) {
                new Connection(startNode, chestRoom, hallway, direction);
                this.nodes.add(chestRoom);
                return true;
            } else {
                boolean flag = this.edges.computeIfAbsent(collisionPiece, piece -> new HashMap<>()).values().stream()
                        .map(Connection::endPiece).anyMatch(piece -> piece == startNode);
                if (!flag/* && random.nextBoolean()*/) {
                    new Connection(startNode, chestRoom, hallway, direction);
                }
            }
        }

        return false;
    }

    /*public boolean propagateRoomsOld(BronzeDungeonPieces.DungeonRoom start) {
        Rotation rotation = start.getRotation();
        for (Rotation rot : Rotation.getShuffled(random)) {
            if (this.remainingRooms > 0 && rotation != rot) {
                rot = rotation.getRotated(rot);
                Direction direction = rot.rotate(Direction.SOUTH);
                BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(start.getBoundingBox(), direction, 6);
                BronzeDungeonPieces.DungeonRoom hallway = new BronzeDungeonPieces.DungeonRoom(manager, "square_tunnel", pos, rot);
                pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, 12);
                BronzeDungeonPieces.DungeonRoom chestRoom = new BronzeDungeonPieces.DungeonRoom(manager, "chest_room", pos, rot);
                if (pieceAccessor.findCollisionPiece(chestRoom.getBoundingBox()) == null) {
                    --this.remainingRooms;
                    pieceAccessor.addPiece(chestRoom);
                    pieceAccessor.addPiece(hallway);
                    this.addTemplateChildren(manager, chestRoom, pieceAccessor, random);
                }
            }
        }
    }*/

    /**
     * Adds all the pieces to the StructurePieceAccessor so that it can generate in the world.
     */
    public void populatePiecesBuilder() {
        StructurePiece bossRoom = this.nodes.remove(0);
        this.nodes.forEach(this.builder::addPiece);
        this.edges.values().forEach(map -> map.values().forEach(connection -> this.builder.addPiece(connection.hallway)));
        // Add the tunnel at the end to make sure the tunnel doesn't dig into the boss room, since we have special doorway blocks.
        this.builder.addPiece(bossRoom);
    }

    private boolean hasConnection(StructurePiece node, Direction direction) {
        return this.edges.get(node) instanceof HashMap map && map.containsKey(direction);
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
