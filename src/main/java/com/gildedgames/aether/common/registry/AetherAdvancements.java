package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.common.advancement.LoreTrigger;
import com.gildedgames.aether.common.advancement.MountTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancements
{
    public static void init() {
        CriteriaTriggers.register(MountTrigger.INSTANCE);
        CriteriaTriggers.register(LoreTrigger.INSTANCE);
    }
}
