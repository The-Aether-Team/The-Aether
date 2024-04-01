package com.aetherteam.aether.item.accessories.gloves;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.combat.AetherArmorMaterials;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class ZaniteGlovesItem extends GlovesItem {
    public ZaniteGlovesItem(double punchDamage, Properties properties) {
        super(AetherArmorMaterials.ZANITE, punchDamage, "zanite_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Gloves Damage Bonus", calculateIncrease(stack), AttributeModifier.Operation.ADDITION));
        return attributes;
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
