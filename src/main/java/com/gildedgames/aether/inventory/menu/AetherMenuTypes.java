package com.gildedgames.aether.inventory.menu;

import com.gildedgames.aether.Aether;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Aether.MODID);

	public static final RegistryObject<MenuType<AccessoriesMenu>> ACCESSORIES = MENU_TYPES.register("accessories", () -> new MenuType<>(AccessoriesMenu::new));
	public static final RegistryObject<MenuType<LoreBookMenu>> BOOK_OF_LORE = MENU_TYPES.register("book_of_lore", () -> new MenuType<>(LoreBookMenu::new));
	public static final RegistryObject<MenuType<AltarMenu>> ALTAR = MENU_TYPES.register("altar", () -> new MenuType<>(AltarMenu::new));
	public static final RegistryObject<MenuType<FreezerMenu>> FREEZER = MENU_TYPES.register("freezer", () -> new MenuType<>(FreezerMenu::new));
	public static final RegistryObject<MenuType<IncubatorMenu>> INCUBATOR = MENU_TYPES.register("incubator", () -> new MenuType<>(IncubatorMenu::new));
}
