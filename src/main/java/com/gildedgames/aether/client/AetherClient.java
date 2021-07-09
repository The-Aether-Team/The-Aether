package com.gildedgames.aether.client;

import com.gildedgames.aether.client.renderer.accessory.layer.RepulsionShieldLayer;
import com.gildedgames.aether.client.renderer.dimension.AetherSkyRenderInfo;
import com.gildedgames.aether.client.renderer.player.layer.EnchantedDartLayer;
import com.gildedgames.aether.client.renderer.player.layer.GoldenDartLayer;
import com.gildedgames.aether.client.renderer.player.layer.PoisonDartLayer;
import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AetherClient
{
    public static void clientInitialization() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(AetherClient::clientSetup);
        modEventBus.addListener(AetherClient::clientComplete);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        AetherRendering.registerEntityRenderers();
        AetherRendering.registerTileEntityRenderers();

        event.enqueueWork(() -> {
            AetherRendering.registerColors();
            AetherRendering.registerBlockRenderLayers();
            AetherRendering.registerItemModelProperties();
            AetherRendering.registerGuiFactories();
            AetherRendering.registerWoodTypeAtlases();

            DimensionRenderInfo.EFFECTS.put(AetherDimensions.AETHER_DIMENSION.location(), new AetherSkyRenderInfo());
        });
    }

    public static void clientComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            for (PlayerRenderer render : Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values()) {
                render.addLayer(new RepulsionShieldLayer<>(render, new BipedModel(1.1F)));
                render.addLayer(new GoldenDartLayer<>(render));
                render.addLayer(new PoisonDartLayer<>(render));
                render.addLayer(new EnchantedDartLayer<>(render));
            }
        });
    }
}
