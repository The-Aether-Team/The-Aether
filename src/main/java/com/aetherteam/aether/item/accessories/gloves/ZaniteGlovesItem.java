package com.aetherteam.aether.item.accessories.gloves;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.item.combat.AetherArmorMaterials;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ZaniteGlovesItem extends GlovesItem {
    ResourceLocation DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "zanite_gloves_modified_attack_damage");

    public ZaniteGlovesItem(double punchDamage, Properties properties) {
        super(AetherArmorMaterials.ZANITE, punchDamage, "zanite_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, properties);
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        if (reference.slotName().equals(AetherAccessorySlots.GLOVES_SLOT_LOCATION.toString())) {
            builder.addStackable(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_PUNCH_DAMAGE_ID, this.calculateIncrease(stack), AttributeModifier.Operation.ADD_VALUE));
        }
    }

    /**
     * If the current durability is greater than 3/4 the max durability, the damage will be 0.25.<br><br>
     * If the current durability is less than 3/4 the max durability and greater than 1/3 the max durability, the damage will be 0.5.<br><br>
     * If the current durability is less than 1/3 the max durability, the damage will be 0.75.
     *
     * @param stack The {@link ItemStack} of the item.
     * @return The {@link Float} value of the Zanite Gloves' damage.
     */
    private float calculateIncrease(ItemStack stack) {
        int maxDurability = stack.getMaxDamage();
        int currentDurability = maxDurability - stack.getDamageValue();
        if (currentDurability >= maxDurability - (int) (maxDurability / 4.0)) {
            return 0.25F;
        } else if (currentDurability >= maxDurability - (int) (maxDurability / 1.5)) {
            return 0.5F;
        } else {
            return 0.75F;
        }
    }
}
