package com.gildedgames.aether.common.block.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.LockCode;

public class LockCodeItem extends LockCode {

    public static final LockCodeItem UNBOUND_LOCK = new LockCodeItem("");

    public LockCodeItem(String p_i45903_1_) {
        super(p_i45903_1_);
    }

    public boolean unlocksWith(ItemStack itemStack) {
        CompoundNBT itemTag = itemStack.getTag();

        return !itemStack.isEmpty() && itemTag != null
                && this.key.equals(itemTag.getString("Lock"));
    }

}
