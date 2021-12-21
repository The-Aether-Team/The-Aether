package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class HolystoneSwordItem extends SwordItem
{
	public HolystoneSwordItem() {
		super(AetherItemTiers.HOLYSTONE, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!attacker.level.isClientSide && attacker instanceof Player && target.level.getRandom().nextInt(20) == 0) {
			target.spawnAtLocation(AetherItems.AMBROSIUM_SHARD.get());
		}
		return super.hurtEnemy(stack, target, attacker);
	}
}
