package com.gildedgames.aether.common.item.misc;

import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class SkyrootBucketItem extends Item {
    public SkyrootBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.resultPass(itemstack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            Direction direction = blockraytraceresult.getFace();
            BlockPos blockpos1 = blockpos.offset(direction);
            if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos1, direction, itemstack)) {
                BlockState blockstate1 = worldIn.getBlockState(blockpos);
                if (blockstate1.getBlock() instanceof IBucketPickupHandler) {
                    Fluid fluid = ((IBucketPickupHandler)blockstate1.getBlock()).pickupFluid(worldIn, blockpos, blockstate1);
                    if (fluid == Fluids.WATER) {
                        SoundEvent soundevent = SoundEvents.ITEM_BUCKET_FILL;
                        playerIn.playSound(soundevent, 1.0F, 1.0F);
                        ItemStack itemstack1 = DrinkHelper.fill(itemstack, playerIn, new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()));
                        return ActionResult.func_233538_a_(itemstack1, worldIn.isRemote());
                    }
                }
            }
        }
        return ActionResult.resultFail(itemstack);
    }

    /**
     * This method is used to milk a cow.
     */
    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if((target instanceof FlyingCowEntity || target instanceof CowEntity) && !target.isChild()) {
            playerIn.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.fill(stack, playerIn, AetherItems.SKYROOT_MILK_BUCKET.get().getDefaultInstance());
            playerIn.setHeldItem(hand, itemstack1);
            return ActionResultType.func_233537_a_(target.world.isRemote);
        }
        return ActionResultType.PASS;
    }
}
