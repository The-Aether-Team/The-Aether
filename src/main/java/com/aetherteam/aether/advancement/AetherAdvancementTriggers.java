package com.aetherteam.aether.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancementTriggers {
    public static void init() {
        CriteriaTriggers.register(IncubationTrigger.INSTANCE);
        CriteriaTriggers.register(LoreTrigger.INSTANCE);
    }
}
