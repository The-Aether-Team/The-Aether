package com.gildedgames.aether.item.miscellaneous;

import com.gildedgames.aether.inventory.provider.LoreBookProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

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
