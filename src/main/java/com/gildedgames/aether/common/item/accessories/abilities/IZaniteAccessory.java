package com.gildedgames.aether.common.item.accessories.abilities;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.apache.commons.lang3.tuple.ImmutableTriple;

public interface IZaniteAccessory
{
    static void handleMiningSpeed(PlayerEvent.BreakSpeed event, ImmutableTriple<String, Integer, ItemStack> triple) {
        float originalSpeed = event.getOriginalSpeed();
        ItemStack stack = triple.getRight();
        float newSpeed = originalSpeed * (1.0F + (((float) stack.getDamageValue()) / (((float) stack.getMaxDamage()) * 3.0F)));
        event.setNewSpeed(newSpeed == originalSpeed ? originalSpeed : newSpeed + originalSpeed);
    }
}
