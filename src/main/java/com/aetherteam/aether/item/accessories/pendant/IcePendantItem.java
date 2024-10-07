package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.abilities.FreezingAccessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.item.ItemStack;

public class IcePendantItem extends PendantItem implements FreezingAccessory {
    public IcePendantItem(Properties properties) {
        super("ice_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_PENDANT, properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        return repairMaterial.is(AetherTags.Items.ICE_REPAIRING);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!reference.entity().isInFluidType()) {
            this.freezeTick(reference, stack);
        }
    }
}
