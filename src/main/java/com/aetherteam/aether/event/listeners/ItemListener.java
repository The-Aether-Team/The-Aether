package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.ItemHooks;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemListener {
    /**
     * @see ItemHooks#addDungeonTooltips(List, ItemStack, TooltipFlag)
     */
    public static void onTooltipAdd(ItemStack itemStack, TooltipFlag tooltipFlag, List<Component> itemTooltips) {
        ItemHooks.addDungeonTooltips(itemTooltips, itemStack, tooltipFlag);
    }

    public static void init() {
        ItemTooltipCallback.EVENT.register(ItemListener::onTooltipAdd);
    }
}
