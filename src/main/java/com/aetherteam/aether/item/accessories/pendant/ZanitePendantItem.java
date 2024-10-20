package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.item.accessories.abilities.ZaniteAccessory;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Zanite ring mining speed boost behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvent.BreakSpeed)}
 */
public class ZanitePendantItem extends PendantItem implements ZaniteAccessory {
    public ZanitePendantItem(Properties properties) {
        super("zanite_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ZANITE_PENDANT, properties);
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        if (reference.slotName().equals(AetherAccessorySlots.PENDANT_SLOT_LOCATION.toString())) {
            builder.addStackable(Attributes.MINING_EFFICIENCY, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "zanite_pendant_attack_damage_" + reference.slot()), this.handleMiningSpeed(1.0F, stack), AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        return repairMaterial.is(AetherTags.Items.ZANITE_REPAIRING);
    }
}
