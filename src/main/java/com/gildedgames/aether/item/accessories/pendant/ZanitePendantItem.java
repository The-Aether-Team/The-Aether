package com.gildedgames.aether.item.accessories.pendant;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.item.accessories.abilities.ZaniteAccessory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

import top.theillusivec4.curios.api.SlotContext;

/**
 * Zanite ring mining speed boost behavior is called by {@link com.gildedgames.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvent.BreakSpeed)}
 */
public class ZanitePendantItem extends PendantItem implements ZaniteAccessory {
    public ZanitePendantItem(Properties properties) {
        super("zanite_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ZANITE_PENDANT, properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        return repairMaterial.is(AetherTags.Items.ZANITE_REPAIRING);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        this.damageZaniteAccessory(slotContext, stack);
    }
}