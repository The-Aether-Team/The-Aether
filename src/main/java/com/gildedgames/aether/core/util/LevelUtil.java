package com.gildedgames.aether.core.util;

import com.gildedgames.aether.Aether;
import net.minecraft.world.level.Level;

public final class LevelUtil {
    // This way if anyone wants to create a new dimension, it will be compatible with as much Aether logic as possible
    public static boolean isLevelAether(Level level) {
        return Aether.MODID.equals(level.dimension().location().getNamespace());
    }

    private LevelUtil() {
    }
}
