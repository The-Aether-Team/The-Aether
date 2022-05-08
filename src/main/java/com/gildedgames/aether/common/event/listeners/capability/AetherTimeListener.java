package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.common.event.hooks.CapabilityHooks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Deprecated
@Mod.EventBusSubscriber
public class AetherTimeListener {
    /*@SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        CapabilityHooks.AetherTimeHooks.login(player);
    }

    @SubscribeEvent
    public static void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getPlayer();
        CapabilityHooks.AetherTimeHooks.changeDimension(player);
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        LevelAccessor levelAccessor = event.getWorld();
        CapabilityHooks.AetherTimeHooks.load(levelAccessor);
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        LevelAccessor levelAccessor = event.getWorld();
        CapabilityHooks.AetherTimeHooks.unload(levelAccessor);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        Level level = event.world;
        if (event.phase == TickEvent.Phase.END) {
            CapabilityHooks.AetherTimeHooks.tick(level);
        }
    }*/
}
