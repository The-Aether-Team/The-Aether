package com.gildedgames.aether.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public final class AetherAdvancementTriggers
{
    public static void init() {
        CriteriaTriggers.register(LoreTrigger.INSTANCE);
    }
}
