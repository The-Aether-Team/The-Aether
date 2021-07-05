package com.gildedgames.aether.common.item.accessories.miscellaneous;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.gildedgames.aether.common.item.accessories.abilities.ISlowFallAccessory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class GoldenFeatherItem extends AccessoryItem implements ISlowFallAccessory
{
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        handleSlowFall(livingEntity);
    }
}
