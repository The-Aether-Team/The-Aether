package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.abilities.ZaniteAccessory;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.minecraft.world.item.ItemStack;

/**
 * Zanite ring mining speed boost behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvents.BreakSpeed)}
 */
public class ZanitePendantItem extends PendantItem implements ZaniteAccessory {
    public ZanitePendantItem(Properties properties) {
        super("zanite_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ZANITE_PENDANT, properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        return repairMaterial.is(AetherTags.Items.ZANITE_REPAIRING);
    }
}