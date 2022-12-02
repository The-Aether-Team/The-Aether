package com.gildedgames.aether.item.miscellaneous;

import com.gildedgames.aether.inventory.menu.LoreBookMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
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
     * Opens the Book of Lore screen.
     * @param level The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand The {@link InteractionHand} in which the item is being used.
     * @return The super {@link InteractionResultHolder}.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((id, inventory, playerEntity) -> new LoreBookMenu(id, inventory), Component.translatable("menu.aether.book_of_lore")));
        }
        return super.use(level, player, hand);
    }
}
