package com.gildedgames.aether.advancement;

import com.gildedgames.aether.advancement.triggers.LoreTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancements
{
    public static void init() {
        CriteriaTriggers.register(LoreTrigger.INSTANCE);
    }
}
