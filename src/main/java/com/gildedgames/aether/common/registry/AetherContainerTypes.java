package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.inventory.container.AltarContainer;
import com.gildedgames.aether.common.inventory.container.FreezerContainer;
import com.gildedgames.aether.common.inventory.container.IncubatorContainer;
import com.gildedgames.aether.common.inventory.container.LoreBookContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherContainerTypes
{
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Aether.MODID);

	public static final RegistryObject<ContainerType<LoreBookContainer>> BOOK_OF_LORE = CONTAINERS.register("book_of_lore", () -> new ContainerType<>(LoreBookContainer::new));
	public static final RegistryObject<ContainerType<AltarContainer>> ALTAR = CONTAINERS.register("altar", () -> new ContainerType<>(AltarContainer::new));
	public static final RegistryObject<ContainerType<FreezerContainer>> FREEZER = CONTAINERS.register("freezer", () -> new ContainerType<>(FreezerContainer::new));
	public static final RegistryObject<ContainerType<IncubatorContainer>> INCUBATOR = CONTAINERS.register("incubator", () -> new ContainerType<>(IncubatorContainer::new));
}
