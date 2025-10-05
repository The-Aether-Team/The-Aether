package com.aetherteam.aether.item.miscellaneous.bucket;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class SkyrootBucketWrapper extends FluidBucketWrapper {
    public SkyrootBucketWrapper(ItemStack container) {
        super(container);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return fluid.getFluid().is(AetherTags.Fluids.ALLOWED_BUCKET_PICKUP) || (ForgeMod.MILK_TYPE.isPresent() && fluid.getFluid().getFluidType() == ForgeMod.MILK_TYPE.get());
    }

    @Override
    protected void setFluid(FluidStack fluidStack) {
        if (fluidStack.isEmpty()) {
            this.container = new ItemStack(AetherItems.SKYROOT_BUCKET.get());
        } else {
            this.container = SkyrootBucketItem.swapBucketType(FluidUtil.getFilledBucket(fluidStack));
        }
    }
}
