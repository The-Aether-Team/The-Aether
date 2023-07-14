package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.SunAltarScreen;
import com.aetherteam.aether.client.gui.screen.inventory.*;
import com.aetherteam.aether.client.renderer.AetherRenderers;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.inventory.menu.LoreBookMenu;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.google.common.reflect.Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterEntitySpectatorShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        Reflection.initialize(CustomizationsOptions.class);
        AetherRenderers.registerCuriosRenderers();
        event.enqueueWork(() -> {
            AetherAtlases.registerTreasureChestAtlases();
            AetherAtlases.registerWoodTypeAtlases();
            registerGuiFactories();
            registerItemModelProperties();
        });
        registerLoreOverrides();
    }

    public static void registerGuiFactories() {
        MenuScreens.register(AetherMenuTypes.ACCESSORIES.get(), AccessoriesScreen::new);
        MenuScreens.register(AetherMenuTypes.BOOK_OF_LORE.get(), LoreBookScreen::new);
        MenuScreens.register(AetherMenuTypes.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(AetherMenuTypes.FREEZER.get(), FreezerScreen::new);
        MenuScreens.register(AetherMenuTypes.INCUBATOR.get(), IncubatorScreen::new);
    }

    public static void registerItemModelProperties() {
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pulling"), (stack, world, living, i)
                -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pull"), (stack, world, living, i) -> {
            if (living == null) {
                return 0.0F;
            } else {
                return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(AetherItems.CANDY_CANE_SWORD.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living, i)
                -> stack.getHoverName().getString().equalsIgnoreCase("green candy cane sword") ? 1.0F : 0.0F);

        ItemProperties.register(AetherItems.HAMMER_OF_KINGBDOGZ.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living, i)
                -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    public static void registerLoreOverrides() {
        LoreBookMenu.addLoreEntryOverride(stack -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb"), "lore.item.aether.hammer_of_jeb");
    }

    @SubscribeEvent
    public static void registerSpectatorShaders(RegisterEntitySpectatorShadersEvent event) {
        event.register(AetherEntityTypes.SUN_SPIRIT.get(), new ResourceLocation(Aether.MODID, "shaders/post/sun_spirit.json"));
    }

    /**
     * Used to work around a classloading crash on the server.
     */
    public static void setToSunAltarScreen(Component name) {
        Minecraft.getInstance().setScreen(new SunAltarScreen(name));
    }
}
