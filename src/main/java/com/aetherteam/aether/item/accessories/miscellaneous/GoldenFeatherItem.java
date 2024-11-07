package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.abilities.SlowFallAccessory;
import io.wispforest.accessories.api.events.extra.AllowWalkingOnSnow;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.item.ItemStack;

public class GoldenFeatherItem extends AccessoryItem implements SlowFallAccessory, AllowWalkingOnSnow {
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        this.handleSlowFall(reference.entity());
    }

    @Override
    public TriState allowWalkingOnSnow(ItemStack stack, SlotReference reference) {
        return TriState.TRUE;
    }
}
