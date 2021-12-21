package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class LightningSwordItem extends SwordItem
{
    public LightningSwordItem() {
        super(Tiers.DIAMOND, 4, -2.4f, new Item.Properties().durability(502).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.level);
        if (lightningBolt != null) {
            if (!attacker.level.isClientSide) {
                ILightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(attacker));
            }
            lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
            attacker.level.addFreshEntity(lightningBolt);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
