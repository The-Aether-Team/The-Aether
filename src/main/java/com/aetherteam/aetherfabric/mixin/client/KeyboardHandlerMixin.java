package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.client.events.ScreenKeyboardEventsExtension;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @WrapOperation(method = "method_1454", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z"))
    private static boolean aetherFabric$afterKeyPressedEvent(Screen instance, int keyCode, int scanCode, int modifiers, Operation<Boolean> original, @Local(argsOnly = true) Screen screen) {
        var result = original.call(instance, keyCode, scanCode, modifiers);

        ScreenKeyboardEventsExtension.setPressedResult(screen, result);

        return result;
    }
}
