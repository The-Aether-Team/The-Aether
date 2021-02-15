package com.aether.item.combat;

import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.SEntityVelocityPacket;

public class GravititeSwordItem extends SwordItem
{
	public GravititeSwordItem() {
		super(AetherItemTier.GRAVITITE, 3, -2.4F, new Item.Properties().group(AetherItemGroups.AETHER_WEAPONS));
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
