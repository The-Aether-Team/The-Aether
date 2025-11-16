package com.aetherteam.aetherfabric.client.events;

import com.aetherteam.aetherfabric.events.LivingEntityEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;

public class ClientPlayerEvents {

    public static final Event<OnRespawn> ON_RESPAWN = EventFactory.createArrayBacked(OnRespawn.class, invokers -> (oldPlayer, newPlayer) -> {
        for (var invoker : invokers) invoker.respawning(oldPlayer, newPlayer);
    });

    public interface OnRespawn {
        void respawning(LocalPlayer oldPlayer, LocalPlayer newPlayer);
    }
}
