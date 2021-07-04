package com.gildedgames.aether.common.item.food;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GummySwetItem extends Item
{
	public GummySwetItem() {
		super(new Item.Properties().food(new Food.Builder().fast().nutrition(20).build()).tab(AetherItemGroups.AETHER_FOOD));
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		if (this.isEdible()) {
			if (playerEntity.canEat(this.getFoodProperties().canAlwaysEat())) {
				playerEntity.startUsingItem(hand);
				return ActionResult.consume(itemstack);
			} else {
				return ActionResult.fail(itemstack);
			}
		} else {
			if (playerEntity.getHealth() < playerEntity.getMaxHealth()) {
				playerEntity.startUsingItem(hand);
				return ActionResult.consume(itemstack);
			} else {
				return ActionResult.fail(itemstack);
			}
		}
	}

	public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity livingEntity) {
		if (this.isEdible()) {
			return livingEntity.eat(world, stack);
		} else {
			livingEntity.heal(livingEntity.getMaxHealth());
			if (livingEntity instanceof PlayerEntity && !((PlayerEntity) livingEntity).isCreative()) {
				stack.shrink(1);
			}
			return stack;
		}
	}

	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.EAT;
	}

	public int getUseDuration(ItemStack stack) {
		return 16;
	}

	public boolean isEdible() {
		return !AetherConfig.COMMON.healing_gummy_swets.get();
	}
}
