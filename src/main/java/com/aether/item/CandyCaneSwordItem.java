package com.aether.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class CandyCaneSwordItem extends SwordItem {
    public CandyCaneSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == AetherItems.CANDY_CANE;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.deathTime > 0) {
            return true;
        }
        if(random.nextBoolean() && attacker instanceof PlayerEntity && !attacker.world.isRemote && target.hurtTime > 0) {
            target.entityDropItem(AetherItems.CANDY_CANE);
        }
        return super.hitEntity(stack, target, attacker);
    }
}
