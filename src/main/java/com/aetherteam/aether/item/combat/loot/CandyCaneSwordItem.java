package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class CandyCaneSwordItem extends SwordItem {
    public CandyCaneSwordItem() {
        super(AetherItemTiers.CANDY_CANE, 3, -2.4F, new Item.Properties());
    }

    /**
     * Drops Candy Canes if the target can drop them and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}, with a 1/2 chance.
     * @param stack The {@link ItemStack} used to hurt the target
     * @param target The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the enemy was hurt or not, as a {@link Boolean}.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (!target.getType().is(AetherTags.Entities.NO_CANDY_CANE_DROPS) && target.getLevel().getRandom().nextBoolean()) {
                target.spawnAtLocation(AetherItems.CANDY_CANE.get());
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
