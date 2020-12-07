package com.aether.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.item.Item.Properties;

public class LightningSwordItem extends SwordItem {

    public LightningSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.world);
        lightningBolt.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
        attacker.world.addEntity(lightningBolt);
        return super.hitEntity(stack, target, attacker);
    }

}
