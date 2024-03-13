package com.aetherteam.aether.item.accessories.ring;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Supplier;

public class RingItem extends AccessoryItem {
    public RingItem(Supplier<? extends SoundEvent> ringSound, Properties properties) {
        super(ringSound, properties);
    }
}
