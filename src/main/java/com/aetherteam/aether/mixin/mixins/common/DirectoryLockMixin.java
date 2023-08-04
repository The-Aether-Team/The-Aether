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
    /**
     * Used by the world preview system.<br>
     * Unlocks the active world preview level when in the world selection screen.
     * @param basePath The {@link Path} for the level directory.
     * @param cir The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     * @see WorldDisplayHelper#isActive()
     * @see AetherMixinHooks#canUnlockLevel(Path)
     */
    @Inject(at = @At(value = "HEAD"), method = "isLocked(Ljava/nio/file/Path;)Z", cancellable = true)
    private static void isLocked(Path basePath, CallbackInfoReturnable<Boolean> cir) {
        if (WorldDisplayHelper.isActive()) {
            if (AetherMixinHooks.canUnlockLevel(basePath)) {
                cir.setReturnValue(false);
            }
        }
    }
}
