package com.gildedgames.aether.common.item.accessories.cape;

import com.gildedgames.aether.common.item.accessories.abilities.ISlowFallAccessory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ValkyrieCapeItem extends CapeItem implements ISlowFallAccessory
{
    public ValkyrieCapeItem(Properties properties) {
        super("valkyrie_cape", properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        handleSlowFall(livingEntity);
    }
}
