package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItemTiers;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.capability.lightning.LightningTracker;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class LightningSwordItem extends SwordItem
{
    public LightningSwordItem() {
        super(AetherItemTiers.LIGHTNING, 3, -2.4f, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.level);
        if (lightningBolt != null) {
            if (!attacker.level.isClientSide) {
                LightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(attacker));
            }
            lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
            attacker.level.addFreshEntity(lightningBolt);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
