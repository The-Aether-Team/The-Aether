package com.gildedgames.aether.common.item.tools.valkyrie;

import com.gildedgames.aether.common.item.tools.abilities.ValkyrieToolAbility;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import com.gildedgames.aether.common.registry.AetherItems;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

import javax.annotation.Nonnull;

public class ValkyrieShovelItem extends ShovelItem implements ValkyrieToolAbility {
    private final float attackDamage;
    private final float attackSpeed;

    public ValkyrieShovelItem(float attackDamageIn, float attackSpeedIn) {
        super(AetherItemTiers.VALKYRIE, attackDamageIn, attackSpeedIn, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_TOOLS));
        this.attackDamage = attackDamageIn + AetherItemTiers.VALKYRIE.getAttackDamageBonus();;
        this.attackSpeed = attackSpeedIn;
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@Nonnull EquipmentSlot equipmentSlot) {
        return this.createDefaultAttributes(equipmentSlot, BASE_ATTACK_DAMAGE_UUID, this.attackDamage, BASE_ATTACK_SPEED_UUID, this.attackSpeed, super.getDefaultAttributeModifiers(equipmentSlot));
    }
}
