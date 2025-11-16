package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.LevelEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @WrapOperation(method = "tickChildren", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void aetherFabric$levelTickEvents(ServerLevel instance, BooleanSupplier hasTimeLeft, Operation<Void> original) {
        LevelEvents.BEFORE.invoker().beforeTick(instance);

        original.call(instance, hasTimeLeft);

        LevelEvents.AFTER.invoker().afterTick(instance);
    }
}
