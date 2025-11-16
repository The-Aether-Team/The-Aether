package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aetherfabric.entity.IEntityWithComplexSpawn;
import com.aetherteam.aetherfabric.events.CancellableCallbackImpl;
import com.aetherteam.aetherfabric.events.EntityEvents;
import com.aetherteam.aetherfabric.events.EntityTickEvents;
import com.aetherteam.aetherfabric.network.payload.AdvancedAddEntityPayload;
import com.aetherteam.aetherfabric.pond.EntityExtension;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.DimensionTransition;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityExtension {
    @Shadow
    protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @WrapOperation(method = "rideTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
    private void aetherFabric$entityTickEvents(Entity instance, Operation<Void> original) {
        var shouldCancelEvent = new MutableBoolean(false);

        EntityTickEvents.BEFORE.invoker().beforeTick(instance, shouldCancelEvent);

        if (shouldCancelEvent.getValue()) return;

        original.call(instance);

        EntityTickEvents.AFTER.invoker().afterTick(instance);
    }

    @Inject(method = "changeDimension", at = @At("HEAD"))
    private void aetherFabric$beforeDimensionChange(DimensionTransition transition, CallbackInfoReturnable<Entity> cir) {
        EntityEvents.BEFORE_DIMENSION_CHANGE.invoker().beforeChange((Entity) (Object) this, transition.newLevel().dimension());
    }

    @Override
    public boolean aetherFabric$isInFluidType() {
        for (var value : this.fluidHeight.values()) {
            if (value > 0.0) return true;
        }

        return false;
    }

    @Definition(id = "vehicle", field = "Lnet/minecraft/world/entity/Entity;vehicle:Lnet/minecraft/world/entity/Entity;")
    @Expression("this.vehicle = null")
    @Inject(method = "removeVehicle", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.BEFORE), cancellable = true)
    private void aetherFabric$entityMountEvent_remove(CallbackInfo ci) {
        if (shouldPerformAction(((Entity) (Object) this), false)) return;

        ci.cancel();
    }

    @Inject(method = "startRiding(Lnet/minecraft/world/entity/Entity;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;canRide(Lnet/minecraft/world/entity/Entity;)Z"), cancellable = true)
    private void aetherFabric$entityMountEvent_add(Entity vehicle, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (shouldPerformAction(((Entity) (Object) this), false)) return;

        cir.setReturnValue(false);
    }

    @Unique
    private static boolean shouldPerformAction(Entity entityMounting, boolean isDismounting) {
        var callback = new CancellableCallbackImpl();

        EntityEvents.ENTITY_MOUNT.invoker().onMount(entityMounting, entityMounting.getVehicle(), isDismounting, callback);

        if (!callback.isCanceled()) return true;

        entityMounting.absMoveTo(entityMounting.getX(), entityMounting.getY(), entityMounting.getZ(), entityMounting.yRotO, entityMounting.xRotO);

        return false;
    }

    @Override
    public void aetherFabric$sendPairingData(ServerPlayer serverPlayer, Consumer<CustomPacketPayload> bundleBuilder) {
        if (this instanceof IEntityWithComplexSpawn) {
            bundleBuilder.accept(new AdvancedAddEntityPayload((Entity)(Object) this));
        }
    }

    @Inject(method = "spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private void aetherFabric$captureDroppedStack(ItemStack stack, float offsetY, CallbackInfoReturnable<ItemEntity> cir, @Local() ItemEntity itemEntity) {
        if ((Entity) (Object) this instanceof Player player) {
            itemEntity.getAttachedOrCreate(AetherDataAttachments.DROPPED_ITEM).setOwner(player);
        }
    }
}
