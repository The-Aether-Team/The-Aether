package com.aetherteam.aether.Utils;

import com.aetherteam.aether.mixin.mixins.common.accessor.EntityAccessor;
import net.minecraft.world.entity.LivingEntity;

public class LivingEntityUtils {

    public static boolean isInFluidType(LivingEntity livingEntity){
        return ((EntityAccessor) livingEntity).getFluidHeight().size() > 0;
    }
}
