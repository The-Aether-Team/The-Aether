package com.gildedgames.aether.entity.ai.brain.memory;

import com.gildedgames.aether.Aether;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class AetherMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, Aether.MODID);

    public static final RegistryObject<MemoryModuleType<Object2DoubleMap<LivingEntity>>> AGGRO_TRACKER = MEMORY_TYPES.register("aggro_tracker", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Integer>> MOVE_DELAY = MEMORY_TYPES.register("move_delay", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Direction>> MOVE_DIRECTION = MEMORY_TYPES.register("move_direction", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Vec3>> TARGET_POSITION = MEMORY_TYPES.register("target_position", () -> new MemoryModuleType<>(Optional.empty()));
}
