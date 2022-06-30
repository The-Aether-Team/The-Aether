package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.client.AetherSoundEvents;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class LightningKnifeItem extends Item
{
	public LightningKnifeItem() {
		super(new Item.Properties().rarity(AetherItems.AETHER_LOOT).stacksTo(16).tab(AetherItemGroups.AETHER_WEAPONS));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
		ItemStack heldItem = playerIn.getItemInHand(hand);
		if (!worldIn.isClientSide) {
			if (!playerIn.getAbilities().instabuild && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, heldItem) == 0) {
				heldItem.shrink(1);
			}
			ThrownLightningKnife lightningKnife = new ThrownLightningKnife(playerIn, worldIn);
			lightningKnife.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 0.8F, 1.0F);
			worldIn.addFreshEntity(lightningKnife);
		}
		worldIn.playLocalSound(playerIn.getX(), playerIn.getY(), playerIn.getZ(), AetherSoundEvents.ITEM_LIGHTNING_KNIFE_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F), false);
		playerIn.awardStat(Stats.ITEM_USED.get(this));
		return InteractionResultHolder.success(heldItem);
	}
}
