package com.gildedgames.aether.core.util;

import com.gildedgames.aether.Aether;
import net.minecraft.world.level.Level;

public final class LevelUtil {
    public static boolean isLevelAether(Level level) {
        return Aether.MODID.equals(level.dimension().location().getNamespace());
    }

    private LevelUtil() {
    }
}
