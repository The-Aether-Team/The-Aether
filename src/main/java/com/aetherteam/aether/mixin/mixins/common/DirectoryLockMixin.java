package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.DirectoryLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Path;

@Mixin(DirectoryLock.class)
public class DirectoryLockMixin {
    /**
     * Used by the world preview system.<br>
     * Unlocks the active world preview level when in the world selection screen.
     *
     * @param original The original lock state of the level's path.
     * @param basePath The {@link Path} for the level directory.
     * @return The lock state of the path after adjusting for if the world preview is active.
     * @see WorldDisplayHelper#isActive()
     * @see AetherMixinHooks#canUnlockLevel(Path)
     */
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "isLocked(Ljava/nio/file/Path;)Z")
    private static boolean isLocked(boolean original, @Local(ordinal = 0, argsOnly = true) Path basePath) {
        if (WorldDisplayHelper.isActive() && AetherMixinHooks.canUnlockLevel(basePath)) return false;
        return original;
    }
}
