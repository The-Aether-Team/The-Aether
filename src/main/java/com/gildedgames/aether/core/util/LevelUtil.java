package com.gildedgames.aether.core.util;

import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import net.minecraft.world.level.Level;

public final class LevelUtil {
    public static boolean isLevelAether(Level level) {
        return level.dimension() == AetherDimensions.AETHER_LEVEL; //TODO: Verify this works.
    }
}
