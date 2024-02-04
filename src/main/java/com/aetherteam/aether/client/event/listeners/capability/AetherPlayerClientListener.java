package com.aetherteam.aether.client.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.CapabilityClientHooks;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class AetherPlayerClientListener {
    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#movementInput(Player, Input)
     */
    @SubscribeEvent
    public static void onMove(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        CapabilityClientHooks.AetherPlayerHooks.movementInput(player, input);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#mouseInput(int)
     */
    @SubscribeEvent
    public static void onClick(InputEvent.MouseButton.Post event) {
        int button = event.getButton();
        CapabilityClientHooks.AetherPlayerHooks.mouseInput(button);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#keyInput(int)
     */
    @SubscribeEvent
    public static void onPress(InputEvent.Key event) {
        int key = event.getKey();
        CapabilityClientHooks.AetherPlayerHooks.keyInput(key);
    }
}
