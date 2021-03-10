package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.SEntityVelocityPacket;

public class GravititeSwordItem extends SwordItem
{
	public GravititeSwordItem() {
		super(AetherItemTiers.GRAVITITE, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.hurtTime > 0 || target.deathTime > 0) {
			target.push(0.0, 1.0, 0.0);
		}
		
		if (target instanceof ServerPlayerEntity) {
			((ServerPlayerEntity) target).connection.send(new SEntityVelocityPacket(target));
		}
		
		return super.hurtEnemy(stack, target, attacker);
	}
}
