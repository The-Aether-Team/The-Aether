package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aether.event.listeners.DimensionListener;
import com.aetherteam.aetherfabric.events.BlockEvents;
import com.aetherteam.aetherfabric.events.CancellableCallbackImpl;
import com.aetherteam.aetherfabric.events.EntityTickEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableLong;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
    protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @WrapOperation(method = "tickNonPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
    private void aetherFabric$entityTickEvents(Entity instance, Operation<Void> original) {
        var shouldCancelEvent = new MutableBoolean(false);

        EntityTickEvents.BEFORE.invoker().beforeTick(instance, shouldCancelEvent);

        if (shouldCancelEvent.getValue()) return;

        original.call(instance);
        EntityTickEvents.AFTER.invoker().afterTick(instance);
    }

    @Inject(method = "updateNeighborsAt", at = @At("HEAD"))
    private void aetherFabric$runUpdateEvent(BlockPos pos, Block block, CallbackInfo ci){
        var level = (Level) (Object) this;

        BlockEvents.NEIGHBOR_UPDATE.invoker().onNeighborUpdate(level, pos, level.getBlockState(pos), EnumSet.allOf(Direction.class), false, new CancellableCallbackImpl(false));
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V"))
    private void aetherFabric$adjustSleepTime(ServerLevel instance, long time, Operation<Void> original) {
        var minTime = this.getDayTime();
        var newTime = new MutableLong(time);

        DimensionListener.onSleepFinish(instance, newTime, newTimeIn -> {
            if (minTime > newTimeIn) return false;
            newTime.setValue(newTimeIn);
            return true;
        });

        original.call(instance, newTime.getValue());
    }
}
