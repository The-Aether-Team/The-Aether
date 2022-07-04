package com.gildedgames.aether.item.accessories.miscellaneous;

import com.gildedgames.aether.item.accessories.AccessoryItem;
import com.gildedgames.aether.item.accessories.abilities.SlowFallAccessory;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class GoldenFeatherItem extends AccessoryItem implements SlowFallAccessory
{
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        handleSlowFall(slotContext.entity());
    }
}
