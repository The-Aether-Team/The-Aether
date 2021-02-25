package com.gildedgames.aether.item.tools.abilities;

import java.util.UUID;

public interface IValkyrieToolItem {
    UUID reachModifierUUID = new UUID(-2346749345421374890L, -8027384656528210169L); //TODO: Should this be here or in AetherPlayer?
    /**
     * @return double value for extended reach distance
     */
    default double getReachDistanceModifier() {
        return 3.0D;
    }
}
