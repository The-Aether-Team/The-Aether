package com.gildedgames.aether.event.listeners.capability;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class CapeEntityListener {
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        CapabilityHooks.CapeEntityHooks.update(livingEntity);
    }
}
