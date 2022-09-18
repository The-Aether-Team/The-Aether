package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface HolystoneWeapon {
    /**
     * Drops ambrosium if the stack is a holystone weapon, if the target can drop ambrosium, if the call isn't clientside, and if the attacker attacked with full attack strength if they're a player, with a 1/20 chance.
     * @param stack The stack used to hurt the target
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     */
    default void dropAmbrosium(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.is(AetherTags.Items.HOLYSTONE_WEAPONS) && !target.getType().is(AetherTags.Entities.NO_AMBROSIUM_DROPS) && !attacker.getLevel().isClientSide() && target.level.getRandom().nextInt(20) == 0
                && ((attacker instanceof Player player && player.getAttackStrengthScale(1.0F) == 1.0F) || !(attacker instanceof Player))) {
            target.spawnAtLocation(AetherItems.AMBROSIUM_SHARD.get());
        }
    }
}
