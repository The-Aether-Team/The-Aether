package com.gildedgames.aether.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancements
{
    public static void init() {
        CriteriaTriggers.register(LoreTrigger.INSTANCE);
    }
}
