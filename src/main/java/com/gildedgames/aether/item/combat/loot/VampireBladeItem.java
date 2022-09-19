package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.capability.player.AetherPlayerCapability;
import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import javax.annotation.Nonnull;

public class VampireBladeItem extends SwordItem {
    public VampireBladeItem() {
        super(AetherItemTiers.VAMPIRE, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    /**
     * Heals the attacker for half a heart when hurting the enemy if the attacker attacked with full attack strength if they're a player.
     * For players, this is done through tracking whether they should be healed through {@link AetherPlayerCapability#setVampireHealing(boolean)}.
     * @param stack The stack used to hurt the target
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     * @return Whether the enemy was hurt or not.
     */
    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if ((attacker instanceof Player player && player.getAttackStrengthScale(1.0F) == 1.0F) || !(attacker instanceof Player)) {
            if (attacker.getHealth() < attacker.getMaxHealth()) {
                if (attacker instanceof Player player) {
                    AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setVampireHealing(true));
                } else {
                    attacker.heal(1.0F);
                }
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
