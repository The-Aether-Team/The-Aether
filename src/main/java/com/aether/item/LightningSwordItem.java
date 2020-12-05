package com.aether.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.server.ServerWorld;

public class LightningSwordItem extends SwordItem {

    public LightningSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        LightningBoltEntity lightningBolt = new LightningBoltEntity(target.world, target.getPosX(), target.getPosY(), target.getPosZ(), false);
        ((ServerWorld)target.world).addLightningBolt(new LightningBoltEntity(target.world, target.getPosX(), target.getPosY(), target.getPosZ(), false));
        return super.hitEntity(stack, target, attacker);
    }

}
