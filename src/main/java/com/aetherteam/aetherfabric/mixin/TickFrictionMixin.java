package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.BlockStateExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {ExperienceOrb.class, ItemEntity.class})
public abstract class TickFrictionMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getFriction()F"))
    private float aetherFabric$adjustFriction(Block instance, Operation<Float> original) {
        return BlockStateExtension.aetherFabric$getFriction((Entity) (Object) this, () -> original.call(instance));
    }
}
