package com.aetherteam.aether.inventory.menu;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class AetherMenuTypes {
	public static final LazyRegistrar<MenuType<?>> MENU_TYPES = LazyRegistrar.create(Registries.MENU, Aether.MODID);

	public static final RegistryObject<MenuType<AccessoriesMenu>> ACCESSORIES = register("accessories", AccessoriesMenu::new);
	public static final RegistryObject<MenuType<LoreBookMenu>> BOOK_OF_LORE = register("book_of_lore",LoreBookMenu::new);
	public static final RegistryObject<MenuType<AltarMenu>> ALTAR = register("altar", AltarMenu::new);
	public static final RegistryObject<MenuType<FreezerMenu>> FREEZER = register("freezer", FreezerMenu::new);
	public static final RegistryObject<MenuType<IncubatorMenu>> INCUBATOR = register("incubator", IncubatorMenu::new);

	private static<T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, MenuType.MenuSupplier<T> menu) {
		return MENU_TYPES.register(name, () -> new MenuType<>(menu, FeatureFlags.VANILLA_SET));
	}
}
