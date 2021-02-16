package com.aether.item.accessories;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class AccessoryItem extends Item implements ICurioItem
{
    public AccessoryItem(Properties properties) {
        super(properties);
    }

    public void curioTick(String identifier, int index, LivingEntity living, ItemStack stack) {
        if (!living.getEntityWorld().isRemote)
        {
            //Aether.LOGGER.info(true);
        }
    }
}


