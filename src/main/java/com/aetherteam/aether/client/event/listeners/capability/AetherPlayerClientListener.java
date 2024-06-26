package com.aetherteam.aether.client.event.listeners.capability;

import com.aetherteam.aether.client.event.hooks.CapabilityClientHooks;
import io.github.fabricators_of_create.porting_lib.event.client.KeyInputCallback;
import io.github.fabricators_of_create.porting_lib.event.client.MouseInputEvents;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public class AetherPlayerClientListener {
    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#movementInput(Player, Input)
     */
    public static void onMove(Player player, Input input) {
        CapabilityClientHooks.AetherPlayerHooks.movementInput(player, input);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#mouseInput(int)
     */
    public static void onClick(int button, int modifiers, MouseInputEvents.Action action) {
        CapabilityClientHooks.AetherPlayerHooks.mouseInput(button);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#keyInput(int)
     */
    public static void onPress(int key, int scancode, int action, int mods) {
        CapabilityClientHooks.AetherPlayerHooks.keyInput(key);
    }

    public static void init() {
        MouseInputEvents.AFTER_BUTTON.register(AetherPlayerClientListener::onClick);
        KeyInputCallback.EVENT.register(AetherPlayerClientListener::onPress);
    }
}
