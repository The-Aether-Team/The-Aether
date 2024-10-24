package com.aetherteam.aether.inventory.menu;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, Aether.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<AccessoriesMenu>> ACCESSORIES = register("accessories", AccessoriesMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<LoreBookMenu>> BOOK_OF_LORE = register("book_of_lore", LoreBookMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<AltarMenu>> ALTAR = register("altar", AltarMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<FreezerMenu>> FREEZER = register("freezer", FreezerMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<IncubatorMenu>> INCUBATOR = register("incubator", IncubatorMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String name, MenuType.MenuSupplier<T> menu) {
        return MENU_TYPES.register(name, () -> new MenuType<>(menu, FeatureFlags.VANILLA_SET));
    }
}
