package com.aetherteam.aetherfabric.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Boat.class)
public abstract class BoatMixin {
    @WrapOperation(method = "getGroundFriction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getFriction()F"))
    private float aetherFabric$adjustFriction(Block instance, Operation<Float> original, @Local() BlockPos.MutableBlockPos blockPos, @Local() BlockState blockState) {
        var livingEntity = (Entity) (Object) this;

        var betterFriction = blockState.aetherFabric$getFriction(livingEntity.level(), blockPos, livingEntity);

        return betterFriction != null ? betterFriction : original.call(instance);
    }
}
