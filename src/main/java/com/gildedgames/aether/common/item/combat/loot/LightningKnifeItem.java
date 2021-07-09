package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.entity.projectile.combat.LightningKnifeEntity;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.client.registry.AetherSoundEvents;

import com.gildedgames.aether.core.capability.interfaces.IAetherEntity;
import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class LightningKnifeItem extends Item
{
	public LightningKnifeItem() {
		super(new Item.Properties().rarity(AetherItems.AETHER_LOOT).stacksTo(16).tab(AetherItemGroups.AETHER_WEAPONS));
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand hand) {
		ItemStack heldItem = playerIn.getItemInHand(hand);
		playerIn.swing(hand);
		if (!worldIn.isClientSide) {
			if (!playerIn.abilities.instabuild && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, heldItem) == 0) {
				heldItem.shrink(1);
			}
			LightningKnifeEntity lightningKnife = new LightningKnifeEntity(playerIn, worldIn);
			lightningKnife.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 0.8F, 1.0F);
			worldIn.addFreshEntity(lightningKnife);
		}
		worldIn.playLocalSound(playerIn.getX(), playerIn.getY(), playerIn.getZ(), AetherSoundEvents.ITEM_LIGHTNING_KNIFE_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F), false);
		playerIn.awardStat(Stats.ITEM_USED.get(this));
		return ActionResult.success(heldItem);
	}
}
