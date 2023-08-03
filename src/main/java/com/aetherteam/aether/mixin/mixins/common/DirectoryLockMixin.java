package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.util.DirectoryLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Mixin(DirectoryLock.class)
public class DirectoryLockMixin {
    @Inject(at = @At(value = "HEAD"), method = "isLocked", cancellable = true)
    private static void isLocked(Path basePath, CallbackInfoReturnable<Boolean> cir) {
        if (WorldDisplayHelper.isActive()) {
            if (AetherMixinHooks.canUnlockLevel(basePath)) {
                cir.setReturnValue(false);
            }
        }
    }
}
