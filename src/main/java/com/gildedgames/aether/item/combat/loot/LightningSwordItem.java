package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.capability.lightning.LightningTracker;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;

import javax.annotation.Nonnull;

public class LightningSwordItem extends SwordItem {
    public LightningSwordItem() {
        super(AetherItemTiers.LIGHTNING, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    /**
     * Summons lightning at the position of the target if the attacker attacked with full attack strength if they're a player. {@link com.gildedgames.aether.event.listeners.abilities.WeaponAbilityListener#onLightningStrike(EntityStruckByLightningEvent)} prevents the attacker from being injured by the lightning.
     * @param stack The stack used to hurt the target
     * @param target The hurt entity.
     * @param attacker The attacking entity.
     * @return Whether the enemy was hurt or not.
     */
    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if ((attacker instanceof Player player && player.getAttackStrengthScale(1.0F) == 1.0F) || !(attacker instanceof Player)) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.getLevel());
            if (lightningBolt != null) {
                LightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(attacker));
                lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
                attacker.getLevel().addFreshEntity(lightningBolt);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
