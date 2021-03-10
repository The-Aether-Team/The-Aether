package com.gildedgames.aether.common.item.combat;

import java.util.Random;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class HolystoneSwordItem extends SwordItem
{
	protected final Random rand = new Random();
	
	public HolystoneSwordItem() {
		super(AetherItemTiers.HOLYSTONE, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level.isClientSide && rand.nextInt(20) == 0 && attacker instanceof PlayerEntity && target.hurtTime > 0 && target.deathTime > 0) {
			target.spawnAtLocation(AetherItems.AMBROSIUM_SHARD.get());
		}
		
		return super.hurtEnemy(stack, target, attacker);
	}
}
