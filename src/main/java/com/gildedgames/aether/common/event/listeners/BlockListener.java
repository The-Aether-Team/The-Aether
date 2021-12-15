package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.item.accessories.cape.ColoredCapeItem;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockListener
{
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        Hand hand = event.getHand();
        ItemStack heldStack = player.getItemInHand(hand);

        BlockRayTraceResult hitVec = event.getHitVec();
        BlockPos pos = hitVec.getBlockPos();
        BlockState blockState = world.getBlockState(pos);

        if (blockState.getBlock() == Blocks.CAULDRON) {
            CauldronBlock cauldron = (CauldronBlock) blockState.getBlock();
            int waterLevel = blockState.getValue(BlockStateProperties.LEVEL_CAULDRON);

            if (heldStack.getItem() == AetherItems.SKYROOT_WATER_BUCKET.get()) {
                if (waterLevel < 3) {
                    player.swing(hand);
                    if (!world.isClientSide) {
                        if (!player.abilities.instabuild) {
                            player.setItemInHand(hand, new ItemStack(AetherItems.SKYROOT_BUCKET.get()));
                        }

                        player.awardStat(Stats.FILL_CAULDRON);
                        cauldron.setWaterLevel(world, pos, blockState, 3);
                        world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            } else if (heldStack.getItem() == AetherItems.SKYROOT_BUCKET.get()) {
                if (waterLevel == 3) {
                    player.swing(hand);
                    if (!world.isClientSide) {
                        if (!player.abilities.instabuild) {
                            heldStack.shrink(1);
                            if (heldStack.isEmpty()) {
                                player.setItemInHand(hand, new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()));
                            } else if (!player.inventory.add(new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()))) {
                                player.drop(new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()), false);
                            }
                        }
                        player.awardStat(Stats.USE_CAULDRON);
                        cauldron.setWaterLevel(world, pos, blockState, 0);
                        world.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            } else if (heldStack.getItem() instanceof ColoredCapeItem) {
                if (waterLevel > 0) {
                    player.swing(hand);
                    if (!world.isClientSide) {
                        player.setItemInHand(hand, new ItemStack(AetherItems.WHITE_CAPE.get()));
                        player.awardStat(Stats.CLEAN_ARMOR);
                        cauldron.setWaterLevel(world, pos, blockState, waterLevel - 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBucketRightClicked(FillBucketEvent event) {
        RayTraceResult rayTraceResult = event.getTarget();
        if (rayTraceResult instanceof BlockRayTraceResult)  {
            BlockRayTraceResult hitVec = (BlockRayTraceResult) rayTraceResult;
            BlockState blockState = event.getWorld().getBlockState(hitVec.getBlockPos());
            if (blockState.getBlock() == Blocks.CAULDRON && event.getEmptyBucket().getItem() == AetherItems.SKYROOT_WATER_BUCKET.get()) {
                event.setCanceled(true);
            }
        }
    }
}
