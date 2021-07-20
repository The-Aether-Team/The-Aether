package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherPlayerListener
{
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        IAetherPlayer.get(player).ifPresent((aetherPlayer) -> {
            aetherPlayer.sync();
            if (AetherConfig.COMMON.start_with_portal.get()) {
                aetherPlayer.givePortalItem();
            } else {
                aetherPlayer.setCanGetPortal(false);
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerEntity player = event.getPlayer();
        IAetherPlayer.get(player).ifPresent((aetherPlayer) ->  {
            ModifiableAttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
            if (health != null && health.hasModifier(aetherPlayer.getLifeShardHealthAttributeModifier())) {
                aetherPlayer.setSavedHealth(player.getHealth());
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            IAetherPlayer.get((PlayerEntity) event.getEntityLiving()).ifPresent(IAetherPlayer::onUpdate);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        IAetherPlayer original = IAetherPlayer.get(event.getOriginal()).orElseThrow(
                () -> new IllegalStateException("Player " + event.getOriginal().getName().getContents() + " has no AetherPlayer capability!"));
        IAetherPlayer newPlayer = IAetherPlayer.get(event.getPlayer()).orElseThrow(
                () -> new IllegalStateException("Player " + event.getPlayer().getName().getContents() + " has no AetherPlayer capability!"));

        newPlayer.copyFrom(original);
        if (!event.isWasDeath()) {
            newPlayer.setSavedHealth(original.getPlayer().getHealth());
        } else {
            newPlayer.setSavedHealth(1024.0F); //Max health.
        }
    }
}
