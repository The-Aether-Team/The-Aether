package com.aetherteam.aether.client.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.CapabilityClientHooks;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class AetherPlayerClientListener {
    @SubscribeEvent
    public static void onMove(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        CapabilityClientHooks.AetherPlayerHooks.movementInput(player, input);
    }

    @SubscribeEvent
    public static void onClick(InputEvent.MouseButton.Post event) {
        int button = event.getButton();
        CapabilityClientHooks.AetherPlayerHooks.mouseInput(button);
    }

    @SubscribeEvent
    public static void onClick(InputEvent.Key event) {
        int key = event.getKey();
        CapabilityClientHooks.AetherPlayerHooks.keyInput(key);
    }
}
