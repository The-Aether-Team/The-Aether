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

public class LoreBookItem extends Item {
    public LoreBookItem(Properties properties) {
        super(properties);
    }

    /**
     * Opens the Book of Lore screen using {@link LoreBookProvider}.
     * @param level The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand The {@link InteractionHand} in which the item is being used.
     * @return The super {@link InteractionResultHolder}.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new LoreBookProvider());
        }
        return super.use(level, player, hand);
    }
}
