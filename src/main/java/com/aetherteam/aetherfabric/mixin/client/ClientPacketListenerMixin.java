package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.client.ClientDimUtils;
import com.aetherteam.aetherfabric.client.events.ClientPlayerEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    @Shadow
    public abstract Connection getConnection();

    @Shadow
    public abstract RegistryAccess.Frozen registryAccess();

    @WrapOperation(method = "method_38542", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundBlockEntityDataPacket;getTag()Lnet/minecraft/nbt/CompoundTag;"))
    private CompoundTag aetherFabric$adjustLoadCall(ClientboundBlockEntityDataPacket instance, Operation<CompoundTag> original, @Local(argsOnly = true) BlockEntity blockEntity) {
        return !blockEntity.aetherFabric$onDataPacket(this.getConnection(), instance, this.registryAccess()) ? original.call(instance) : new CompoundTag();
    }

    @Inject(method = "handleRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addEntity(Lnet/minecraft/world/entity/Entity;)V"))
    private void aetherFabric$onPlayerClone(ClientboundRespawnPacket packet, CallbackInfo ci, @Local(ordinal = 0) LocalPlayer oldPlayer, @Local(ordinal = 1) LocalPlayer newPlayer) {
        ClientPlayerEvents.ON_RESPAWN.invoker().respawning(oldPlayer, newPlayer);
    }

    //--

    @Unique
    private final ThreadLocal<@Nullable Pair<@Nullable ResourceKey<Level>, @Nullable ResourceKey<Level>>> aetherFabric$storedLevels = ThreadLocal.withInitial(() -> null);

    @Inject(method = "handleRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;startWaitingForNewLevel(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/gui/screens/ReceivingLevelScreen$Reason;)V"))
    private void aetherFabric$storeLevelData(ClientboundRespawnPacket packet, CallbackInfo ci, @Local(ordinal = 0) LocalPlayer localPlayer, @Local(ordinal = 0) ResourceKey<Level> resourceKey, @Local(ordinal = 1) ResourceKey<Level> resourceKey2){
        this.aetherFabric$storedLevels.set(Pair.of(localPlayer.isDeadOrDying() ? null : resourceKey, localPlayer.isDeadOrDying() ? null : resourceKey2));
    }

    @WrapOperation(method = "startWaitingForNewLevel", at = @At(value = "NEW", target = "(Ljava/util/function/BooleanSupplier;Lnet/minecraft/client/gui/screens/ReceivingLevelScreen$Reason;)Lnet/minecraft/client/gui/screens/ReceivingLevelScreen;"))
    private ReceivingLevelScreen aetherFabric$adjustDimTransition(BooleanSupplier levelReceived, ReceivingLevelScreen.Reason reason, Operation<ReceivingLevelScreen> original) {
        var dims = aetherFabric$storedLevels.get();

        aetherFabric$storedLevels.remove();

        if (dims == null) dims = Pair.of(null, null);

        var alternativeConstructor = ClientDimUtils.getScreen(dims.getFirst(), dims.getSecond());

        return alternativeConstructor != null ? alternativeConstructor.create(levelReceived, reason) : original.call(levelReceived, reason);
    }
}
