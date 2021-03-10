package com.gildedgames.aether.common.item.tools.abilities;

import java.util.UUID;

public interface IValkyrieToolItem
{
    UUID REACH_MODIFIER_UUID = new UUID(-2346749345421374890L, -8027384656528210169L);

    default double getReachDistanceModifier() {
        return 3.0D;
    }
}
