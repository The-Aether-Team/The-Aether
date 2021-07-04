package com.gildedgames.aether.common.item.miscellaneous.bucket;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class SkyrootBucketItem extends Item
{
    public SkyrootBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            Direction direction = blockraytraceresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos1, direction, itemstack)) {
                BlockState blockstate1 = worldIn.getBlockState(blockpos);
                if (blockstate1.getBlock() instanceof IBucketPickupHandler) {
                    Fluid fluid = ((IBucketPickupHandler)blockstate1.getBlock()).takeLiquid(worldIn, blockpos, blockstate1);
                    if (fluid == Fluids.WATER) {
                        SoundEvent soundevent = SoundEvents.BUCKET_FILL;
                        playerIn.playSound(soundevent, 1.0F, 1.0F);
                        ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, playerIn, new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()));
                        return ActionResult.sidedSuccess(itemstack1, worldIn.isClientSide());
                    }
                }
            }
        }
        return ActionResult.fail(itemstack);
    }
}
