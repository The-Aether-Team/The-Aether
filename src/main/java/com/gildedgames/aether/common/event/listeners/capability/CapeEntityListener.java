package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapeEntityListener
{
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        ICapeEntity.get(event.getEntityLiving()).ifPresent(ICapeEntity::onUpdate);
    }
}
