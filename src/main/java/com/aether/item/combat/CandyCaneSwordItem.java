package com.aether.item.combat;

import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;

public class CandyCaneSwordItem extends SwordItem
{
    public CandyCaneSwordItem() {
        super(ItemTier.GOLD, 3, -2.4F, new Item.Properties().group(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == AetherItems.CANDY_CANE.get();
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.deathTime > 0) {
            return true;
        }
        if(random.nextBoolean() && attacker instanceof PlayerEntity && !attacker.world.isRemote && target.hurtTime > 0) {
            target.entityDropItem(AetherItems.CANDY_CANE.get());
        }
        return super.hitEntity(stack, target, attacker);
    }
}
