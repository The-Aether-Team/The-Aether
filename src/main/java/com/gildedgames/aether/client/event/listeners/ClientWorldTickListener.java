package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.world.ClientWorld.ClientWorldInfo;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientWorldTickListener {
    @SubscribeEvent
    public static void onWorldTick(TickEvent.RenderTickEvent event) {
        ClientWorld world = Minecraft.getInstance().level;
        if (world != null) {
            if (world.dimension() == AetherDimensions.AETHER_WORLD) {
                if (event.side == LogicalSide.CLIENT) {
                    world.setDayTime(6000);
                }
            }
        }
    }
}
