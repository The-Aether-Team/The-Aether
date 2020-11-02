package com.aether.item;

import java.util.UUID;

public interface IValkyrieToolItem {
    UUID reachModifierUUID = UUID.fromString("df6eabe7-6947-4a56-9099-002f90370707");
    /**
     * @return double value for extended reach distance
     */
    default double getReachDistanceModifier() {
        return 3.0D;
    }
}
