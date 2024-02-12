package com.aetherteam.aether.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Listener for Forge events to handle functions in {@link com.aetherteam.aether.capability.player.AetherPlayerCapability}.
 */
@Mod.EventBusSubscriber(modid = Aether.MODID)
public class AetherPlayerListener {
    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#login(Player)
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.login(player);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#login(Player)
     */
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.logout(player);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#joinLevel(Entity)
     */
    @SubscribeEvent
    public static void onPlayerJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.joinLevel(entity);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#update(LivingEntity)
     */
    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.update(livingEntity);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#clone(Player, Player, boolean)
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        boolean wasDeath = event.isWasDeath();
        CapabilityHooks.AetherPlayerHooks.clone(originalPlayer, newPlayer, wasDeath);
    }

    /**
     * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks#changeDimension(Player)
     */
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        CapabilityHooks.AetherPlayerHooks.changeDimension(player);
    }
}
