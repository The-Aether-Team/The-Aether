package com.aetherteam.aether.entity.ai.navigator;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class AxisAlignedNodeEvaluator extends NodeEvaluator {
    private final Long2ObjectMap<BlockPathTypes> pathTypeByPosCache = new Long2ObjectOpenHashMap<>();

    @Override
    public void prepare(PathNavigationRegion level, Mob mob) {
        super.prepare(level, mob);
        this.pathTypeByPosCache.clear();
    }

    @Override
    public void done() {
        this.pathTypeByPosCache.clear();
        super.done();
//        Aether.LOGGER.info("---------------------------------------------------------------------");
    }

    @Nullable
    @Override
    public Node getStart() {
        BlockPos pos = this.mob.blockPosition();
        BlockPathTypes startPathType = this.getCachedBlockPathType(pos.getX(), pos.getY(), pos.getZ());
        if (this.mob.getPathfindingMalus(startPathType) < 0.0F) {
            for(BlockPos blockpos : this.iteratePathfindingStartNodeCandidatePositions(this.mob)) {
                BlockPathTypes blockpathtypes = this.getCachedBlockPathType(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                if (this.mob.getPathfindingMalus(blockpathtypes) >= 0.0F) {
                    return this.getStartNode(blockpos);
                }
            }
        }
        return this.getStartNode(this.mob.blockPosition());
    }

    @Nullable
    protected Node getStartNode(BlockPos pos) {
        Node node = this.getNode(pos);
        node.type = this.getCachedBlockPathType(node.x, node.y, node.z);
        node.costMalus = this.mob.getPathfindingMalus(node.type);

        return node;
    }

    @Override
    @Nullable
    protected Node getNode(int pX, int pY, int pZ) {
        Node node = null;
        BlockPathTypes blockpathtypes = this.getCachedBlockPathType(pX, pY, pZ);
        float f = this.mob.getPathfindingMalus(blockpathtypes);
        if (f >= 0.0F) {
            node = super.getNode(pX, pY, pZ);
            node.type = blockpathtypes;
            node.costMalus = Math.max(node.costMalus, f);
            if (blockpathtypes == BlockPathTypes.WALKABLE) {
                ++node.costMalus;
            }
        }

        return node;
    }

    @Nullable
    @Override
    public Target getGoal(double pX, double pY, double pZ) {
        return this.getTargetFromNode(super.getNode(Mth.floor(pX), Mth.floor(pY), Mth.floor(pZ)));
    }

    @Override
    public int getNeighbors(Node[] neighbors, Node baseNode) {
        int i = 0;
        Direction currentDirection = this.calculateDirection(baseNode);
        for (Direction direction : Direction.values()) {
            Node node = this.getNode(baseNode.x + direction.getStepX(), baseNode.y + direction.getStepY(), baseNode.z + direction.getStepZ());
            if (this.isOpen(node)) {
                if (direction != currentDirection) {
                    node.costMalus += 1;
                }
                neighbors[i++] = node;
                Aether.LOGGER.info(node.toString());
            }
        }
        return i;
    }

    /**
     * Determine the current direction the path is going in based on the difference in position between two nodes.
     */
    private Direction calculateDirection(Node baseNode) {
        Node lastNode = baseNode.cameFrom;
        if (lastNode == null) {
            return null;
        }
        return Direction.fromNormal(baseNode.x - lastNode.x, baseNode.y - lastNode.y, baseNode.z - lastNode.z);
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter getter, int x, int y, int z, Mob mob) {
        return this.getBlockPathType(getter, x, y, z);
    }

    private boolean isOpen(@Nullable Node pNode) {
        return pNode != null && !pNode.closed;
    }

    public BlockPathTypes getCachedBlockPathType(int pX, int pY, int pZ) {
        return this.pathTypeByPosCache.computeIfAbsent(BlockPos.asLong(pX, pY, pZ), (hash) -> {
            return this.getBlockPathType(this.level, pX, pY, pZ);
        });
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);
        BlockState blockState = level.getBlockState(pos);

        if (blockState.is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
            return BlockPathTypes.BLOCKED;
        } else {
            return BlockPathTypes.OPEN;
        }
    }

    private Iterable<BlockPos> iteratePathfindingStartNodeCandidatePositions(Mob mob) {
        float f = 1.0F;
        AABB aabb = mob.getBoundingBox();
        boolean flag = aabb.getSize() < 1.0D;
        if (!flag) {
            return List.of(BlockPos.containing(aabb.minX, mob.getBlockY(), aabb.minZ), BlockPos.containing(aabb.minX, mob.getBlockY(), aabb.maxZ), BlockPos.containing(aabb.maxX, mob.getBlockY(), aabb.minZ), BlockPos.containing(aabb.maxX, mob.getBlockY(), aabb.maxZ));
        } else {
            double d0 = Math.max(0.0D, (1.5D - aabb.getZsize()) / 2.0D);
            double d1 = Math.max(0.0D, (1.5D - aabb.getXsize()) / 2.0D);
            double d2 = Math.max(0.0D, (1.5D - aabb.getYsize()) / 2.0D);
            AABB aabb1 = aabb.inflate(d1, d2, d0);
            return BlockPos.randomBetweenClosed(mob.getRandom(), 10, Mth.floor(aabb1.minX), Mth.floor(aabb1.minY), Mth.floor(aabb1.minZ), Mth.floor(aabb1.maxX), Mth.floor(aabb1.maxY), Mth.floor(aabb1.maxZ));
        }
    }
}
