package com.gildedgames.aether.world.processor;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering StructureProcessorTypes
 */
public class AetherStructureProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registry.STRUCTURE_PROCESSOR_REGISTRY, Aether.MODID);

    public static final RegistryObject<StructureProcessorType<HellfireStoneProcessor>> HELLFIRE_STONE_REPLACE = STRUCTURE_PROCESSOR_TYPES.register("hellfire_stone_replace", () -> () -> HellfireStoneProcessor.CODEC);
}
