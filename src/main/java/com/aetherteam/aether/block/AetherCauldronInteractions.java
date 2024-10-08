package com.aetherteam.aether.block;

import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class AetherCauldronInteractions {
    public static final CauldronInteraction FILL_WATER = (state, level, pos, player, hand, stack) ->
            emptySkyrootBucket(level, pos, player, hand, stack, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY);

    public static final CauldronInteraction FILL_POWDER_SNOW = (state, level, pos, player, hand, stack) ->
            emptySkyrootBucket(level, pos, player, hand, stack, Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY_POWDER_SNOW);

    public static final CauldronInteraction EMPTY_WATER = (state, level, pos, player, hand, stack) ->
            CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()), (blockState) ->
                    blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);

    public static final CauldronInteraction EMPTY_POWDER_SNOW = (state, level, pos, player, hand, stack) ->
            CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, new ItemStack(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get()), (blockState) ->
                    blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);

    /**
     * Cleans colored Capes into White Capes.
     */
    public static final CauldronInteraction CAPE = (state, level, pos, player, hand, stack) -> {
        if (!level.isClientSide()) {
            player.setItemInHand(hand, new ItemStack(AetherItems.WHITE_CAPE.get()));
            player.awardStat(Stats.CLEAN_ARMOR);
            LayeredCauldronBlock.lowerFillLevel(state, level, pos);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    };

    /**
     * Empties a filled Skyroot Bucket into an empty Skyroot Bucket.
     */
    private static ItemInteractionResult emptySkyrootBucket(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state, SoundEvent sound) {
        if (!level.isClientSide()) {
            Item item = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(AetherItems.SKYROOT_BUCKET.get())));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, state);
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }
}
