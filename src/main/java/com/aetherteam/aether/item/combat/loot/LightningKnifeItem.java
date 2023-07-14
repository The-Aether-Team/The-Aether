package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;

import javax.annotation.Nullable;
import java.util.List;

public class LightningKnifeItem extends Item {
	public LightningKnifeItem() {
		super(new Item.Properties().rarity(AetherItems.AETHER_LOOT).stacksTo(16));
	}

	/**
	 * Spawns a thrown Lightning Knife projectile when right-clicking, consuming the item.<br><br>
	 * {@link com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onLightningStrike(EntityStruckByLightningEvent)} prevents the attacker from being injured by the lightning.
	 * @param level The {@link Level} of the user.
	 * @param player The {@link Player} using this item.
	 * @param hand The {@link InteractionHand} in which the item is being used.
	 * @return Success (the item is swung). This is an {@link InteractionResultHolder InteractionResultHolder&lt;ItemStack&gt;}.
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack heldStack = player.getItemInHand(hand);
		if (!level.isClientSide()) {
			if (!player.getAbilities().instabuild && heldStack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) == 0) { // Note: Lightning knives can't be enchanted with Infinity in survival, but we still implement the behavior.
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

	/**
	 * When in a creative tab, this adds a tooltip to an item indicating what dungeon it can be found in.
	 * @param stack The {@link ItemStack} with the tooltip.
	 * @param level The {@link Level} the item is rendered in.
	 * @param components A {@link List} of {@link Component}s making up this item's tooltip.
	 * @param flag A {@link TooltipFlag} for the tooltip type.
	 */
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
		super.appendHoverText(stack, level, components, flag);
		if (flag.isCreative()) {
			components.add(AetherItems.BRONZE_DUNGEON_TOOLTIP);
		}
	}
}
