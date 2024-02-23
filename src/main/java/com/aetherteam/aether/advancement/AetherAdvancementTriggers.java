package com.aetherteam.aether.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancementTriggers {
    public static final IncubationTrigger INCUBATION_TRIGGER = CriteriaTriggers.register("aether:incubation_trigger", new IncubationTrigger());
    public static final LoreTrigger LORE_ENTRY = CriteriaTriggers.register("aether:lore_entry", new LoreTrigger());
}
