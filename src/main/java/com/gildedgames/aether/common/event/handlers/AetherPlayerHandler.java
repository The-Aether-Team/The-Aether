package com.gildedgames.aether.common.event.handlers;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherPlayerHandler
{
    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            IAetherPlayer.get((PlayerEntity) event.getEntityLiving()).ifPresent(IAetherPlayer::onUpdate);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        IAetherPlayer original = IAetherPlayer.get(event.getOriginal()).orElseThrow(
                () -> new IllegalStateException("Player " + event.getOriginal().getName().getContents() + " has no AetherPlayer capability!"));;
        IAetherPlayer newPlayer = IAetherPlayer.get(event.getPlayer()).orElseThrow(
                () -> new IllegalStateException("Player " + event.getPlayer().getName().getContents() + " has no AetherPlayer capability!"));;

        newPlayer.copyFrom(original);
    }
}
