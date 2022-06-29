package com.gildedgames.aether.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class BlockLogic
{
    private BlockLogic() { }

    public static boolean doesAirExistNearby(BlockPos center, int radius, WorldGenLevel level) {
        return level.isEmptyBlock(center.north(radius))
                || level.isEmptyBlock(center.south(radius))
                || level.isEmptyBlock(center.west(radius))
                || level.isEmptyBlock(center.east(radius));
    }
}
