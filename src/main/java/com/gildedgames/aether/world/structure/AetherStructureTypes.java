package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, Aether.MODID);

    public static RegistryObject<StructureType<LargeAercloudStructure>> LARGE_AERCLOUD = STRUCTURE_TYPES.register("large_aercloud", () -> () -> LargeAercloudStructure.CODEC);
}
