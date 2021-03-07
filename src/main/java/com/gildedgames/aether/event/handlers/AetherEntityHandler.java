package com.gildedgames.aether.event.handlers;

import com.gildedgames.aether.registry.AetherAdvancement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherEntityHandler
{
    @SubscribeEvent
    public static void onMountEntity(EntityMountEvent event) {
        Entity rider = event.getEntityMounting();
        Entity mount = event.getEntityBeingMounted();
        if (event.getEntityBeingMounted() != null && rider instanceof ServerPlayerEntity) {
            AetherAdvancement.MOUNT_ENTITY.trigger((ServerPlayerEntity) rider, mount);
        }
    }
}
