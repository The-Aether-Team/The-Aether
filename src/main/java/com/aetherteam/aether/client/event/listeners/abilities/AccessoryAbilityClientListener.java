package com.aetherteam.aether.client.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

public class AccessoryAbilityClientListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(AccessoryAbilityClientListener::onRenderPlayer);
        bus.addListener(AccessoryAbilityClientListener::onRenderHand);
    }

    /**
     * Disables the player's rendering completely if wearing an Invisibility Cloak.
     */
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (!event.isCanceled()) {
            if (player.getData(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak()) {
                event.setCanceled(true);
            }
        }
    }

    /**
     * Disables the player's first-person arm rendering completely if wearing an Invisibility Cloak.
     */
    public static void onRenderHand(RenderArmEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (!event.isCanceled() && player != null) {
            if (player.getData(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak()) {
                event.setCanceled(true);
            }
        }
    }
}
