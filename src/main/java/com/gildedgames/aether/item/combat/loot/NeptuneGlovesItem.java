package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.item.accessories.gloves.GlovesItem;
import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;
import java.util.function.Supplier;

public class NeptuneGlovesItem extends GlovesItem {
    public NeptuneGlovesItem(double punchDamage, String glovesName, Supplier<? extends SoundEvent> glovesSound, Properties properties) {
        super(punchDamage, glovesName, glovesSound, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slotContext, uuid, stack);
        attributes.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, "Swim speed", 0.2, AttributeModifier.Operation.ADDITION));
        return super.getAttributeModifiers(slotContext, uuid, stack);
    }
}
