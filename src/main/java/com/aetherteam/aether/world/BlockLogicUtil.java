package com.aetherteam.aether.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class BlockLogicUtil {
    /**
     * Find the entry point for a tunnel piece to a room with an odd-numbered width
     * @param box - The room to tunnel from
     * @param direction - The direction to tunnel in
     * @param width - The width of the tunnel to build
     */
    public static BlockPos tunnelFromOddSquareRoom(BoundingBox box, Direction direction, int width) {
        int offsetFromCenter = (direction.getAxis() == Direction.Axis.X ? box.getZSpan() : box.getXSpan()) >> 1;
        int sidedOffset = (width >> 1);

        int xOffset = direction.getStepX() * offsetFromCenter - direction.getStepZ() * sidedOffset - Math.max(0, direction.getStepX());
        int zOffset = direction.getStepZ() * offsetFromCenter + direction.getStepX() * sidedOffset - Math.max(0, direction.getStepZ());

        return box.getCenter().offset(
                xOffset,
                -(box.getYSpan() >> 1),
                zOffset
        );
    }

    /**
     * Find the entry point for a tunnel piece to a room with an even-numbered width
     * @param box - The room to tunnel from
     * @param direction - The direction to tunnel in
     * @param width - The width of the tunnel to build
     */
    public static BlockPos tunnelFromEvenSquareRoom(BoundingBox box, Direction direction, int width) {
        int offsetFromCenter = (((direction.getAxis() == Direction.Axis.X ? box.getZSpan() : box.getXSpan()) + 1) >> 1);
        int sidedOffset = (width >> 1);

        int xOffset = direction.getStepX() * offsetFromCenter - direction.getStepZ() * sidedOffset - Math.max(0, direction.getStepX()) + Math.min(0, direction.getStepZ());
        int zOffset = direction.getStepZ() * offsetFromCenter + direction.getStepX() * sidedOffset - Math.max(0, direction.getStepZ()) - Math.max(0, direction.getStepX());

        return box.getCenter().offset(
                xOffset,
                -(box.getYSpan() >> 1),
                zOffset
        );
    }
}
