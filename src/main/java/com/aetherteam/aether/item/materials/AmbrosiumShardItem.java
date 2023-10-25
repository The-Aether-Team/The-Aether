package com.aetherteam.aether.item.materials;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.materials.behavior.ItemUseConversion;
import com.aetherteam.aether.item.miscellaneous.ConsumableItem;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.block.AmbrosiumRecipe;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AmbrosiumShardItem extends Item implements ItemUseConversion<AmbrosiumRecipe>, ConsumableItem {
	public AmbrosiumShardItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult result = this.convertBlock(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get(), context);
		if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS) {
			context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), AetherSoundEvents.ITEM_AMBROSIUM_SHARD.get(), SoundSource.BLOCKS, 1.0F, 3.0F + (context.getLevel().getRandom().nextFloat() - context.getLevel().getRandom().nextFloat()) * 0.8F);
		}
		return result;
	}

	/**
	 * Checks if the player can begin using an Ambrosium Shard if it is edible (b1.7.3 behavior) and the player doesn't have full health (or is in creative mode).
	 * Modified based on {@link Item#use(Level, Player, InteractionHand)}'s default code.
	 * @param level The {@link Level} of the user.
	 * @param player The {@link Player} using this item.
	 * @param hand The {@link InteractionHand} in which the item is being used.
	 * @return Consume (cause the item to bob down then up in hand) if the Ambrosium Shard is edible or the player is missing health. Fail (do nothing) if the player has full health. Pass (do nothing) if the Ambrosium Shard isn't edible.
	 * This is an {@link InteractionResultHolder InteractionResultHolder&lt;ItemStack&gt;}.
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (AetherConfig.SERVER.edible_ambrosium.get()) {
			ItemStack itemStack = player.getItemInHand(hand);
			if (player.getHealth() < player.getMaxHealth() || player.isCreative()) {
				player.startUsingItem(hand);
				return InteractionResultHolder.consume(itemStack);
			} else {
				return InteractionResultHolder.fail(itemStack);
			}
		} else {
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}
	}

	/**
	 * Heals half of a heart when finished using if the Ambrosium Shard is edible according to {@link AetherConfig.Server#edible_ambrosium}.
	 * Then it consumes the item using {@link ConsumableItem#consume(Item, ItemStack, LivingEntity)}.
	 * @param stack The {@link ItemStack} in use.
	 * @param level The {@link Level} of the user.
	 * @param user The {@link LivingEntity} using the stack.
	 * @return The used {@link ItemStack}.
	 */
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
		if (AetherConfig.SERVER.edible_ambrosium.get()) {
			user.heal(1);
			this.consume(this, stack, user);
		}
		return stack;
	}

	/**
	 * @return The {@link UseAnim#EAT} animation if the Ambrosium Shard is edible according to {@link AetherConfig.Server#edible_ambrosium}, otherwise {@link UseAnim#NONE}.
	 */
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return AetherConfig.SERVER.edible_ambrosium.get() ? UseAnim.EAT : UseAnim.NONE;
	}

	/**
	 * @return A use duration of 16 as an {@link Integer} if the Ambrosium Shard is edible according to {@link AetherConfig.Server#edible_ambrosium}, otherwise 0.
	 */
	@Override
	public int getUseDuration(ItemStack stack) {
		return AetherConfig.SERVER.edible_ambrosium.get() ? 16 : 0;
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return 1600;
	}
}
