package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;

public class GravititeSwordItem extends SwordItem
{
	public GravititeSwordItem() {
		super(AetherItemTiers.GRAVITITE, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.push(0.0, 1.0, 0.0);
		if (target instanceof ServerPlayer) {
			((ServerPlayer) target).connection.send(new ClientboundSetEntityMotionPacket(target));
		}
		return super.hurtEnemy(stack, target, attacker);
	}
}
