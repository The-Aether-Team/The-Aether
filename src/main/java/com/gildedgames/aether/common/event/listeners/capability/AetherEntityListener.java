package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.core.capability.interfaces.IAetherEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherEntityListener
{
    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        IAetherEntity.get(event.getEntityLiving()).ifPresent(IAetherEntity::sync);
        IAetherEntity.get(event.getEntityLiving()).ifPresent(IAetherEntity::onUpdate);
    }
}
