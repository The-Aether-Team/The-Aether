package com.gildedgames.aether.common.item.accessories.gloves;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class ZaniteGlovesItem extends GlovesItem
{
    public ZaniteGlovesItem(double punchDamage, Properties properties) {
        super(punchDamage, "zanite_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Gloves Damage Bonus", calculateIncrease(stack), AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    private float calculateIncrease(ItemStack tool) {
        int current = tool.getDamageValue();
        int maxDamage = tool.getMaxDamage();

        if (maxDamage - 55 <= current) {
            return 2.0F;
        } else if (maxDamage - 110 <= current) {
            return 1.5F;
        } else {
            return 1.0F;
        }
    }
}
