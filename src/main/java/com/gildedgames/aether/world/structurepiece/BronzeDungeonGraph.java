package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.util.BlockLogicUtil;
import com.google.common.collect.ImmutableMap;
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
import java.util.stream.Collectors;

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
    public final List<Node> nodes = new ArrayList<>();
    public final Set<Connection> edges = new HashSet<>();


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

        Node startRoom = new Node(bossRoom);
        this.nodes.add(startRoom);
        Node secondRoom = new Node(chestRoom);
        this.nodes.add(secondRoom);
        Connection edge = new Connection(startRoom, secondRoom, hallway, direction);
        this.edges.add(edge);

        for (int i = 2; i < this.maxSize; i++) {
            propagateRooms(secondRoom);
        }
    }

    /**
     * Recursively move through the graph of rooms to add new pieces. Returns true if successful in placing a new piece.
     */
    public boolean propagateRooms(Node startNode) {
        Rotation rotation = startNode.room.getRotation();
        switch (this.random.nextInt(3)) {
            case 0 -> rotation = rotation.getRotated(Rotation.COUNTERCLOCKWISE_90);
            case 2 -> rotation = rotation.getRotated(Rotation.CLOCKWISE_90);
        }

        Direction direction = rotation.rotate(Direction.SOUTH);
        if (startNode.hasConnection(direction)) {
            if (propagateRooms(startNode.connections.get(direction).end)) {
                return true;
            }
        } else {
            BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(startNode.room.getBoundingBox(), direction, this.edgeWidth);
            BronzeDungeonPieces.DungeonRoom hallway = new BronzeDungeonPieces.DungeonRoom(this.manager, "square_tunnel", pos, rotation);
            pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
            BronzeDungeonPieces.DungeonRoom chestRoom = new BronzeDungeonPieces.DungeonRoom(manager, "chest_room", pos, rotation);
            StructurePiece collisionPiece = StructurePiece.findCollisionPiece(this.nodes.stream().map(Node::getRoom).collect(Collectors.toList()), chestRoom.getBoundingBox());
            if (collisionPiece == null) {
                Node endNode = new Node(chestRoom);
                Connection edge = new Connection(startNode, endNode, hallway, direction);
                this.nodes.add(endNode);
                this.edges.add(edge);
                return true;
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
     * Adds all of the pieces to the StructurePieceAccessor so that it can generate in the world.
     */
    public void populatePiecesBuilder() {
        StructurePiece bossRoom = this.nodes.remove(0).room;
        this.nodes.forEach(node -> builder.addPiece(node.room));
        edges.forEach(edge -> builder.addPiece(edge.hallway));
        // Add the tunnel at the end to make sure the tunnel doesn't dig into the boss room, since we have special doorway blocks.
        builder.addPiece(bossRoom);
    }

    static class Node {
        public final StructurePiece room;
        final HashMap<Direction, Connection> connections;

        public Node(StructurePiece room) {
            this.room = room;
            this.connections = new HashMap<>();
        }

        public boolean hasConnection(Direction direction) {
            return this.connections.containsKey(direction);
        }

        public StructurePiece getRoom() {
            return this.room;
        }
    }

    /* An edge going in one direction. When iterating through the graph, you cannot go backward through these. */
    static class Connection {
        public final Node start;
        public final Node end;
        public final StructurePiece hallway;

        public Connection(Node start, Node end, StructurePiece hallway, Direction direction) {
            this.start = start;
            this.end = end;
            this.hallway = hallway;
            start.connections.put(direction, this);
        }
    }
}
