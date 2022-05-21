package com.gildedgames.aether.common.item.food;

import com.gildedgames.aether.common.registry.AetherFoods;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class GummySwetItem extends Item
{
	public GummySwetItem() {
		super(new Item.Properties().rarity(AetherItems.AETHER_LOOT).food(AetherFoods.GUMMY_SWET).tab(AetherItemGroups.AETHER_FOOD));
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		if (this.isEdible()) {
			if (playerEntity.canEat(this.getFoodProperties().canAlwaysEat())) {
				playerEntity.startUsingItem(hand);
				return InteractionResultHolder.consume(itemstack);
			} else {
				return InteractionResultHolder.fail(itemstack);
			}
		} else {
			if (playerEntity.getHealth() < playerEntity.getMaxHealth() && !playerEntity.isCreative()) {
				playerEntity.startUsingItem(hand);
				return InteractionResultHolder.consume(itemstack);
			} else {
				return InteractionResultHolder.fail(itemstack);
			}
		}
	}

	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity livingEntity) {
		if (this.isEdible()) {
			return livingEntity.eat(world, stack);
		} else {
			livingEntity.heal(livingEntity.getMaxHealth());
			if (livingEntity instanceof Player && !((Player) livingEntity).getAbilities().instabuild) {
				stack.shrink(1);
			}
			return stack;
		}
	}

	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.EAT;
	}

	public int getUseDuration(ItemStack stack) {
		return 16;
	}

	public boolean isEdible() {
		return !AetherConfig.COMMON.healing_gummy_swets.get();
	}
}
