package com.gildedgames.aether.common.item.accessories.abilities;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import top.theillusivec4.curios.api.SlotResult;

public interface ZaniteAccessory
{
    static void handleMiningSpeed(PlayerEvent.BreakSpeed event, SlotResult slotResult) {
        float originalSpeed = event.getOriginalSpeed();
        ItemStack stack = slotResult.stack();
        float newSpeed = originalSpeed * (1.0F + (((float) stack.getDamageValue()) / (((float) stack.getMaxDamage()) * 3.0F)));
        event.setNewSpeed(newSpeed == originalSpeed ? originalSpeed : newSpeed + originalSpeed);
    }
}
