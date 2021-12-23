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
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.entity.player.Player;
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
        modEventBus.addListener(AetherRendering::registerLayerDefinitions);
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
            for (EntityRenderer<? extends Player> render : Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values()) {
                if(render instanceof PlayerRenderer r) {
//                    r.addLayer(new RepulsionShieldLayer<>(r, new HumanoidModel<>(1.1F)));
                    r.addLayer(new GoldenDartLayer<>(r));
                    r.addLayer(new PoisonDartLayer<>(r));
                    r.addLayer(new EnchantedDartLayer<>(r));
                }
            }
        });
    }
}
