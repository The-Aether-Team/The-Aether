package com.gildedgames.aether.client.event.listeners.abilities;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class AccessoryAbilityClientListener {
    /**
     * Disables the player's rendering completely if wearing an Invisibility Cloak.
     */
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (!event.isCanceled() && EquipmentUtil.hasInvisibilityCloak(player)) {
            event.setCanceled(true);
        }
    }

    /**
     * Disables the player's first-person arm rendering completely if wearing an Invisibility Cloak.
     */
    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (!event.isCanceled() && player != null && EquipmentUtil.hasInvisibilityCloak(player)) {
            event.setCanceled(true);
        }
    }
}
