package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class CandyCaneSwordItem extends SwordItem
{
    public CandyCaneSwordItem() {
        super(AetherItemTiers.CANDY_CANE, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(AetherItems.CANDY_CANE.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level.isClientSide && attacker instanceof Player && target.level.getRandom().nextBoolean()) {
            target.spawnAtLocation(AetherItems.CANDY_CANE.get());
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
