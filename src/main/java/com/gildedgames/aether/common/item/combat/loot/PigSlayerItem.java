package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;

import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.FlameParticlePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;

public class PigSlayerItem extends SwordItem
{
	public PigSlayerItem() {
		super(ItemTier.IRON, 3, -2.4f, new Item.Properties().durability(200).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (AetherTags.Entities.PIGS.contains(target.getType())) {
			if (target.getHealth() > 0.0F) {
				target.hurt(DamageSource.mobAttack(attacker), 9999);
			}
			AetherPacketHandler.sendToAll(new FlameParticlePacket(target.getId()));
		}
		return super.hurtEnemy(stack, target, attacker);
	}
}
