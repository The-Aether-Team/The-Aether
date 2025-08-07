package com.aetherteam.aether.item.miscellaneous.bucket;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

public class SkyrootBucketWrapper extends FluidBucketWrapper {
    public SkyrootBucketWrapper(ItemStack container) {
        super(container);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return fluid.is(AetherTags.Fluids.ALLOWED_BUCKET_PICKUP) || (NeoForgeMod.MILK_TYPE.isBound() && fluid.getFluidType() == NeoForgeMod.MILK_TYPE.get());
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
