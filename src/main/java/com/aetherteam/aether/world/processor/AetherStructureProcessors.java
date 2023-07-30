package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherStructureProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Aether.MODID);

    public static final RegistryObject<StructureProcessorType<NoReplaceProcessor>> NO_REPLACE = STRUCTURE_PROCESSOR_TYPES.register("no_replace", () -> () -> NoReplaceProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<VerticalGradientProcessor>> VERTICAL_GRADIENT = STRUCTURE_PROCESSOR_TYPES.register("vertical_gradient", () -> () -> VerticalGradientProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<DoubleDropsProcessor>> DOUBLE_DROPS = STRUCTURE_PROCESSOR_TYPES.register("double_drops", () -> () -> DoubleDropsProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<BossRoomProcessor>> BOSS_ROOM = STRUCTURE_PROCESSOR_TYPES.register("boss_room", () -> () -> BossRoomProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<SurfaceRuleProcessor>> SURFACE_RULE = STRUCTURE_PROCESSOR_TYPES.register("surface_rule", () -> () -> SurfaceRuleProcessor.CODEC);
}
