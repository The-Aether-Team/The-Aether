package com.aetherteam.aether.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancementTriggers {
    public static void init() {
        CriteriaTriggers.register("incubation_trigger", IncubationTrigger.INSTANCE);
        CriteriaTriggers.register("lore_entry", LoreTrigger.INSTANCE);
    }
}
