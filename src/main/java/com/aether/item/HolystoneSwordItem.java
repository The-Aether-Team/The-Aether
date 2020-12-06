package com.aether.item;

import java.util.Random;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import net.minecraft.item.Item.Properties;

public class HolystoneSwordItem extends SwordItem {

	protected final Random rand = new Random();
	
	public HolystoneSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.world.isRemote && rand.nextInt(20) == 0 && attacker instanceof PlayerEntity && target.hurtTime > 0 && target.deathTime > 0) {
			target.entityDropItem(AetherItems.AMBROSIUM_SHARD);
		}
		
		return super.hitEntity(stack, target, attacker);
	}

}
