package com.aetherteam.aether.item.food;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.item.AetherConsumables;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.miscellaneous.ConsumableItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class GummySwetItem extends Item implements ConsumableItem {
    public GummySwetItem() {
        super(new Item.Properties().rarity(AetherItems.AETHER_LOOT).food(AetherFoods.GUMMY_SWET, AetherConsumables.FAST_FOOD));
    }

    /**
     * Checks if the Gummy Swet can be used either if the player is able to eat it according to {@link Player#canEat(boolean)} if it behaves as food, or if the player is missing health and isn't in creative if it behaves as a healing item.
     *
     * @param level  The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand   The {@link InteractionHand} in which the item is being used.
     * @return Consume (cause the item to bob down then up in hand) if the item is successfully used whether it be for eating or healing, or fail (do nothing) if those conditions aren't met.
     * This is an {@link InteractionResult InteractionResult&lt;ItemStack&gt;}.
     */
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.has(DataComponents.FOOD)) { // If AetherConfig.SERVER.healing_gummy_swets.get() is false.
            FoodProperties foodProperties = heldStack.get(DataComponents.FOOD);
            if (foodProperties != null && player.canEat(foodProperties.canAlwaysEat())) {
                player.startUsingItem(hand);
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.FAIL;
            }
        } else { // If AetherConfig.SERVER.healing_gummy_swets.get() is true.
            if (player.getHealth() < player.getMaxHealth() && !player.isCreative()) {
                player.startUsingItem(hand);
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.FAIL;
            }
        }
    }

    /**
     * Performs the {@link LivingEntity#eat(Level, ItemStack)} code if the Gummy Swet is edible/behaves as food.
     * Otherwise, it heals the player, and consumes the item using {@link ConsumableItem#consume(Item, ItemStack, LivingEntity)}.
     *
     * @param stack The {@link ItemStack} in use.
     * @param level The {@link Level} of the user.
     * @param user  The {@link LivingEntity} using the stack.
     * @return The used {@link ItemStack}.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (!AetherConfig.SERVER.healing_gummy_swets.get()) { // If AetherConfig.SERVER.healing_gummy_swets.get() is false.
            return super.finishUsingItem(stack, level, user);// Automatically handles the criteria trigger and stat awarding code.
        } else { // If AetherConfig.SERVER.healing_gummy_swets.get() is true.
            user.heal(user.getMaxHealth());
            this.consume(this, stack, user);
            return stack;
        }
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 16;
    }

    /**
     * @return A {@link Boolean} based on if the Gummy Swet heals or fills hunger. When the {@link AetherConfig.Server#healing_gummy_swets} config is false, this is true.
     * This is based on the difference of Gummy Swets being used to heal in b1.7.3 and being used for hunger in 1.2.5.
     */
}
