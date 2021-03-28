package com.gildedgames.aether.common.item.accessories.pendant;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.gildedgames.aether.common.item.accessories.abilities.IIceAccessory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class IcePendantItem extends AccessoryItem
{
    public IcePendantItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        IIceAccessory.handleLiquidFreezing(identifier, index, livingEntity, stack);
    }
}
