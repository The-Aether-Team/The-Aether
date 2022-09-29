package com.gildedgames.aether.item.food;

import com.gildedgames.aether.item.AetherItemGroups;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

public class HealingStoneItem extends Item {
    public HealingStoneItem() {
        super(new Item.Properties().rarity(Rarity.RARE).food(AetherFoods.HEALING_STONE).tab(AetherItemGroups.AETHER_FOOD));
    }

    /**
     * Triggers the {@link CriteriaTriggers#CONSUME_ITEM} advancement criteria and gives the {@link Stats#ITEM_USED} stat to the player for the item.
     * @param stack The {@link ItemStack} in use.
     * @param level The {@link Level} of the user.
     * @param user The {@link LivingEntity} using the stack.
     * @return The used {@link ItemStack}.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        return super.finishUsingItem(stack, level, user);
    }

    /**
     * @param stack The {@link ItemStack}.
     * @return Whether the item should render an enchantment glint, as a {@link Boolean}.
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
