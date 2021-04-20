package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.registry.AetherAdvancements;
import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityListener
{
    @SubscribeEvent
    public static void onMountEntity(EntityMountEvent event) {
        Entity rider = event.getEntityMounting();
        Entity mount = event.getEntityBeingMounted();
        if (event.getEntityBeingMounted() != null && rider instanceof ServerPlayerEntity) {
            AetherAdvancements.MOUNT_ENTITY.trigger((ServerPlayerEntity) rider, mount);
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        fallFromAether(entity);
    }

    private static void fallFromAether(Entity entity) {
        World serverLevel = entity.level;
        if(serverLevel != null && serverLevel.dimension() == AetherDimensions.AETHER_WORLD && entity.getY() <= 0) {
            MinecraftServer minecraftserver = serverLevel.getServer();
            if (minecraftserver != null) {
                ServerWorld destination = minecraftserver.getLevel(World.OVERWORLD);
                if (destination != null && !entity.isPassenger()) {
                    if(entity instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity)entity).teleportTo(destination, entity.getX(), 255D, entity.getZ(), 0.0F, 0.0F);
                    }
                    else {
                        entity.setPos(entity.getX(), 255D, entity.getZ());
                        destination.addFromAnotherDimension(entity);
                    }
                }
            }
        }
    }
}
