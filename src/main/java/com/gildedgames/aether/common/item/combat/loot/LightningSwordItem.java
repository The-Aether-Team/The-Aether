package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.*;

public class LightningSwordItem extends SwordItem
{
    public LightningSwordItem() {
        super(ItemTier.DIAMOND, 4, -2.4f, new Item.Properties().durability(502).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.level);
        if (lightningBolt != null) {
            lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
            attacker.level.addFreshEntity(lightningBolt);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
