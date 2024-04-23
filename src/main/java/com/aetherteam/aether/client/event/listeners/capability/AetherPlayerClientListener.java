package com.aetherteam.aether.client.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.CapabilityClientHooks;
import io.github.fabricators_of_create.porting_lib.event.client.KeyInputCallback;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public class AetherPlayerClientListener {
//    /** TODO: PORT
//     * @see CapabilityClientHooks.AetherPlayerHooks#movementInput(Player, Input)
//     */
//    @SubscribeEvent
//    public static void onMove(MovementInputUpdateEvent event) {
//        Player player = event.getEntity();
//        Input input = event.getInput();
//        CapabilityClientHooks.AetherPlayerHooks.movementInput(player, input);
//    }
//
//    /**
//     * @see CapabilityClientHooks.AetherPlayerHooks#mouseInput(int)
//     */
//    @SubscribeEvent
//    public static void onClick(InputEvent.MouseButton.Post event) {
//        int button = event.getButton();
//        CapabilityClientHooks.AetherPlayerHooks.mouseInput(button);
//    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#keyInput(int)
     */
    public static void onPress(int key, int scancode, int action, int mods) {
        CapabilityClientHooks.AetherPlayerHooks.keyInput(key);
    }

    public static void init() {
        KeyInputCallback.EVENT.register(AetherPlayerClientListener::onPress);
    }
}
