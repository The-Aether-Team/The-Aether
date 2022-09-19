package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.client.AetherSoundEvents;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class LightningKnifeItem extends Item {
	public LightningKnifeItem() {
		super(new Item.Properties().rarity(AetherItems.AETHER_LOOT).stacksTo(16).tab(AetherItemGroups.AETHER_WEAPONS));
	}

	/**
	 * Spawns a lightning knife projectile when right-clicking, consuming the item.
	 * @param level The level of the user.
	 * @param player The entity using this item.
	 * @param hand The hand in which the item is being used.
	 * @return Success (the item is swung).
	 */
	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
		ItemStack heldStack = player.getItemInHand(hand);
		if (!level.isClientSide()) {
			if (!player.getAbilities().instabuild && heldStack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) == 0) { // Note: Lightning knives can't be enchanted with infinity in survival, but we still implement the behavior.
				heldStack.shrink(1);
			}
			ThrownLightningKnife lightningKnife = new ThrownLightningKnife(player, level);
			lightningKnife.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.8F, 1.0F);
			level.addFreshEntity(lightningKnife);
		}
		level.playLocalSound(player.getX(), player.getY(), player.getZ(), AetherSoundEvents.ITEM_LIGHTNING_KNIFE_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F), false);
		player.awardStat(Stats.ITEM_USED.get(this));
		return InteractionResultHolder.success(heldStack);
	}
}
