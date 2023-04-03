package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.item.combat.AetherArmorItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class NeptuneArmorItem extends AetherArmorItem {
    //Copied from ArmorItem
    private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    public NeptuneArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributes = ImmutableMultimap.builder();
        attributes.putAll(super.getAttributeModifiers(slot, stack));
        if (slot == this.getEquipmentSlot()) {
            attributes.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()], "Swim speed", 0.2, AttributeModifier.Operation.ADDITION));
        }
        return attributes.build();
    }
}
