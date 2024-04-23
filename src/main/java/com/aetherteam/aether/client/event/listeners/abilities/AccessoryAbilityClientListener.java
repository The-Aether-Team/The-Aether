package com.aetherteam.aether.client.event.listeners.abilities;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fabricators_of_create.porting_lib.client_events.event.client.RenderArmCallback;
import io.github.fabricators_of_create.porting_lib.event.client.RenderPlayerEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

public class AccessoryAbilityClientListener {
    /**
     * Disables the player's rendering completely if wearing an Invisibility Cloak.
     */
    public static boolean onRenderPlayer(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (AetherPlayer.get(player).isWearingInvisibilityCloak()) {
            return true;
        }
        return false;
    }

    /**
     * Disables the player's first-person arm rendering completely if wearing an Invisibility Cloak.
     */
    public static boolean onRenderHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, HumanoidArm arm) {
        if (player != null) {
            AetherPlayer aetherPlayer = AetherPlayer.get(player);
            if (aetherPlayer.isWearingInvisibilityCloak()) {
                return true;
            }
        }

        return false;
    }

    public static void init() {
        RenderPlayerEvents.PRE.register(AccessoryAbilityClientListener::onRenderPlayer);
        RenderArmCallback.EVENT.register(AccessoryAbilityClientListener::onRenderHand);
    }
}
