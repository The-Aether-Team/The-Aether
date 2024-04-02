package com.aetherteam.aether.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Listener for Forge events to handle functions in {@link AetherPlayerAttachment}.
 */
public class AetherPlayerListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(AetherPlayerListener::onPlayerLogin);
        bus.addListener(AetherPlayerListener::onPlayerLogout);
        bus.addListener(AetherPlayerListener::onPlayerJoinLevel);
        bus.addListener(AetherPlayerListener::onPlayerUpdate);
        bus.addListener(EventPriority.LOWEST, AetherPlayerListener::onPlayerClone);
        bus.addListener(AetherPlayerListener::onPlayerChangeDimension);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#login(Player)
     */
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.login(player);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#login(Player)
     */
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.logout(player);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#joinLevel(Entity)
     */
    public static void onPlayerJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.joinLevel(entity);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#update(LivingEntity)
     */
    public static void onPlayerUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.update(livingEntity);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#clone(Player, boolean)
     */
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CapabilityHooks.AetherPlayerHooks.clone(event.getEntity(), event.isWasDeath());
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#changeDimension(Player)
     */
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.changeDimension(player);
    }
}
