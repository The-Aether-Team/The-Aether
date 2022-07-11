package com.gildedgames.aether.client.renderer.level;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.data.resources.AetherDimensions;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherRenderEffects {
    public final static DimensionSpecialEffects AETHER_EFFECTS = new AetherSkyRenderEffects();

    @SubscribeEvent
    public static void registerRenderEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(AetherDimensions.AETHER_DIMENSION_TYPE.location(), AETHER_EFFECTS);
    }
}
