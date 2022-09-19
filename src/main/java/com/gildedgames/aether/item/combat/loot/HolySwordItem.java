package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import javax.annotation.Nonnull;

public class HolySwordItem extends SwordItem {
    public HolySwordItem() {
        super(AetherItemTiers.HOLY, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    /**
     * Deals 15 damage to undead mobs or mobs that treat healing and harming effects as inverted, with an extra 2.5 damage for every level of smite the item has.
     * This occurs if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}. This also reduces the item's durability by 10.
     * @param stack The stack used to hurt the target
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     * @return Whether the enemy was hurt or not.
     */
    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (target.getMobType() == MobType.UNDEAD || target.isInvertedHealAndHarm()) {
                DamageSource damageSource = attacker instanceof Player player ? DamageSource.playerAttack(player) : DamageSource.mobAttack(attacker);
                float damageAmount = 15.0F;
                int smiteModifier = stack.getEnchantmentLevel(Enchantments.SMITE);
                if (smiteModifier > 0) {
                    damageAmount += (smiteModifier * 2.5);
                }
                target.hurt(damageSource, damageAmount);
                stack.hurtAndBreak(10, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
