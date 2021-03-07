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
		super(AetherItemTiers.HOLYSTONE, 3, -2.4F, new Item.Properties().group(AetherItemGroups.AETHER_WEAPONS));
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.world.isRemote && rand.nextInt(20) == 0 && attacker instanceof PlayerEntity && target.hurtTime > 0 && target.deathTime > 0) {
			target.entityDropItem(AetherItems.AMBROSIUM_SHARD.get());
		}
		
		return super.hitEntity(stack, target, attacker);
	}
}
