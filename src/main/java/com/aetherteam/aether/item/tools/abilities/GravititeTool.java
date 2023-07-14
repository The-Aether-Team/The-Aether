package com.aetherteam.aether.item.tools.abilities;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.miscellaneous.FloatingBlock;
import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface GravititeTool {
    /**
     * Floats a block by spawning a floating entity version at its position and removing the block.<br><br>
     * This occurs if the player isn't holding shift, if the tool can harvest the block, if the block has space above it, if it isn't a block entity, if the block is a singular block that's not in the {@link AetherTags.Blocks#GRAVITITE_ABILITY_BLACKLIST} tag, and if the call isn't clientside.<br><br>
     * This damages the tool for 4 durability. There is also behavior to allow the floating block to damage entities if it is an Anvil.
     * @param level The {@link Level} of the block.
     * @param pos The {@link BlockPos} of the block.
     * @param stack The {@link ItemStack} of the tool.
     * @param state The {@link BlockState} of the block.
     * @param player The {@link Player} using the tool.
     * @param hand The {@link InteractionHand} the tool was used in.
     * @return Whether the block was successfully floated or not, as a {@link Boolean}.
     */
    default boolean floatBlock(Level level, BlockPos pos, ItemStack stack, BlockState state, Player player, InteractionHand hand) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            if (player != null && !player.isShiftKeyDown()) {
                if ((stack.getDestroySpeed(state) == tieredItem.getTier().getSpeed() || stack.isCorrectToolForDrops(state)) && FloatingBlock.isFree(level.getBlockState(pos.above()))) {
                    if (level.getBlockEntity(pos) == null && state.getDestroySpeed(level, pos) >= 0.0F && !state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && !state.is(AetherTags.Blocks.GRAVITITE_ABILITY_BLACKLIST)) {
                        if (!level.isClientSide()) {
                            FloatingBlockEntity entity = new FloatingBlockEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, state);
                            entity.setNatural(false);
                            if (state.is(BlockTags.ANVIL)) {
                                entity.setHurtsEntities(2.0F, 40);
                            }
                            level.addFreshEntity(entity);
                            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
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
