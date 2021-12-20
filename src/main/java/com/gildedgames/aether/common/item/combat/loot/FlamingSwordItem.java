package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

@Mod.EventBusSubscriber
public class FlamingSwordItem extends SwordItem
{
	public FlamingSwordItem() {
		super(Tiers.DIAMOND, 4, -2.4f, new Item.Properties().durability(502).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@SubscribeEvent
	public static void onEntityAttack(AttackEntityEvent event) {
		Player player = event.getPlayer();
		Entity target = event.getTarget();
		if (player.getMainHandItem().getItem() == AetherItems.FLAMING_SWORD.get()) {
			if (target.isAttackable() && !target.skipAttackInteraction(player)) {
				if (target instanceof LivingEntity) {
					int defaultTime = 30;
					int fireAspectModifier = EnchantmentHelper.getFireAspect(player);
					if (fireAspectModifier > 0) {
						defaultTime += (fireAspectModifier * 4);
					}
					target.setSecondsOnFire(defaultTime);
				}
			}
		}
	}
}
