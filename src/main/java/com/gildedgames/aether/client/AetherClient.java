package com.gildedgames.aether.client;

import com.gildedgames.aether.client.registry.AetherAtlases;
import com.gildedgames.aether.client.registry.AetherKeys;
import com.gildedgames.aether.client.renderer.accessory.layer.RepulsionShieldLayer;
import com.gildedgames.aether.client.renderer.player.layer.EnchantedDartLayer;
import com.gildedgames.aether.client.renderer.player.layer.GoldenDartLayer;
import com.gildedgames.aether.client.renderer.player.layer.PoisonDartLayer;
import com.gildedgames.aether.client.world.AetherSkyRenderInfo;
import com.gildedgames.aether.common.registry.AetherDimensions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class AetherClient
{
    public static void clientInitialization() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(AetherClient::clientSetup);
        modEventBus.addListener(AetherClient::clientComplete);
        modEventBus.addListener(AetherRendering::registerEntityRenderers);
    }

    public static void clientSetup(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {
            AetherKeys.registerKeys();
            AetherRendering.registerColors();
            AetherRendering.registerBlockRenderLayers();
            AetherRendering.registerItemModelProperties();
            AetherRendering.registerGuiFactories();
            AetherAtlases.registerWoodTypeAtlases();

            DimensionSpecialEffects.EFFECTS.put(AetherDimensions.AETHER_DIMENSION.location(), new AetherSkyRenderInfo());
        });
    }

    public static void clientComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            for (PlayerRenderer render : Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values()) {
                render.addLayer(new RepulsionShieldLayer<>(render, new HumanoidModel<>(1.1F)));
                render.addLayer(new GoldenDartLayer<>(render));
                render.addLayer(new PoisonDartLayer<>(render));
                render.addLayer(new EnchantedDartLayer<>(render));
            }
        });
    }
}
