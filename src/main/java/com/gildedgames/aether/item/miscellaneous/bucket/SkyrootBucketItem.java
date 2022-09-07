package com.gildedgames.aether.item.miscellaneous.bucket;

import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.AetherTags;
import com.google.common.collect.Maps;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SkyrootBucketItem extends BucketItem {
    public static Map<Holder<Item>, Supplier<Item>> REPLACEMENTS = new HashMap<>();

    public SkyrootBucketItem(Supplier<? extends Fluid> supplier, Item.Properties properties) {
        super(supplier, properties);
        REPLACEMENTS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(Items.WATER_BUCKET), AetherItems.SKYROOT_WATER_BUCKET);
        REPLACEMENTS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(Items.POWDER_SNOW_BUCKET), AetherItems.SKYROOT_POWDER_SNOW_BUCKET);
        REPLACEMENTS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(Items.COD_BUCKET), AetherItems.SKYROOT_COD_BUCKET);
        REPLACEMENTS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(Items.SALMON_BUCKET), AetherItems.SKYROOT_SALMON_BUCKET);
        REPLACEMENTS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(Items.PUFFERFISH_BUCKET), AetherItems.SKYROOT_PUFFERFISH_BUCKET);
        REPLACEMENTS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(Items.TROPICAL_FISH_BUCKET), AetherItems.SKYROOT_TROPICAL_FISH_BUCKET);
        REPLACEMENTS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(Items.AXOLOTL_BUCKET), AetherItems.SKYROOT_AXOLOTL_BUCKET);
        REPLACEMENTS.put(ForgeRegistries.ITEMS.getDelegateOrThrow(Items.TADPOLE_BUCKET), AetherItems.SKYROOT_TADPOLE_BUCKET);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, this.getFluid() == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> interactionResult = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, level, itemStack, blockhitresult);
        if (interactionResult != null) return interactionResult;
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            BlockPos blockPos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos relativePos = blockPos.relative(direction);
            if (level.mayInteract(player, blockPos) && player.mayUseItemAt(relativePos, direction, itemStack)) {
                if (this.getFluid() == Fluids.EMPTY) {
                    BlockState blockState = level.getBlockState(blockPos);
                    FluidState fluidState = level.getFluidState(blockPos);
                    if (blockState.getBlock() instanceof BucketPickup bucketPickup && (blockState.is(AetherTags.Blocks.ALLOWED_BUCKET_PICKUP) || fluidState.is(AetherTags.Fluids.ALLOWED_BUCKET_PICKUP))) {
                        ItemStack bucketStack = bucketPickup.pickupBlock(level, blockPos, blockState);
                        bucketStack = swapBucketType(bucketStack);
                        if (!bucketStack.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            bucketPickup.getPickupSound(blockState).ifPresent((soundEvent) -> player.playSound(soundEvent, 1.0F, 1.0F));
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
                            ItemStack resultStack = ItemUtils.createFilledResult(itemStack, player, bucketStack);
                            if (!level.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, bucketStack);
                            }
                            return InteractionResultHolder.sidedSuccess(resultStack, level.isClientSide());
                        }
                    }
                    return InteractionResultHolder.fail(itemStack);
                } else {
                    BlockState blockstate = level.getBlockState(blockPos);
                    BlockPos newPos = canBlockContainFluid(level, blockPos, blockstate) ? blockPos : relativePos;
                    if (this.emptyContents(player, level, newPos, blockhitresult)) {
                        this.checkExtraContent(player, level, itemStack, newPos);
                        if (player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, newPos, itemStack);
                        }
                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemStack, player), level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(itemStack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemStack);
            }
        }
    }

    public static ItemStack swapBucketType(ItemStack filledStack) {
        Supplier<Item> filledItem = ForgeRegistries.ITEMS.getDelegateOrThrow(filledStack.getItem());
        if (REPLACEMENTS.containsKey(filledItem)) {
            Item replacedItem = REPLACEMENTS.get(filledItem).get();
            ItemStack newStack = new ItemStack(replacedItem);
            newStack.setTag(filledStack.getTag());
            return newStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Nonnull
    public static ItemStack getEmptySuccessItem(@Nonnull ItemStack bucketStack, Player player) {
        return !player.getAbilities().instabuild ? new ItemStack(AetherItems.SKYROOT_BUCKET.get()) : bucketStack;
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
        return null;
    }

    private boolean canBlockContainFluid(Level level, BlockPos pos, BlockState state) {
        return state.getBlock() instanceof LiquidBlockContainer liquidBlockContainer && liquidBlockContainer.canPlaceLiquid(level, pos, state, this.getFluid());
    }
}
