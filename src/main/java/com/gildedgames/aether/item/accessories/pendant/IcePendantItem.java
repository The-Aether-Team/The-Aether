package com.gildedgames.aether.item.accessories.pendant;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.item.accessories.abilities.FreezingAccessory;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class IcePendantItem extends PendantItem implements FreezingAccessory {
    public IcePendantItem(Properties properties) {
        super("ice_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_PENDANT, properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        return repairMaterial.is(AetherTags.Items.ICE_REPAIRING);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        this.freezeTick(slotContext, stack);
    }
}
