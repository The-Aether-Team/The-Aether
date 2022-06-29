package com.gildedgames.aether.item.food;

import com.gildedgames.aether.item.AetherFoods;
import com.gildedgames.aether.item.AetherItemGroups;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

public class HealingStoneItem extends Item
{
    public HealingStoneItem() {
        super(new Item.Properties().rarity(Rarity.RARE).food(AetherFoods.HEALING_STONE).tab(AetherItemGroups.AETHER_FOOD));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer) entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }
        return super.finishUsingItem(stack, world, entityLiving);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
