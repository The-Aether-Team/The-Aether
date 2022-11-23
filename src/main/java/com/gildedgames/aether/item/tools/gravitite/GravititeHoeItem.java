package com.gildedgames.aether.item.tools.gravitite;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.GravititeTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GravititeHoeItem extends HoeItem implements GravititeTool {
    public GravititeHoeItem() {
        super(AetherItemTiers.GRAVITITE, -3, 0, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }

    /**
     * Interaction code to prioritize the ability at {@link GravititeTool#floatBlock(Level, BlockPos, ItemStack, BlockState, Player, InteractionHand)} over the normal tool interactions.
     * @param context The {@link UseOnContext} of the tool interaction.
     * @return The {@link InteractionResult} of the interaction, which returns the result of {@link HoeItem#useOn(UseOnContext)} if the ability fails and a {@link InteractionResult#sidedSuccess(boolean)} if it doesn't.
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        if (!this.floatBlock(level, blockPos, itemStack, blockState, player, hand)) {
            return super.useOn(context);
        } else {
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
    }
}
