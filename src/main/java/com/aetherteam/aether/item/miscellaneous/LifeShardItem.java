package com.aetherteam.aether.item.miscellaneous;

import com.aetherteam.aether.capability.INBTSynchable;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class LifeShardItem extends Item implements ConsumableItem {
    public LifeShardItem(Properties properties) {
        super(properties);
    }

    /**
     * Consumes the Life Shard as long as the currently used Life Shard count is below the limit, otherwise a message will be displayed to let the player know they can't consume any more.
     * @param level The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand The {@link InteractionHand} in which the item is being used.
     * @return A success on client and consume on server (based on {@link InteractionResult#sidedSuccess(boolean)}) if the Life Shard can be consumed, otherwise pass is returned.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!player.isCreative()) {
            Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
            if (aetherPlayerOptional.isPresent()) {
                AetherPlayer aetherPlayer = aetherPlayerOptional.get();
                if (aetherPlayer.getLifeShardCount() < aetherPlayer.getLifeShardLimit()) {
                    player.swing(hand);
                    if (!level.isClientSide()) {
                        this.consume(this, heldStack, player);
                        aetherPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setLifeShardCount", aetherPlayer.getLifeShardCount() + 1);
                        return InteractionResultHolder.consume(heldStack);
                    } else {
                        return InteractionResultHolder.success(heldStack);
                    }
                } else if (aetherPlayer.getLifeShardCount() >= aetherPlayer.getLifeShardLimit()) {
                    player.displayClientMessage(Component.translatable("aether.life_shard_limit", aetherPlayer.getLifeShardLimit()), true);
                }
            }
        }
        return InteractionResultHolder.pass(heldStack);
    }

    /**
     * When in a creative tab, this adds a tooltip to an item indicating what dungeon it can be found in.
     * @param stack The {@link ItemStack} with the tooltip.
     * @param level The {@link Level} the item is rendered in.
     * @param components A {@link List} of {@link Component}s making up this item's tooltip.
     * @param flag A {@link TooltipFlag} for the tooltip type.
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (flag.isCreative()) {
            components.add(AetherItems.GOLD_DUNGEON_TOOLTIP);
        }
    }
}
