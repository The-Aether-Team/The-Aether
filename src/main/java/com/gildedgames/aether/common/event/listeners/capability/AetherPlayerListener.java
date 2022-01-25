package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.core.capability.interfaces.AetherPlayerSerializable;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherPlayerListener
{
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        AetherPlayerSerializable.get(player).ifPresent(AetherPlayerSerializable::onLogin);
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();
        AetherPlayerSerializable.get(player).ifPresent((aetherPlayer) ->  {
            AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
            if (health != null && health.hasModifier(aetherPlayer.getLifeShardHealthAttributeModifier())) {
                aetherPlayer.setSavedHealth(player.getHealth());
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            AetherPlayerSerializable.get((Player) event.getEntityLiving()).ifPresent(AetherPlayerSerializable::onUpdate);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        AetherPlayerSerializable original = AetherPlayerSerializable.get(event.getOriginal()).orElseThrow(
                () -> new IllegalStateException("Player " + event.getOriginal().getName().getContents() + " has no AetherPlayer capability!"));
        AetherPlayerSerializable newPlayer = AetherPlayerSerializable.get(event.getPlayer()).orElseThrow(
                () -> new IllegalStateException("Player " + event.getPlayer().getName().getContents() + " has no AetherPlayer capability!"));
        newPlayer.copyFrom(original, event.isWasDeath());

        if (!event.isWasDeath()) {
            newPlayer.setSavedHealth(original.getPlayer().getHealth());
        } else {
            newPlayer.setSavedHealth(1024.0F); //Max health.
        }
        event.getOriginal().invalidateCaps();
    }
}
