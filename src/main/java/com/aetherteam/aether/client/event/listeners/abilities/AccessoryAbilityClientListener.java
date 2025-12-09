package com.aetherteam.aether.client.event.listeners.abilities;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.nitrogen.fabric.client.events.PlayerRenderEvents;
import com.aetherteam.nitrogen.fabric.events.CancellableCallback;
import net.minecraft.world.entity.player.Player;

public class AccessoryAbilityClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        PlayerRenderEvents.BEFORE_RENDER.register((player, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback) -> onRenderPlayer(player, callback));
        PlayerRenderEvents.BEFORE_ARM_RENDER.register((player, renderer, partialTick, poseStack, multiBufferSource, packedLight, arm, callback) -> onRenderHand(player, callback));
    }

    /**
     * Disables the player's rendering completely if wearing an Invisibility Cloak.
     */
    public static void onRenderPlayer(Player player, CancellableCallback callback) {
        if (!callback.isCanceled()) {
            if (player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak()) {
                callback.setCanceled(true);
            }
        }
    }

    /**
     * Disables the player's first-person arm rendering completely if wearing an Invisibility Cloak.
     */
    public static void onRenderHand(Player player, CancellableCallback callback) {
        if (!callback.isCanceled() && player != null) {
            if (player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak()) {
                callback.setCanceled(true);
            }
        }
    }
}
