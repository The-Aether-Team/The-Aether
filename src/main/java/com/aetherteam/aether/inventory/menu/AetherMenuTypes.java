package com.aetherteam.aether.inventory.menu;

import com.aetherteam.aether.Aether;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Aether.MODID);

	public static final RegistryObject<MenuType<AccessoriesMenu>> ACCESSORIES = register("accessories", AccessoriesMenu::new);
	public static final RegistryObject<MenuType<LoreBookMenu>> BOOK_OF_LORE = register("book_of_lore",LoreBookMenu::new);
	public static final RegistryObject<MenuType<AltarMenu>> ALTAR = register("altar", AltarMenu::new);
	public static final RegistryObject<MenuType<FreezerMenu>> FREEZER = register("freezer", FreezerMenu::new);
	public static final RegistryObject<MenuType<IncubatorMenu>> INCUBATOR = register("incubator", IncubatorMenu::new);

	private static<T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, MenuType.MenuSupplier<T> menu) {
		return MENU_TYPES.register(name, () -> new MenuType<>(menu));
	}
}
