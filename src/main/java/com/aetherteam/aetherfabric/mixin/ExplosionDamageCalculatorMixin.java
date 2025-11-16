package com.aetherteam.aetherfabric.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExplosionDamageCalculator.class)
public abstract class ExplosionDamageCalculatorMixin {
    @WrapOperation(method = "getBlockExplosionResistance", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getExplosionResistance()F"))
    private float aetherFabric$getAdjustedResistance(Block instance, Operation<Float> original, @Local(argsOnly = true) Explosion explosion, @Local(argsOnly = true) BlockGetter reader, @Local(argsOnly = true) BlockPos pos) {
        var betterResistance = reader.getBlockState(pos).aetherFabric$getExplosionResistance(reader, pos, explosion);

        return betterResistance != null ? betterResistance : original.call(instance);
    }
}
