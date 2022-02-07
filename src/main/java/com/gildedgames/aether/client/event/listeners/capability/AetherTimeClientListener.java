/*
package com.gildedgames.aether.client.event.listeners.capability;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.capability.interfaces.IAetherTime;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AetherTimeClientListener {
    public static Level world;
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Level level = Minecraft.getInstance().level;
        if(level != null && level.dimension() == AetherDimensions.AETHER_WORLD && event.phase == TickEvent.Phase.END) {
            world = level;
            IAetherTime.get(level).ifPresent(aetherTime -> aetherTime.tick(level));
        }
    }
}
*/
