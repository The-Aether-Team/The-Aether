package com.gildedgames.aether.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.screen.inventory.*;
import com.gildedgames.aether.client.registry.AetherAtlases;
import com.gildedgames.aether.client.registry.AetherKeys;
import com.gildedgames.aether.client.registry.AetherOverlays;
import com.gildedgames.aether.client.registry.AetherRenderers;
import com.gildedgames.aether.client.world.AetherSkyRenderInfo;
import com.gildedgames.aether.common.item.miscellaneous.MoaEggItem;
import com.gildedgames.aether.common.registry.AetherContainerTypes;
import com.gildedgames.aether.common.registry.AetherDimensions;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherClient
{
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            AetherAtlases.registerWoodTypeAtlases();
            AetherKeys.registerKeys();
            AetherOverlays.registerOverlays();
            AetherRenderers.registerBlockRenderLayers();
            AetherRenderers.registerCuriosRenderers();
            registerGuiFactories();
            registerItemModelProperties();
            registerColors();
            DimensionSpecialEffects.EFFECTS.put(AetherDimensions.AETHER_DIMENSION.location(), new AetherSkyRenderInfo());
        });
    }

    public static void registerGuiFactories() {
        MenuScreens.register(AetherContainerTypes.ACCESSORIES.get(), AccessoriesScreen::new);
        MenuScreens.register(AetherContainerTypes.BOOK_OF_LORE.get(), LoreBookScreen::new);
        MenuScreens.register(AetherContainerTypes.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(AetherContainerTypes.FREEZER.get(), FreezerScreen::new);
        MenuScreens.register(AetherContainerTypes.INCUBATOR.get(), IncubatorScreen::new);
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

        ItemProperties.register(AetherItems.HAMMER_OF_NOTCH.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living, i)
                -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    public static void registerColors() {
        ItemColors colors = Minecraft.getInstance().getItemColors();

        colors.register((color, itemProvider) -> itemProvider > 0 ? -1 : ((DyeableLeatherItem) color.getItem()).getColor(color), AetherItems.LEATHER_GLOVES.get());

        for (MoaEggItem moaEggItem : MoaEggItem.moaEggs()) {
            colors.register((color, itemProvider) -> moaEggItem.getColor(itemProvider), moaEggItem);
        }
    }

    /**
     * Used to work around a classloading crash on the server.
     */
    public static void setToSunAltarScreen(Component name) {
        Minecraft.getInstance().setScreen(new SunAltarScreen(name));
    }
}
