package com.gildedgames.aether.common.item.accessories.gloves;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class ZaniteGlovesItem extends GlovesItem
{
    public ZaniteGlovesItem(Properties properties, double punchDamage) {
        super(properties, punchDamage);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Gloves Damage Bonus", calculateIncrease(stack), AttributeModifier.Operation.ADDITION));
        return atts;
    }

    private float calculateIncrease(ItemStack tool) {
        int current = tool.getDamageValue();
        int maxDamage = tool.getMaxDamage();

        if (maxDamage - 50 <= current) {
            return 3.5F;
        }
        else if (maxDamage - 110 <= current) {
            return 3.0F;
        }
        else if (maxDamage - 200 <= current) {
            return 2.5F;
        }
        else if (maxDamage - 239 <= current) {
            return 2.0F;
        }
        else {
            return 1.0F;
        }
    }
}
