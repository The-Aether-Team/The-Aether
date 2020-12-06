package com.aether.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.SEntityVelocityPacket;

import net.minecraft.item.Item.Properties;

public class GravititeSwordItem extends SwordItem {

	public GravititeSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.hurtTime > 0 || target.deathTime > 0) {
			target.addVelocity(0.0, 1.0, 0.0);
		}
		
		if (target instanceof ServerPlayerEntity) {
			((ServerPlayerEntity) target).connection.sendPacket(new SEntityVelocityPacket(target));
		}
		
		return super.hitEntity(stack, target, attacker);
	}

}
