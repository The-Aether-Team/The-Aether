package com.gildedgames.aether.mixin.mixins.common.accessor;

import net.minecraft.util.DirectoryLock;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelStorageSource.LevelStorageAccess.class)
public interface LevelStorageAccessAccessor {
    @Mutable
    @Accessor("lock")
    void aether$setLock(DirectoryLock lock);
}