package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.abilities.ZaniteAccessory;
import net.minecraft.world.item.ItemStack;

public class ZanitePendantItem extends PendantItem implements ZaniteAccessory {
    public ZanitePendantItem(Properties properties) {
        super("zanite_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ZANITE_PENDANT, properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        return repairMaterial.is(AetherTags.Items.ZANITE_REPAIRING);
    }
}
