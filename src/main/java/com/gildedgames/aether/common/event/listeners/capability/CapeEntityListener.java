package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.core.capability.cape.CapeEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapeEntityListener
{
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        CapeEntity.get(event.getEntityLiving()).ifPresent(CapeEntity::onUpdate);
    }
}
