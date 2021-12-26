package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.item.accessories.cape.ColoredCapeItem;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockListener
{
    //TODO: See: CauldronInteraction
//    @SubscribeEvent
//    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//        Level world = event.getWorld();
//        Player player = event.getPlayer();
//        InteractionHand hand = event.getHand();
//        ItemStack heldStack = player.getItemInHand(hand);
//
//        BlockHitResult hitVec = event.getHitVec();
//        BlockPos pos = hitVec.getBlockPos();
//        BlockState blockState = world.getBlockState(pos);
//
//        if (blockState.getBlock() == Blocks.CAULDRON) {
//            CauldronBlock cauldron = (CauldronBlock) blockState.getBlock();
//            int waterLevel = blockState.getValue(BlockStateProperties.LEVEL_CAULDRON);
//
//            if (heldStack.getItem() == AetherItems.SKYROOT_WATER_BUCKET.get()) {
//                if (waterLevel < 3) {
//                    player.swing(hand);
//                    if (!world.isClientSide) {
//                        if (!player.getAbilities().instabuild) {
//                            player.setItemInHand(hand, new ItemStack(AetherItems.SKYROOT_BUCKET.get()));
//                        }
//
//                        player.awardStat(Stats.FILL_CAULDRON);
//                        cauldron.setWaterLevel(world, pos, blockState, 3);
//                        world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
//                    }
//                }
//            } else if (heldStack.getItem() == AetherItems.SKYROOT_BUCKET.get()) {
//                if (waterLevel == 3) {
//                    player.swing(hand);
//                    if (!world.isClientSide) {
//                        if (!player.getAbilities().instabuild) {
//                            heldStack.shrink(1);
//                            if (heldStack.isEmpty()) {
//                                player.setItemInHand(hand, new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()));
//                            } else if (!player.getInventory().add(new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()))) {
//                                player.drop(new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()), false);
//                            }
//                        }
//                        player.awardStat(Stats.USE_CAULDRON);
//                        cauldron.setWaterLevel(world, pos, blockState, 0);
//                        world.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
//                    }
//                }
//            } else if (heldStack.getItem() instanceof ColoredCapeItem) {
//                if (waterLevel > 0) {
//                    player.swing(hand);
//                    if (!world.isClientSide) {
//                        player.setItemInHand(hand, new ItemStack(AetherItems.WHITE_CAPE.get()));
//                        player.awardStat(Stats.CLEAN_ARMOR);
//                        cauldron.setWaterLevel(world, pos, blockState, waterLevel - 1);
//                    }
//                }
//            }
//        }
//    }

    @SubscribeEvent
    public static void onBucketRightClicked(FillBucketEvent event) {
        HitResult rayTraceResult = event.getTarget();
        if (rayTraceResult instanceof BlockHitResult)  {
            BlockHitResult hitVec = (BlockHitResult) rayTraceResult;
            BlockState blockState = event.getWorld().getBlockState(hitVec.getBlockPos());
            if (blockState.getBlock() == Blocks.CAULDRON && event.getEmptyBucket().getItem() == AetherItems.SKYROOT_WATER_BUCKET.get()) {
                event.setCanceled(true);
            }
        }
    }
}
