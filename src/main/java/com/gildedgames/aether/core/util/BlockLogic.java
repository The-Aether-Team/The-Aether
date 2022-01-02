package com.gildedgames.aether.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.function.BiPredicate;

public final class BlockLogic
{
    public static BoundingBox tunnelFromEvenSquareRoom(BoundingBox box, Direction direction, int length, int width, int height) {
        int offsetFromCenter = (((direction.getAxis() == Direction.Axis.X ? box.getZSpan() : box.getXSpan()) + 1) >> 1) + 1;
        int sidedOffset = width >> 1;

        return tunnel(box.getCenter().offset(
                direction.getStepX() * offsetFromCenter - direction.getStepZ() * sidedOffset - Math.max(0, direction.getStepX()) - Math.max(0, direction.getStepZ()),
                -(box.getYSpan() >> 1),
                direction.getStepZ() * offsetFromCenter - direction.getStepX() * sidedOffset - Math.max(0, direction.getStepZ()) - Math.max(0, direction.getStepX())
        ), direction, length, width, height);
    }

    public static BoundingBox tunnel(BlockPos startCorner, Direction direction, int length, int width, int height) {
        int x = startCorner.getX() + direction.getStepX() * length + direction.getStepZ() * width;
        int y = startCorner.getY() + height;
        int z = startCorner.getZ() + direction.getStepZ() * length + direction.getStepX() * width;

        System.out.println(direction + ": " + startCorner.getX() + " " + startCorner.getY() + " " + startCorner.getZ());

        return new BoundingBox(startCorner).encapsulate(new BlockPos(x, y, z));
    }

    public static boolean doesAirExistNearby(BlockPos center, int radius, WorldGenLevel level) {
        return level.isEmptyBlock(center.north(radius))
                || level.isEmptyBlock(center.south(radius))
                || level.isEmptyBlock(center.west(radius))
                || level.isEmptyBlock(center.east(radius));
    }

    public static BiPredicate<BoundingBox, BlockPos> getShellingDirection(Direction.Axis axis) {
        return switch (axis) {
            case X -> (bb, pos) -> pos.getY() != bb.minY() && pos.getY() != bb.maxY() && pos.getZ() != bb.minZ() && pos.getZ() != bb.maxZ();
            case Y -> (bb, pos) -> pos.getX() != bb.minX() && pos.getX() != bb.maxX() && pos.getZ() != bb.minZ() && pos.getZ() != bb.maxZ();
            case Z -> (bb, pos) -> pos.getX() != bb.minX() && pos.getX() != bb.maxX() && pos.getY() != bb.minY() && pos.getY() != bb.maxY();
        };
    }

    private BlockLogic() { }
}
