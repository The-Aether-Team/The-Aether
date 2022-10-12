package com.gildedgames.aether.block;

import com.gildedgames.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class AetherCauldronInteractions
{
    public static final CauldronInteraction FILL_WATER = (p_175683_, p_175684_, p_175685_, p_175686_, p_175687_, p_175688_) ->
            emptySkyrootBucket(p_175684_, p_175685_, p_175686_, p_175687_, p_175688_, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY);

    public static final CauldronInteraction FILL_POWDER_SNOW = (p_175669_, p_175670_, p_175671_, p_175672_, p_175673_, p_175674_) ->
            emptySkyrootBucket(p_175670_, p_175671_, p_175672_, p_175673_, p_175674_, Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY_POWDER_SNOW);

    public static final CauldronInteraction EMPTY_WATER = (p_175725_, p_175726_, p_175727_, p_175728_, p_175729_, p_175730_) ->
            CauldronInteraction.fillBucket(p_175725_, p_175726_, p_175727_, p_175728_, p_175729_, p_175730_, new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()), (p_175660_) ->
                    p_175660_.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);

    public static final CauldronInteraction EMPTY_POWDER_SNOW = (p_175725_, p_175726_, p_175727_, p_175728_, p_175729_, p_175730_) ->
            CauldronInteraction.fillBucket(p_175725_, p_175726_, p_175727_, p_175728_, p_175729_, p_175730_, new ItemStack(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get()), (p_175660_) ->
                    p_175660_.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);

    public static final CauldronInteraction CAPE = (pState, pLevel, pPos, pPlayer, pHand, pStack) -> {
        if (!pLevel.isClientSide) {
            pPlayer.setItemInHand(pHand, new ItemStack(AetherItems.WHITE_CAPE.get()));
            pPlayer.awardStat(Stats.CLEAN_ARMOR);
            LayeredCauldronBlock.lowerFillLevel(pState, pLevel, pPos);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    };

    private static InteractionResult emptySkyrootBucket(Level p_175619_, BlockPos p_175620_, Player p_175621_, InteractionHand p_175622_, ItemStack p_175623_, BlockState p_175624_, SoundEvent p_175625_) {
        if (!p_175619_.isClientSide) {
            Item item = p_175623_.getItem();
            p_175621_.setItemInHand(p_175622_, ItemUtils.createFilledResult(p_175623_, p_175621_, new ItemStack(AetherItems.SKYROOT_BUCKET.get())));
            p_175621_.awardStat(Stats.FILL_CAULDRON);
            p_175621_.awardStat(Stats.ITEM_USED.get(item));
            p_175619_.setBlockAndUpdate(p_175620_, p_175624_);
            p_175619_.playSound(null, p_175620_, p_175625_, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_175619_.gameEvent(null, GameEvent.FLUID_PLACE, p_175620_);
        }

        return InteractionResult.sidedSuccess(p_175619_.isClientSide);
    }
}
