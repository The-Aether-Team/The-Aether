package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;

public class CandyCaneSwordItem extends SwordItem
{
    public CandyCaneSwordItem() {
        super(ItemTier.GOLD, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == AetherItems.CANDY_CANE.get();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.deathTime > 0) {
            return true;
        }
        if(random.nextBoolean() && attacker instanceof PlayerEntity && !attacker.level.isClientSide && target.hurtTime > 0) {
            target.spawnAtLocation(AetherItems.CANDY_CANE.get());
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
