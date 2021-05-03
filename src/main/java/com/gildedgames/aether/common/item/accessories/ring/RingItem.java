package com.gildedgames.aether.common.item.accessories.ring;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class RingItem extends AccessoryItem
{
    protected final Supplier<SoundEvent> equipSound;

    public RingItem(Supplier<SoundEvent> ringSound, Properties properties) {
        super(properties);
        this.equipSound = ringSound;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(this.equipSound.get(), 1.0f, 1.0f);
    }
}
