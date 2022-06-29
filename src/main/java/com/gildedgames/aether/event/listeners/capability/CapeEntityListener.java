package com.gildedgames.aether.event.listeners.capability;

import com.gildedgames.aether.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapeEntityListener {
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        CapabilityHooks.CapeEntityHooks.update(livingEntity);
    }
}
