package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import javax.annotation.Nonnull;

public class CandyCaneSwordItem extends SwordItem {
    public CandyCaneSwordItem() {
        super(AetherItemTiers.CANDY_CANE, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
    }

    /**
     * Drops candy canes if the target can drop them and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}, with a 1/2 chance.
     * @param stack The {@link ItemStack} used to hurt the target
     * @param target The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the enemy was hurt or not, as a {@link Boolean}.
     */
    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (!target.getType().is(AetherTags.Entities.NO_CANDY_CANE_DROPS) && target.getLevel().getRandom().nextBoolean()) {
                target.spawnAtLocation(AetherItems.CANDY_CANE.get());
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
