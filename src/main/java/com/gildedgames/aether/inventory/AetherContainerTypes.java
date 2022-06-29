package com.gildedgames.aether.inventory;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.inventory.container.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherContainerTypes
{
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Aether.MODID);

	public static final RegistryObject<MenuType<AccessoriesMenu>> ACCESSORIES = CONTAINERS.register("accessories", () -> new MenuType<>(AccessoriesMenu::new));
	public static final RegistryObject<MenuType<LoreBookMenu>> BOOK_OF_LORE = CONTAINERS.register("book_of_lore", () -> new MenuType<>(LoreBookMenu::new));
	public static final RegistryObject<MenuType<AltarMenu>> ALTAR = CONTAINERS.register("altar", () -> new MenuType<>(AltarMenu::new));
	public static final RegistryObject<MenuType<FreezerMenu>> FREEZER = CONTAINERS.register("freezer", () -> new MenuType<>(FreezerMenu::new));
	public static final RegistryObject<MenuType<IncubatorMenu>> INCUBATOR = CONTAINERS.register("incubator", () -> new MenuType<>(IncubatorMenu::new));
}
