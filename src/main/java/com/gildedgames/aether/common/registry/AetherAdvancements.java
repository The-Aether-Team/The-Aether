package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.common.advancement.LoreTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancements
{
    public static void init() {
        CriteriaTriggers.register(LoreTrigger.INSTANCE);
    }
}
