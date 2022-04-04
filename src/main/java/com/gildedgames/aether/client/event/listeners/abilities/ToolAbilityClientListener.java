package com.gildedgames.aether.client.event.listeners.abilities;

import com.gildedgames.aether.common.item.tools.abilities.ValkyrieTool;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ToolAbilityClientListener {
    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemStack();
        ValkyrieTool.attackEntityWithReach(itemStack, player);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemStack();
        event.setCanceled(ValkyrieTool.attackEntityWithReach(itemStack, player));
    }

    @SubscribeEvent
    public static void onClientTick(EntityViewRenderEvent.CameraSetup event) {
        Player player = Minecraft.getInstance().player;
        ValkyrieTool.checkForEntityWithReach(player);
    }
}
