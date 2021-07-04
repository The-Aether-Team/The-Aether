package com.gildedgames.aether.common.item.food;

import com.gildedgames.aether.common.registry.AetherFoods;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class WhiteAppleItem extends Item
{
    public WhiteAppleItem() {
        super(new Item.Properties().food(AetherFoods.WHITE_APPLE).tab(AetherItemGroups.AETHER_FOOD));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide) entityLiving.curePotionEffects(new ItemStack(AetherItems.WHITE_APPLE.get()));
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            IAetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setRemedyTimer(300));
        }
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }
}
