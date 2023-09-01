package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.ItemHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class ItemListener {
    /**
     * @see ItemHooks#addDungeonTooltips(List, ItemStack, TooltipFlag)
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTooltipAdd(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        TooltipFlag tooltipFlag = event.getFlags();
        List<Component> itemTooltips = event.getToolTip();
        ItemHooks.addDungeonTooltips(itemTooltips, itemStack, tooltipFlag);
    }
}
