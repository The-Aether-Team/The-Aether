package com.gildedgames.aether.client.event.listeners.capability;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EternalDayClientListener
{
    public static boolean isEternalDay;
    public static boolean shouldCheckTime;
    public static long aetherTime;
    public static long serverWorldTime;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.RenderTickEvent event) {
        if (event.side == LogicalSide.CLIENT) {
            ClientWorld world = Minecraft.getInstance().level;
            if (world != null) {
                if (world.dimension() == AetherDimensions.AETHER_WORLD) {
                    if (!AetherConfig.COMMON.disable_eternal_day.get()) {
                        if (shouldCheckTime) {
                            if (!isEternalDay) {
                                long dayTime = serverWorldTime % 72000;
                                if (dayTime != aetherTime) {
                                    world.setDayTime(aetherTime);
                                }
                            } else {
                                world.setDayTime(18000L);
                            }
                        }
                    }
                }
            }
        }
    }
}
