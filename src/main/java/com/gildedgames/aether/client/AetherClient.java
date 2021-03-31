package com.gildedgames.aether.client;

import com.gildedgames.aether.client.renderer.dimension.AetherSkyRenderInfo;
import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AetherClient
{
    public static void clientInitialization() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(AetherClient::clientSetup);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        AetherRendering.registerEntityRenderers();
        AetherRendering.registerTileEntityRenderers();

        event.enqueueWork(() -> {
            AetherRendering.registerBlockRenderLayers();
            AetherRendering.registerItemModelProperties();
            AetherRendering.registerGuiFactories();
            AetherRendering.registerWoodTypeAtlases();

            DimensionRenderInfo.EFFECTS.put(AetherDimensions.AETHER_DIMENSION.location(), new AetherSkyRenderInfo());
        });
    }
}
