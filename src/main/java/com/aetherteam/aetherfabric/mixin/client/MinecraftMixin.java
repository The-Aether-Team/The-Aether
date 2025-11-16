package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.client.ClientDimUtils;
import com.aetherteam.aetherfabric.events.AddPackFindersEvent;
import com.aetherteam.aetherfabric.events.LevelEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    @Final
    private PackRepository resourcePackRepository;

    @Shadow
    @Nullable
    public ClientLevel level;

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void aetherFabric$levelTickEvents(ClientLevel instance, BooleanSupplier hasTimeLeft, Operation<Void> original) {
        LevelEvents.BEFORE.invoker().beforeTick(instance);

        original.call(instance, hasTimeLeft);

        LevelEvents.AFTER.invoker().afterTick(instance);
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;reload()V"))
    private void aetherFabric$addClientPackResources(GameConfig gameConfig, CallbackInfo ci) {
        AddPackFindersEvent.invokeEvent(PackType.CLIENT_RESOURCES, this.resourcePackRepository);
    }

    @WrapOperation(method = "setLevel", at = @At(value = "NEW", target = "(Ljava/util/function/BooleanSupplier;Lnet/minecraft/client/gui/screens/ReceivingLevelScreen$Reason;)Lnet/minecraft/client/gui/screens/ReceivingLevelScreen;"))
    private ReceivingLevelScreen aetherFabric$adjustLevelTransition(BooleanSupplier levelReceived, ReceivingLevelScreen.Reason reason, Operation<ReceivingLevelScreen> original, @Local(argsOnly = true) ClientLevel level) {
        var alternativeConstructor = ClientDimUtils.getScreenFromLevel(level, this.level);

        return alternativeConstructor != null ? alternativeConstructor.create(levelReceived, reason) : original.call(levelReceived, reason);
    }
}
