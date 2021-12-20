package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.common.inventory.provider.LoreBookProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.world.item.Item.Properties;

public class LoreBookItem extends Item
{
    public LoreBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide()) {
            NetworkHooks.openGui((ServerPlayer) playerIn, new LoreBookProvider());
        }
        return super.use(worldIn, playerIn, handIn);
    }
}
