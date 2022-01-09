package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.inventory.container.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherContainerTypes
{
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Aether.MODID);

	public static final RegistryObject<MenuType<AccessoriesContainer>> ACCESSORIES = CONTAINERS.register("accessories", () -> new MenuType<>(AccessoriesContainer::new));
	public static final RegistryObject<MenuType<LoreBookContainer>> BOOK_OF_LORE = CONTAINERS.register("book_of_lore", () -> new MenuType<>(LoreBookContainer::new));
	public static final RegistryObject<MenuType<AltarContainer>> ALTAR = CONTAINERS.register("altar", () -> new MenuType<>(AltarContainer::new));
	public static final RegistryObject<MenuType<FreezerContainer>> FREEZER = CONTAINERS.register("freezer", () -> new MenuType<>(FreezerContainer::new));
	public static final RegistryObject<MenuType<IncubatorContainer>> INCUBATOR = CONTAINERS.register("incubator", () -> new MenuType<>(IncubatorContainer::new));
}
