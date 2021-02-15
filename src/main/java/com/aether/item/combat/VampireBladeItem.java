package com.aether.item.combat;

import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class VampireBladeItem extends SwordItem
{
    public VampireBladeItem() {
        super(ItemTier.DIAMOND, 3, -2.4f, new Item.Properties().rarity(AetherItems.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT));
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.getHealth() < attacker.getMaxHealth()) {
            attacker.heal(1.0F);
        }
        return super.hitEntity(stack, target, attacker);
    }
}
