package com.aetherteam.aether.item.miscellaneous.bucket;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SkyrootBucketItem extends BucketItem {
    /**
     * Map of replacements for vanilla buckets to Skyroot buckets.
     */
    public static final Map<Supplier<? extends Item>, Supplier<? extends Item>> REPLACEMENTS = new HashMap<>();

    public SkyrootBucketItem(Fluid supplier, Item.Properties properties) {
        super(supplier, properties);
    }

    /**
     * [CODE COPY] - {@link BucketItem#use(Level, Player, InteractionHand)}.<br><br>
     * Blocks that can be picked up depends on {@link AetherTags.Blocks#ALLOWED_BUCKET_PICKUP} or {@link AetherTags.Fluids#ALLOWED_BUCKET_PICKUP},
     * and the method will also swap out any returned vanilla buckets from interactions with Skyroot Buckets using {@link SkyrootBucketItem#swapBucketType(ItemStack)}.
     *
     * @param level  The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand   The {@link InteractionHand} in which the item is being used.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        BlockHitResult blockhitResult = getPlayerPOVHitResult(level, player, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        if (blockhitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(heldStack);
        } else if (blockhitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(heldStack);
        } else {
            BlockPos blockPos = blockhitResult.getBlockPos();
            Direction direction = blockhitResult.getDirection();
            BlockPos relativePos = blockPos.relative(direction);
            if (level.mayInteract(player, blockPos) && player.mayUseItemAt(relativePos, direction, heldStack)) {
                if (this.content == Fluids.EMPTY) {
                    BlockState blockState = level.getBlockState(blockPos);
                    FluidState fluidState = level.getFluidState(blockPos);
                    if (blockState.getBlock() instanceof BucketPickup bucketPickup && (blockState.is(AetherTags.Blocks.ALLOWED_BUCKET_PICKUP) || fluidState.is(AetherTags.Fluids.ALLOWED_BUCKET_PICKUP))) {
                        ItemStack bucketStack = bucketPickup.pickupBlock(player, level, blockPos, blockState);
                        bucketStack = swapBucketType(bucketStack);
                        if (!bucketStack.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            bucketPickup.getPickupSound(blockState).ifPresent((soundEvent) -> player.playSound(soundEvent, 1.0F, 1.0F));
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
                            ItemStack resultStack = ItemUtils.createFilledResult(heldStack, player, bucketStack);
                            if (!level.isClientSide()) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketStack);
                            }
                            return InteractionResultHolder.sidedSuccess(resultStack, level.isClientSide());
                        }
                    }
                    return InteractionResultHolder.fail(heldStack);
                } else {
                    BlockState blockState = level.getBlockState(blockPos);
                    BlockPos newPos = canBlockContainFluid(player, level, blockPos, blockState) ? blockPos : relativePos;
                    if (this.emptyContents(player, level, newPos, blockhitResult, heldStack)) {
                        this.checkExtraContent(player, level, heldStack, newPos);
                        if (player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, newPos, heldStack);
                        }
                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(heldStack, player), level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(heldStack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(heldStack);
            }
        }
    }

    /**
     * Swaps a given bucket with a replacement using {@link SkyrootBucketItem#REPLACEMENTS}.
     *
     * @param filledStack The given bucket as an {@link ItemStack}
     * @return The replacement bucket as an {@link ItemStack}.
     */
    public static ItemStack swapBucketType(ItemStack filledStack) {
        Supplier<? extends Item> filledItem = filledStack::getItem;
        for (Map.Entry<Supplier<? extends Item>, Supplier<? extends Item>> entry : REPLACEMENTS.entrySet()) {
            if (filledItem.get() == entry.getKey().get()) {
                Item replacedItem = entry.getValue().get();
                ItemStack newStack = new ItemStack(replacedItem.builtInRegistryHolder(), 1, filledStack.getComponentsPatch());
                return newStack;
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * [CODE COPY] - {@link BucketItem#getEmptySuccessItem(ItemStack, Player)}.<br><br>
     * Returns a Skyroot Bucket instead of a vanilla bucket.
     */
    public static ItemStack getEmptySuccessItem(ItemStack bucketStack, Player player) {
        return !player.getAbilities().instabuild ? new ItemStack(AetherItems.SKYROOT_BUCKET.get()) : bucketStack;
    }

    /**
     * [CODE COPY] - {@link BucketItem#canBlockContainFluid(Player, Level, BlockPos, BlockState)}.
     */
    protected boolean canBlockContainFluid(Player player, Level level, BlockPos pos, BlockState state) {
        return state.getBlock() instanceof LiquidBlockContainer liquidBlockContainer && liquidBlockContainer.canPlaceLiquid(player, level, pos, state, this.content);
    }
}
