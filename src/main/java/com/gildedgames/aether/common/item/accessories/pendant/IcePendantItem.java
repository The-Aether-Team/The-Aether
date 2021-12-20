package com.gildedgames.aether.common.item.accessories.pendant;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.item.accessories.abilities.IIceAccessory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class IcePendantItem extends PendantItem implements IIceAccessory
{
    public IcePendantItem(Properties properties) {
        super("ice_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_PENDANT, properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        handleLiquidFreezing(identifier, index, livingEntity, stack);
    }
}
