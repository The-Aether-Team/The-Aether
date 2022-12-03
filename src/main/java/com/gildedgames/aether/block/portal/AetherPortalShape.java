package com.gildedgames.aether.block.portal;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.util.ConstantsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Based on {@link net.minecraft.world.level.portal.PortalShape}.
 */
public class AetherPortalShape {
    private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) -> state.is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS);
    private final LevelAccessor level;
    private final Direction.Axis axis;
    private final Direction rightDir;
    private int numPortalBlocks;
    @Nullable
    private BlockPos bottomLeft;
    private int height;
    private final int width;

    public static Optional<AetherPortalShape> findEmptyAetherPortalShape(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis) {
        return findPortalShape(level, bottomLeft, (shape) -> shape.isValid() && shape.numPortalBlocks == 0, axis);
    }

    public static Optional<AetherPortalShape> findPortalShape(LevelAccessor level, BlockPos bottomLeft, Predicate<AetherPortalShape> predicate, Direction.Axis axis) {
        Optional<AetherPortalShape> optional = Optional.of(new AetherPortalShape(level, bottomLeft, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis directionAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(new AetherPortalShape(level, bottomLeft, directionAxis)).filter(predicate);
        }
    }

    public AetherPortalShape(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis) {
        this.level = level;
        this.axis = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.bottomLeft = this.calculateBottomLeft(bottomLeft);
        if (this.bottomLeft == null) {
            this.bottomLeft = bottomLeft;
            this.width = 1;
            this.height = 1;
        } else {
            this.width = this.calculateWidth();
            if (this.width > 0) {
                this.height = this.calculateHeight();
            }
        }
    }

    @Nullable
    private BlockPos calculateBottomLeft(BlockPos pos) {
        for (int i = Math.max(this.level.getMinBuildHeight(), pos.getY() - 21); pos.getY() > i && isEmpty(this.level.getBlockState(pos.below())); pos = pos.below()) { }
        Direction direction = this.rightDir.getOpposite();
        int j = this.getDistanceUntilEdgeAboveFrame(pos, direction) - 1;
        return j < 0 ? null : pos.relative(direction, j);
    }

    private int calculateWidth() {
        int i = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
        return i >= 2 && i <= 21 ? i : 0;
    }

    private int getDistanceUntilEdgeAboveFrame(BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int i = 0; i <= 21; ++i) {
            mutablePos.set(pos).move(direction, i);
            BlockState blockState = this.level.getBlockState(mutablePos);
            if (!isEmpty(blockState)) {
                if (FRAME.test(blockState, this.level, mutablePos)) {
                    return i;
                }
                break;
            }
            BlockState belowState = this.level.getBlockState(mutablePos.move(Direction.DOWN));
            if (!FRAME.test(belowState, this.level, mutablePos)) {
                break;
            }
        }
        return 0;
    }

    private int calculateHeight() {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int i = this.getDistanceUntilTop(mutablePos);
        return i >= 3 && i <= 21 && this.hasTopFrame(mutablePos, i) ? i : 0;
    }

    private boolean hasTopFrame(BlockPos.MutableBlockPos mutablePos, int amount) {
        for (int i = 0; i < this.width; ++i) {
            BlockPos.MutableBlockPos movedPos = mutablePos.set(this.bottomLeft).move(Direction.UP, amount).move(this.rightDir, i);
            if (!FRAME.test(this.level.getBlockState(movedPos), this.level, movedPos)) {
                return false;
            }
        }
        return true;
    }

    private int getDistanceUntilTop(BlockPos.MutableBlockPos mutablePos) {
        for (int i = 0; i < 21; ++i) {
            mutablePos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
            if (!FRAME.test(this.level.getBlockState(mutablePos), this.level, mutablePos)) {
                return i;
            }

            mutablePos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
            if (!FRAME.test(this.level.getBlockState(mutablePos), this.level, mutablePos)) {
                return i;
            }

            for (int j = 0; j < this.width; ++j) {
                mutablePos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
                BlockState blockState = this.level.getBlockState(mutablePos);
                if (!isEmpty(blockState)) {
                    return i;
                }
                if (blockState.is(AetherBlocks.AETHER_PORTAL.get())) {
                    ++this.numPortalBlocks;
                }
            }
        }

        return 21;
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) || state.is(AetherBlocks.AETHER_PORTAL.get());
    }

    public boolean isValid() {
        return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void createPortalBlocks() {
        BlockState blockState = AetherBlocks.AETHER_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((pos) -> this.level.setBlock(pos, blockState, 2 | 16));
    }

    public boolean isComplete() {
        return this.isValid() && this.numPortalBlocks == this.width * this.height;
    }
}
