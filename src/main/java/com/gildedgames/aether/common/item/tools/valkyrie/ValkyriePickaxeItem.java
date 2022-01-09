package com.gildedgames.aether.common.item.tools.valkyrie;

import com.gildedgames.aether.common.item.tools.abilities.IValkyrieToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import com.gildedgames.aether.common.registry.AetherItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

import net.minecraftforge.common.ForgeMod;

public class ValkyriePickaxeItem extends PickaxeItem implements IValkyrieToolItem
{
    private final float attackDamage;
    private final float attackSpeed;

    private Multimap<Attribute, AttributeModifier> pickaxeAttributes;

    public ValkyriePickaxeItem(int attackDamageIn, float attackSpeedIn) {
        super(AetherItemTiers.VALKYRIE, attackDamageIn, attackSpeedIn, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_TOOLS));
        this.attackDamage = attackDamageIn + AetherItemTiers.VALKYRIE.getAttackDamageBonus();
        this.attackSpeed = attackSpeedIn;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_MODIFIER_UUID, "Tool modifier", this.getReachDistanceModifier(), AttributeModifier.Operation.ADDITION));
        this.pickaxeAttributes = builder.build();
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.pickaxeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}
