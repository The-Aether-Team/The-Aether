package com.gildedgames.aether.item.food;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.AetherConfig;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class GummySwetItem extends Item {
	public GummySwetItem() {
		super(new Item.Properties().rarity(AetherItems.AETHER_LOOT).food(AetherFoods.GUMMY_SWET).tab(AetherItemGroups.AETHER_FOOD));
	}

	/**
	 * Checks if the Gummy Swet can be used either if the player is able to eat it according to {@link Player#canEat(boolean)} if it behaves as food, or if the player is missing health and isn't in creative if it behaves as a healing item.
	 * @param level The {@link Level} of the user.
	 * @param player The {@link Player} using this item.
	 * @param hand The {@link InteractionHand} in which the item is being used.
	 * @return Consume (cause the item to bob down then up in hand) if the item is successfully used whether it be for eating or healing, or fail (do nothing) if those conditions aren't met.
	 */
	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
		ItemStack heldStack = player.getItemInHand(hand);
		if (this.isEdible()) { // If AetherConfig.COMMON.healing_gummy_swets.get() is false.
			FoodProperties foodProperties = this.getFoodProperties(heldStack, player);
			if (foodProperties != null && player.canEat(foodProperties.canAlwaysEat())) {
				player.startUsingItem(hand);
				return InteractionResultHolder.consume(heldStack);
			} else {
				return InteractionResultHolder.fail(heldStack);
			}
		} else { // If AetherConfig.COMMON.healing_gummy_swets.get() is true.
			if (player.getHealth() < player.getMaxHealth() && !player.isCreative()) {
				player.startUsingItem(hand);
				return InteractionResultHolder.consume(heldStack);
			} else {
				return InteractionResultHolder.fail(heldStack);
			}
		}
	}

	/**
	 * Performs the {@link LivingEntity#eat(Level, ItemStack)} code if the Gummy Swet is edible/behaves as food.
	 * Otherwise, it heals the player, and manually consumes the item and triggers the {@link CriteriaTriggers#CONSUME_ITEM} advancement criteria and gives the {@link Stats#ITEM_USED} stat to the player for the item.
	 * @param stack The {@link ItemStack} in use.
	 * @param level The {@link Level} of the user.
	 * @param user The {@link LivingEntity} using the stack.
	 * @return The used {@link ItemStack}.
	 */
	@Nonnull
	@Override
	public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity user) {
		if (this.isEdible()) { // If AetherConfig.COMMON.healing_gummy_swets.get() is false.
			return user.eat(level, stack); // Automatically handles the criteria trigger and stat awarding code.
		} else { // If AetherConfig.COMMON.healing_gummy_swets.get() is true.
			user.heal(user.getMaxHealth());
			if (user instanceof Player player) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				if (player instanceof ServerPlayer serverPlayer) {
					CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
					serverPlayer.awardStat(Stats.ITEM_USED.get(this));
				}
			}
			return stack;
		}
	}

	@Nonnull
	@Override
	public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
		return UseAnim.EAT;
	}

	@Override
	public int getUseDuration(@Nonnull ItemStack stack) {
		return 16;
	}

	/**
	 * @return A {@link Boolean} based on if the Gummy Swet heals or fills hunger. When the {@link AetherConfig.Common#healing_gummy_swets} config is false, this is true.
	 */
	@Override
	public boolean isEdible() {
		return !AetherConfig.COMMON.healing_gummy_swets.get();
	}
}
