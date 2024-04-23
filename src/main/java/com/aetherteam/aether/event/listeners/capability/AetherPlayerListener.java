package com.aetherteam.aether.event.listeners.capability;

import com.aetherteam.aether.event.hooks.CapabilityHooks;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Listener for events to handle functions in {@link com.aetherteam.aether.capability.player.AetherPlayerCapability}.
 */
public class AetherPlayerListener {
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
    public static boolean onPlayerJoinLevel(Entity entity, Level world, boolean loadedFromDisk) {
        CapabilityHooks.AetherPlayerHooks.joinLevel(entity);
        return true;
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#update(LivingEntity)
     */
    public static void onPlayerUpdate(LivingEntityEvents.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.update(livingEntity);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#clone(Player, Player, boolean)
     */
    public static void onPlayerClone(ServerPlayer originalPlayer, ServerPlayer newPlayer, boolean alive) {
        CapabilityHooks.AetherPlayerHooks.clone(originalPlayer, newPlayer, !alive);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#changeDimension(Player)
     */
    public static void onPlayerChangeDimension(ServerPlayer player, ServerLevel origin, ServerLevel destination) {
        CapabilityHooks.AetherPlayerHooks.changeDimension(player);
    }

    public static void init() {
//        PlayerEvents.LOGGED_IN.register(AetherPlayerListener::onPlayerLogin);
//        PlayerEvents.LOGGED_OUT.register(AetherPlayerListener::onPlayerLogout);
//        EntityEvents.ON_JOIN_WORLD.register(AetherPlayerListener::onPlayerJoinLevel);
//        LivingEntityEvents.LivingTickEvent.TICK.register(AetherPlayerListener::onPlayerUpdate);
//        ServerPlayerEvents.COPY_FROM.register(AetherPlayerListener::onPlayerClone);
//        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(AetherPlayerListener::onPlayerChangeDimension);
    }
}
