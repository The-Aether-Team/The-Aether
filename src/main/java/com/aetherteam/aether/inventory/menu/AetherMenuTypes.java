package com.aetherteam.aether.inventory.menu;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.inventory.*;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class AetherMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, Aether.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<AetherAccessoriesMenu>> ACCESSORIES = register("accessories", AetherAccessoriesMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<LoreBookMenu>> BOOK_OF_LORE = register("book_of_lore", LoreBookMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<AltarMenu>> ALTAR = register("altar", AltarMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<FreezerMenu>> FREEZER = register("freezer", FreezerMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<IncubatorMenu>> INCUBATOR = register("incubator", IncubatorMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String name, MenuType.MenuSupplier<T> menu) {
        return MENU_TYPES.register(name, () -> new MenuType<>(menu, FeatureFlags.VANILLA_SET));
    }

    @Environment(EnvType.CLIENT)
    public static void registerMenuScreens() {
        MenuScreens.register(AetherMenuTypes.ACCESSORIES.get(), AetherAccessoriesScreen::new);
        MenuScreens.register(AetherMenuTypes.BOOK_OF_LORE.get(), LoreBookScreen::new);
        MenuScreens.register(AetherMenuTypes.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(AetherMenuTypes.FREEZER.get(), FreezerScreen::new);
        MenuScreens.register(AetherMenuTypes.INCUBATOR.get(), IncubatorScreen::new);
    }
}
