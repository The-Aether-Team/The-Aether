package com.aether.registry;

import com.aether.Aether;

import com.aether.inventory.container.EnchanterContainer;
import com.aether.inventory.container.FreezerContainer;
import com.aether.inventory.container.IncubatorContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherContainerTypes
{
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Aether.MODID);

	public static final RegistryObject<ContainerType<EnchanterContainer>> ENCHANTER = CONTAINERS.register("enchanter", () -> new ContainerType<>(EnchanterContainer::new));
	public static final RegistryObject<ContainerType<FreezerContainer>> FREEZER = CONTAINERS.register("freezer", () -> new ContainerType<>(FreezerContainer::new));
	public static final RegistryObject<ContainerType<IncubatorContainer>> INCUBATOR = CONTAINERS.register("incubator", () -> new ContainerType<>(IncubatorContainer::new));
}
