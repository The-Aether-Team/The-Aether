package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherStructureProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Aether.MODID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<NoReplaceProcessor>> NO_REPLACE = STRUCTURE_PROCESSOR_TYPES.register("no_replace", () -> () -> NoReplaceProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<VerticalGradientProcessor>> VERTICAL_GRADIENT = STRUCTURE_PROCESSOR_TYPES.register("vertical_gradient", () -> () -> VerticalGradientProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<DoubleDropsProcessor>> DOUBLE_DROPS = STRUCTURE_PROCESSOR_TYPES.register("double_drops", () -> () -> DoubleDropsProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<BossRoomProcessor>> BOSS_ROOM = STRUCTURE_PROCESSOR_TYPES.register("boss_room", () -> () -> BossRoomProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SurfaceRuleProcessor>> SURFACE_RULE = STRUCTURE_PROCESSOR_TYPES.register("surface_rule", () -> () -> SurfaceRuleProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<HolystoneReplaceProcessor>> HOLYSTONE_REPLACE = STRUCTURE_PROCESSOR_TYPES.register("holystone_replace", () -> () -> HolystoneReplaceProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<GlowstonePortalAgeProcessor>> GLOWSTONE_PORTAL_AGE = STRUCTURE_PROCESSOR_TYPES.register("glowstone_portal_age", () -> () -> GlowstonePortalAgeProcessor.CODEC);
}
