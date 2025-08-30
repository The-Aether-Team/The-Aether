package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Path;

@Mixin(LevelStorageSource.class)
public class LevelStorageSourceMixin {
    @WrapOperation(method = { "lambda$loadLevelSummaries$2(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory;)Lnet/minecraft/world/level/storage/LevelSummary;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DirectoryLock;isLocked(Ljava/nio/file/Path;)Z"), require = 1, allow = 1)
    private boolean loadLevelSummaries(Path flag, Operation<Boolean> original, @Local(argsOnly = true) LevelStorageSource.LevelDirectory levelDirectory) {
        if (WorldDisplayHelper.isActive() && AetherMixinHooks.canUnlockLevel(levelDirectory.path())) {
            return false;
        }
        return original.call(flag);
    }
}
