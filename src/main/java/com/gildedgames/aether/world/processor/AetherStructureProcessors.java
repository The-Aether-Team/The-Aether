package com.gildedgames.aether.world.processor;

import com.gildedgames.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering StructureProcessorTypes
 */
public class AetherStructureProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Aether.MODID);

    public static final RegistryObject<StructureProcessorType<NoReplaceProcessor>> NO_REPLACE = STRUCTURE_PROCESSOR_TYPES.register("no_replace", () -> () -> NoReplaceProcessor.CODEC);

    public static final RegistryObject<StructureProcessorType<VerticalGradientProcessor>> VERTICAL_GRADIENT = STRUCTURE_PROCESSOR_TYPES.register("vertical_gradient", () -> () -> VerticalGradientProcessor.CODEC);
}
