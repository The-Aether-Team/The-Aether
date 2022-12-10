package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.capability.player.AetherPlayerCapability;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class VampireBladeItem extends SwordItem {
    public VampireBladeItem() {
        super(AetherItemTiers.VAMPIRE, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT));
    }

    /**
     * Heals the attacker for half a heart if they're missing health when hurting an enemy, but only if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.<br><br>
     * For players, this is done through tracking whether they should be healed through {@link AetherPlayerCapability#setVampireHealing(boolean)}.
     * @param stack The {@link ItemStack} used to hurt the target
     * @param target The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the enemy was hurt or not, as a {@link Boolean}.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
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
