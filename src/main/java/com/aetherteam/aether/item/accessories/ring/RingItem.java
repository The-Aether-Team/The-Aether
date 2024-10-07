package com.aetherteam.aether.item.accessories.ring;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class RingItem extends AccessoryItem {
    public RingItem(Holder<SoundEvent> ringSound, Properties properties) {
        super(ringSound, properties);
    }
}
