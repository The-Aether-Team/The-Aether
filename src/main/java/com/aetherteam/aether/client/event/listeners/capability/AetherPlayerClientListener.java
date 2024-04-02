package com.aetherteam.aether.client.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.CapabilityClientHooks;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

public class AetherPlayerClientListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(AetherPlayerClientListener::onMove);
        bus.addListener(AetherPlayerClientListener::onClick);
        bus.addListener(AetherPlayerClientListener::onPress);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#movementInput(Player, Input)
     */
    public static void onMove(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        CapabilityClientHooks.AetherPlayerHooks.movementInput(player, input);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#mouseInput(int)
     */
    public static void onClick(InputEvent.MouseButton.Post event) {
        int button = event.getButton();
        CapabilityClientHooks.AetherPlayerHooks.mouseInput(button);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#keyInput(int)
     */
    public static void onPress(InputEvent.Key event) {
        int key = event.getKey();
        CapabilityClientHooks.AetherPlayerHooks.keyInput(key);
    }
}
