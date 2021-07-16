package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.command.impl.TimeCommand;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber
public class WorldTickListener {

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world.dimension() == AetherDimensions.AETHER_WORLD) {
            if (event.side == LogicalSide.SERVER) {
                ((ServerWorld)event.world).setDayTime(6000);
            }
        }
    }
}
