package com.gildedgames.aether.item.accessories.cape;

import com.gildedgames.aether.item.accessories.abilities.SlowFallAccessory;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class ValkyrieCapeItem extends CapeItem implements SlowFallAccessory {
    public ValkyrieCapeItem(Properties properties) {
        super("valkyrie_cape", properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        this.handleSlowFall(slotContext.entity());
    }
}
