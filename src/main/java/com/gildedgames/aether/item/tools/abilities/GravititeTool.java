package com.gildedgames.aether.item.tools.abilities;

import com.gildedgames.aether.entity.block.FloatingBlockEntity;
import com.gildedgames.aether.AetherTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface GravititeTool {
    static boolean floatBlock(Level level, BlockPos pos, ItemStack stack, BlockState state, Player player, InteractionHand hand) {
        if (stack.is(AetherTags.Items.GRAVITITE_TOOLS) && stack.getItem() instanceof TieredItem tieredItem) {
            if (player != null && !player.isShiftKeyDown()) {
                float destroySpeed = stack.getDestroySpeed(state);
                float efficiency = tieredItem.getTier().getSpeed();
                if ((destroySpeed == efficiency || stack.isCorrectToolForDrops(state)) && level.isEmptyBlock(pos.above())) {
                    if (level.getBlockEntity(pos) == null && state.getDestroySpeed(level, pos) >= 0.0F && !state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && !state.is(AetherTags.Blocks.GRAVITITE_ABILITY_BLACKLIST)) {
                        if (!level.isClientSide) {
                            FloatingBlockEntity entity = new FloatingBlockEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, state);
                            if (state.is(BlockTags.ANVIL)) {
                                entity.setHurtsEntities(2.0F, 40);
                            }
                            level.addFreshEntity(entity);
                            stack.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
                        } else {
                            player.swing(hand);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
