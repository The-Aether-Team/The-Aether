package com.aetherteam.aether.api;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen;
import com.aetherteam.aether.client.gui.screen.menu.VanillaLeftTitleScreen;
import com.aetherteam.nitrogen.Nitrogen;
import com.aetherteam.nitrogen.NitrogenConfig;
import com.aetherteam.nitrogen.api.menu.Menu;
import com.aetherteam.nitrogen.api.menu.Menus;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class AetherMenus {
    public static final DeferredRegister<Menu> MENUS = DeferredRegister.create(Nitrogen.MENU_REGISTRY_KEY, Aether.MODID);
    public static final Supplier<IForgeRegistry<Menu>> MENU_REGISTRY = MENUS.makeRegistry(RegistryBuilder::new);

    // Icons
    private static final ResourceLocation THE_AETHER_ICON = new ResourceLocation(Aether.MODID, "textures/gui/menu_api/menu_icon_aether.png");

    // Names
    private static final Component MINECRAFT_LEFT_NAME = Component.translatable("aether.menu_title.minecraft_left");
    private static final Component THE_AETHER_NAME = Component.translatable("aether.menu_title.the_aether");
    private static final Component THE_AETHER_LEFT_NAME = Component.translatable("aether.menu_title.the_aether_left");

    // Backgrounds
    private static final ResourceLocation THE_AETHER_REGULAR_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/title/options_background.png");
    private static final ResourceLocation THE_AETHER_DARK_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/title/light_sentry_background.png");
    private static final ResourceLocation THE_AETHER_HEADER_SEPARATOR = new ResourceLocation(Aether.MODID, "textures/gui/title/header_separator.png");
    private static final ResourceLocation THE_AETHER_FOOTER_SEPARATOR = new ResourceLocation(Aether.MODID, "textures/gui/title/footer_separator.png");
    private static final ResourceLocation THE_AETHER_TAB_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/title/tab_button.png");
    private static final Menu.Background THE_AETHER_BACKGROUND = new Menu.Background()
            .regularBackground(THE_AETHER_REGULAR_BACKGROUND)
            .darkBackground(THE_AETHER_DARK_BACKGROUND)
            .headerSeparator(THE_AETHER_HEADER_SEPARATOR)
            .footerSeparator(THE_AETHER_FOOTER_SEPARATOR)
            .tabButton(THE_AETHER_TAB_BUTTON);

    // Behavior
    private static final BooleanSupplier MINECRAFT_LEFT_CONDITION = () -> NitrogenConfig.CLIENT.active_menu.get().equals("aether:minecraft_left") || (NitrogenConfig.CLIENT.active_menu.get().equals("aether_nitrogen:minecraft") && AetherConfig.CLIENT.menu_type_toggles_alignment.get() && AetherConfig.CLIENT.enable_world_preview.get());
    private static final BooleanSupplier THE_AETHER_CONDITION = () -> NitrogenConfig.CLIENT.active_menu.get().equals("aether:the_aether");
    private static final BooleanSupplier THE_AETHER_LEFT_CONDITION = () -> NitrogenConfig.CLIENT.active_menu.get().equals("aether:the_aether_left") || (NitrogenConfig.CLIENT.active_menu.get().equals("aether:the_aether") && AetherConfig.CLIENT.menu_type_toggles_alignment.get() && AetherConfig.CLIENT.enable_world_preview.get());

    // Menus
    public static final RegistryObject<Menu> MINECRAFT_LEFT = MENUS.register("minecraft_left", () -> new Menu(Menus.MINECRAFT_ICON, MINECRAFT_LEFT_NAME, new VanillaLeftTitleScreen(), MINECRAFT_LEFT_CONDITION));
    public static final RegistryObject<Menu> THE_AETHER = MENUS.register("the_aether", () -> new Menu(THE_AETHER_ICON, THE_AETHER_NAME, new AetherTitleScreen(), THE_AETHER_CONDITION, new Menu.Properties().music(AetherTitleScreen.MENU).background(THE_AETHER_BACKGROUND)));
    public static final RegistryObject<Menu> THE_AETHER_LEFT = MENUS.register("the_aether_left", () -> new Menu(THE_AETHER_ICON, THE_AETHER_LEFT_NAME, new AetherTitleScreen(true), THE_AETHER_LEFT_CONDITION, new Menu.Properties().music(AetherTitleScreen.MENU).background(THE_AETHER_BACKGROUND)));

    // Methods
    public static Menu get(String id) {
        return MENU_REGISTRY.get().getValue(new ResourceLocation(id));
    }
}