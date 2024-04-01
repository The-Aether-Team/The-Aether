package com.aetherteam.aether.advancement;

import com.aetherteam.aether.Aether;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherAdvancementTriggers {

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Aether.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, IncubationTrigger> INCUBATION_TRIGGER = TRIGGERS.register("incubation_trigger", IncubationTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, LoreTrigger> LORE_ENTRY = TRIGGERS.register("lore_entry", LoreTrigger::new);
}
