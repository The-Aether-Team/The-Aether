package com.gildedgames.aether.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancementTriggers
{
    public static void init() {
        CriteriaTriggers.register(LoreTrigger.INSTANCE);
    }
}
