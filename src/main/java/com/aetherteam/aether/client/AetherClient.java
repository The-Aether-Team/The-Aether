package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.api.AetherMenus;
import com.aetherteam.aether.client.event.listeners.*;
import com.aetherteam.aether.client.event.listeners.abilities.AccessoryAbilityClientListener;
import com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener;
import com.aetherteam.aether.client.gui.screen.inventory.SunAltarScreen;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.client.renderer.AetherOverlays;
import com.aetherteam.aether.client.renderer.AetherRenderers;
import com.aetherteam.aether.client.renderer.level.AetherRenderEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.inventory.menu.LoreBookMenu;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.nitrogen.event.listeners.TooltipListeners;
import com.google.common.reflect.Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.RegisterEntitySpectatorShadersEvent;
import net.neoforged.neoforge.common.NeoForge;

public class AetherClient {
    private static boolean refreshPacks = false;

    public static void clientInit(IEventBus bus) {
        bus.addListener(AetherClient::clientSetup);
        bus.addListener(AetherClient::registerSpectatorShaders);
        bus.addListener(AetherClient::loadComplete);

        AetherMenus.MENUS.register(bus);

        AetherClient.eventSetup(bus);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        disableCumulusButton();
        Reflection.initialize(CustomizationsOptions.class);
        AetherRenderers.registerCuriosRenderers();
        event.enqueueWork(() -> {
            AetherAtlases.registerTreasureChestAtlases();
            AetherAtlases.registerWoodTypeAtlases();
            registerItemModelProperties();
            registerTooltipOverrides();
        });
        registerLoreOverrides();
        autoApplyPacks();
    }

    /**
     * Disables the Cumulus menu switcher button, since Aether has its own for theme toggling.
     */
    public static void disableCumulusButton() {
        if (AetherConfig.CLIENT.should_disable_cumulus_button.get()) {
            CumulusConfig.CLIENT.enable_menu_list_button.set(false);
            CumulusConfig.CLIENT.enable_menu_list_button.save();
            AetherConfig.CLIENT.should_disable_cumulus_button.set(false);
            AetherConfig.CLIENT.should_disable_cumulus_button.save();
        }
    }

    public static void registerItemModelProperties() {
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), ResourceLocation.withDefaultNamespace("pulling"),
                (stack, world, living, i) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), ResourceLocation.withDefaultNamespace("pull"),
                (stack, world, living, i) -> living != null ? living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration(living) - living.getUseItemRemainingTicks()) / 20.0F : 0.0F);

        ItemProperties.register(AetherItems.CANDY_CANE_SWORD.get(), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "named"), // Easter Egg texture.
                (stack, world, living, i) -> stack.getHoverName().getString().equalsIgnoreCase("green candy cane sword") ? 1.0F : 0.0F);

        ItemProperties.register(AetherItems.HAMMER_OF_KINGBDOGZ.get(), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "named"), // Easter Egg texture.
                (stack, world, living, i) -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    public static void registerTooltipOverrides() {
        TooltipListeners.PREDICATES.put(AetherItems.BLUE_GUMMY_SWET, (player, stack, components, context, component) -> {
            if (AetherConfig.SERVER.healing_gummy_swets.get() && component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey() + ".health");
            } else {
                return component;
            }
        });
        TooltipListeners.PREDICATES.put(AetherItems.GOLDEN_GUMMY_SWET, (player, stack, components, context, component) -> {
            if (AetherConfig.SERVER.healing_gummy_swets.get() && component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey() + ".health");
            } else {
                return component;
            }
        });
        TooltipListeners.PREDICATES.put(AetherItems.LIFE_SHARD, (player, stack, components, context, component) -> {
            if (component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey(), AetherConfig.SERVER.maximum_life_shards.get());
            } else {
                return component;
            }
        });
    }

    /**
     * Applies a unique lore entry in the Book of Lore for the Hammer of Jeb Easter Egg item texture.
     */
    public static void registerLoreOverrides() {
        LoreBookMenu.addLoreEntryOverride(stack -> stack.is(AetherItems.HAMMER_OF_KINGBDOGZ.get()) && stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb"), "lore.item.aether.hammer_of_jeb");
//        LoreBookMenu.addLoreEntryOverride(stack -> ItemStack.isSameItemSameComponents(stack, AetherItems.createSwetBannerItemStack()), "lore.item.aether.swet_banner"); //todo
    }

    /**
     * Auto applies resource packs on load.
     */
    public static void autoApplyPacks() {
        if (ModList.get().isLoaded("tipsmod")) {
            if (AetherConfig.CLIENT.enable_trivia.get()) {
                Minecraft.getInstance().getResourcePackRepository().addPack("builtin/aether_tips");
            } else {
                Minecraft.getInstance().getResourcePackRepository().removePack("builtin/aether_tips");
            }
            refreshPacks = true;
        }
    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        AccessoryAbilityClientListener.listen(bus);
        AetherPlayerClientListener.listen(bus);
        AudioListener.listen(bus);
        DimensionClientListener.listen(bus);
        GuiListener.listen(bus);
        HandRenderListener.listen(bus);
        LevelClientListener.listen(bus);
        MenuListener.listen(bus);
        WorldPreviewListener.listen(bus);

        neoBus.addListener(AetherMenuTypes::registerMenuScreens);
        neoBus.addListener(AetherColorResolvers::registerBlockColor);
        neoBus.addListener(AetherColorResolvers::registerItemColor);
        neoBus.addListener(AetherKeys::registerKeyMappings);
        neoBus.addListener(AetherRecipeCategories::registerRecipeCategories);
        neoBus.addListener(AetherParticleTypes::registerParticleFactories);
        neoBus.addListener(AetherOverlays::registerOverlays);
        neoBus.addListener(AetherRenderers::registerEntityRenderers);
        neoBus.addListener(AetherRenderers::registerLayerDefinitions);
        neoBus.addListener(AetherRenderers::addEntityLayers);
        neoBus.addListener(AetherRenderers::bakeModels);
        neoBus.addListener(AetherRenderEffects::registerRenderEffects);
    }

    /**
     * Registers a unique shader for spectating the Sun Spirit, which tints the screen red.
     */
    public static void registerSpectatorShaders(RegisterEntitySpectatorShadersEvent event) {
        event.register(AetherEntityTypes.SUN_SPIRIT.get(), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "shaders/post/sun_spirit.json"));
    }

    /**
     * Refreshes resource packs at the end of loading, so that auto-applied packs in {@link AetherClient#autoApplyPacks()} get processed.
     */
    public static void loadComplete(FMLLoadCompleteEvent event) {
        if (refreshPacks) {
            Minecraft.getInstance().reloadResourcePacks();
            refreshPacks = false;
        }
    }

    /**
     * Used to work around a classloading crash on the server.
     */
    public static void setToSunAltarScreen(Component name, int timeScale) {
        Minecraft.getInstance().setScreen(new SunAltarScreen(name, timeScale));
    }
}
