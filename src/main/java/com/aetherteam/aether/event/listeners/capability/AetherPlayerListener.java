package com.aetherteam.aether.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.event.hooks.CapabilityHooks;
import com.aetherteam.nitrogen.fabric.events.EntityTickEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Listener for Forge events to handle functions in {@link AetherPlayerAttachment}.
 */
public class AetherPlayerListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> onPlayerLogin(handler.getPlayer()));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> onPlayerLogout(handler.getPlayer()));
        // onPlayerJoinLevel
        EntityTickEvents.AFTER.register(AetherPlayerListener::onPlayerUpdate);
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> onPlayerClone(newPlayer, !alive));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> onPlayerChangeDimension(player));
    }

    @Environment(EnvType.CLIENT)
    public static void listenClient() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> onPlayerJoinLevel(entity));
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#login(Player)
     */
    public static void onPlayerLogin(Player player) {
        CapabilityHooks.AetherPlayerHooks.login(player);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#login(Player)
     */
    public static void onPlayerLogout(Player player) {
        CapabilityHooks.AetherPlayerHooks.logout(player);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#joinLevel(Entity)
     */
    public static void onPlayerJoinLevel(Entity entity) {
        CapabilityHooks.AetherPlayerHooks.joinLevel(entity);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#update(LivingEntity)
     */
    public static void onPlayerUpdate(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            CapabilityHooks.AetherPlayerHooks.update(livingEntity);
        }
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#clone(Player, boolean)
     */
    public static void onPlayerClone(Player player, boolean wasDeath) {
        CapabilityHooks.AetherPlayerHooks.clone(player, wasDeath);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#changeDimension(Player)
     */
    public static void onPlayerChangeDimension(Player player) {
        CapabilityHooks.AetherPlayerHooks.changeDimension(player);
    }
}
