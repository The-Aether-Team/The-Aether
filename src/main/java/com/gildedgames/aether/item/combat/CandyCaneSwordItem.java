package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import javax.annotation.Nonnull;

public class CandyCaneSwordItem extends SwordItem {
    public CandyCaneSwordItem() {
        super(AetherItemTiers.CANDY_CANE, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
    }

    /**
     * Drops candy canes if the target can drop them and if the attacker attacked with full attack strength if they're a player, with a 1/2 chance.
     * @param stack The stack used to hurt the target
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     * @return Whether the enemy was hurt or not.
     */
    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if ((attacker instanceof Player player && player.getAttackStrengthScale(1.0F) == 1.0F) || !(attacker instanceof Player)) {
            if (!target.getType().is(AetherTags.Entities.NO_CANDY_CANE_DROPS) && target.getLevel().getRandom().nextBoolean()) {
                target.spawnAtLocation(AetherItems.CANDY_CANE.get());
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
