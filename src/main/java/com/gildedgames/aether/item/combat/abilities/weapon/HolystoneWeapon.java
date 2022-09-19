package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface HolystoneWeapon {
    /**
     * Drops ambrosium at a target's position if it can drop ambrosium, and if the attacker attacked with full attack strength if they're a player, with a 1/25 chance.
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     */
    default void dropAmbrosium(LivingEntity target, LivingEntity attacker) {
        if ((attacker instanceof Player player && player.getAttackStrengthScale(1.0F) == 1.0F) || !(attacker instanceof Player)) {
            if (!target.getType().is(AetherTags.Entities.NO_AMBROSIUM_DROPS) && target.getLevel().getRandom().nextInt(25) == 0) {
                target.spawnAtLocation(AetherItems.AMBROSIUM_SHARD.get());
            }
        }
    }
}
