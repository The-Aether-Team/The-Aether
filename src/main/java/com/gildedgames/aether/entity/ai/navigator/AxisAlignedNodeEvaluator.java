package com.gildedgames.aether.entity.ai.navigator;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
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
import org.jetbrains.annotations.Nullable;

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
        Aether.LOGGER.info("---------------------------------------------------------------------");
    }

    @Nullable
    @Override
    public Node getStart() {
        BlockPos pos = this.mob.blockPosition();
        BlockPathTypes startPathType = this.getCachedBlockPathType(pos.getX(), pos.getY(), pos.getZ());
        if (this.mob.getPathfindingMalus(startPathType) < 0.0F) {
            for(BlockPos blockpos : this.mob.iteratePathfindingStartNodeCandidatePositions()) {
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
        if (node != null) {
            node.type = this.getCachedBlockPathType(node.x, node.y, node.z);
            node.costMalus = this.mob.getPathfindingMalus(node.type);
        }

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
            if (node != null) {
                node.type = blockpathtypes;
                node.costMalus = Math.max(node.costMalus, f);
                if (blockpathtypes == BlockPathTypes.WALKABLE) {
                    ++node.costMalus;
                }
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
        for (Direction direction : Direction.values()) {
            Node node = this.getNode(baseNode.x + direction.getStepX(), baseNode.y + direction.getStepY(), baseNode.z + direction.getStepZ());
            if (this.isOpen(node)) {
                neighbors[i++] = node;
                Aether.LOGGER.info(node.toString());
            }
        }
        return i;
    }

    private boolean isOpen(@Nullable Node pNode) {
        return pNode != null && !pNode.closed;
    }

    public BlockPathTypes getCachedBlockPathType(int pX, int pY, int pZ) {
        return this.pathTypeByPosCache.computeIfAbsent(BlockPos.asLong(pX, pY, pZ), (hash) -> {
            return this.getBlockPathType(this.level, pX, pY, pZ, this.mob, this.entityWidth, this.entityHeight, this.entityDepth, this.canOpenDoors(), this.canPassDoors());
        });
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z, Mob mob, int pXSize, int pYSize, int pZSize, boolean pCanBreakDoors, boolean pCanEnterDoors) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);
        BlockState blockState = level.getBlockState(pos);

        if (blockState.isAir()) {
            return BlockPathTypes.OPEN;
        } else if (blockState.is(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS)) {
            return BlockPathTypes.BLOCKED;
        } else {
            return BlockPathTypes.WALKABLE;
        }
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        return this.getBlockPathType(level, x, y, z, this.mob, this.entityWidth, this.entityHeight, this.entityDepth, false, false);
    }
}
