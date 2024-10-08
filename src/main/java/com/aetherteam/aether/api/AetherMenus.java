package com.aetherteam.aether.api;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen;
import com.aetherteam.aether.client.gui.screen.menu.VanillaLeftTitleScreen;
import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.api.Menus;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class AetherMenus {
    public static final DeferredRegister<Menu> MENUS = DeferredRegister.create(Cumulus.MENU_REGISTRY_KEY, Aether.MODID);

    // Icons
    private static final ResourceLocation THE_AETHER_ICON = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu_api/menu_icon_aether.png");

    // Names
    private static final Component MINECRAFT_LEFT_NAME = Component.translatable("aether.menu_title.minecraft_left");
    private static final Component THE_AETHER_NAME = Component.translatable("aether.menu_title.the_aether");
    private static final Component THE_AETHER_LEFT_NAME = Component.translatable("aether.menu_title.the_aether_left");

    // Backgrounds
    private static final ResourceLocation THE_AETHER_REGULAR_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/title/options_background.png");
    private static final ResourceLocation THE_AETHER_HEADER_SEPARATOR = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/title/header_separator.png");
    private static final ResourceLocation THE_AETHER_FOOTER_SEPARATOR = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/title/footer_separator.png");
    private static final WidgetSprites THE_AETHER_TAB_BUTTON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(Aether.MODID, "title/tab_selected"),
            ResourceLocation.fromNamespaceAndPath(Aether.MODID, "title/tab"),
            ResourceLocation.fromNamespaceAndPath(Aether.MODID, "title/tab_selected_highlighted"),
            ResourceLocation.fromNamespaceAndPath(Aether.MODID, "title/tab_highlighted"));
    private static final Menu.Background THE_AETHER_BACKGROUND = new Menu.Background()
            .regularBackground(THE_AETHER_REGULAR_BACKGROUND)
            .headerSeparator(THE_AETHER_HEADER_SEPARATOR)
            .footerSeparator(THE_AETHER_FOOTER_SEPARATOR)
            .tabButton(THE_AETHER_TAB_BUTTON);

    // Behavior
    private static final BooleanSupplier MINECRAFT_LEFT_CONDITION = () -> CumulusConfig.CLIENT.active_menu.get().equals("aether:minecraft_left") || (CumulusConfig.CLIENT.active_menu.get().equals("cumulus_menus:minecraft") && AetherConfig.CLIENT.menu_type_toggles_alignment.get() && WorldDisplayHelper.isActive());
    private static final BooleanSupplier THE_AETHER_CONDITION = () -> CumulusConfig.CLIENT.active_menu.get().equals("aether:the_aether");
    private static final BooleanSupplier THE_AETHER_LEFT_CONDITION = () -> CumulusConfig.CLIENT.active_menu.get().equals("aether:the_aether_left") || (CumulusConfig.CLIENT.active_menu.get().equals("aether:the_aether") && AetherConfig.CLIENT.menu_type_toggles_alignment.get() && WorldDisplayHelper.isActive());

    // Menus
    public static final Supplier<Menu> MINECRAFT_LEFT = MENUS.register("minecraft_left", () -> new Menu(Menus.MINECRAFT_ICON, MINECRAFT_LEFT_NAME, new VanillaLeftTitleScreen(), MINECRAFT_LEFT_CONDITION));
    public static final Supplier<Menu> THE_AETHER = MENUS.register("the_aether", () -> new Menu(THE_AETHER_ICON, THE_AETHER_NAME, new AetherTitleScreen(), THE_AETHER_CONDITION, new Menu.Properties().music(AetherTitleScreen.MENU).background(THE_AETHER_BACKGROUND)));
    public static final Supplier<Menu> THE_AETHER_LEFT = MENUS.register("the_aether_left", () -> new Menu(THE_AETHER_ICON, THE_AETHER_LEFT_NAME, new AetherTitleScreen(true), THE_AETHER_LEFT_CONDITION, new Menu.Properties().music(AetherTitleScreen.MENU).background(THE_AETHER_BACKGROUND)));
}
