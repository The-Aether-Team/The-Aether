package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.*;

public class LightningSwordItem extends SwordItem
{
    public LightningSwordItem() {
        super(ItemTier.DIAMOND, 3, -2.4f, new Item.Properties().maxDamage(502).rarity(AetherItems.AETHER_LOOT).group(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.world);
        lightningBolt.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
        attacker.world.addEntity(lightningBolt);
        return super.hitEntity(stack, target, attacker);
    }
}
