package com.aetherteam.aether.item.accessories.ring;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class RingItem extends AccessoryItem {
    protected final Supplier<? extends SoundEvent> equipSound;

    public RingItem(Supplier<? extends SoundEvent> ringSound, Properties properties) {
        super(properties);
        this.equipSound = ringSound;
    }

    @Override
    public SoundInfo getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return new SoundInfo(this.equipSound.get(), 1.0F, 1.0F);
    }
}
