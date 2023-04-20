package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Level.class)
public interface LevelAccessor {
    @Accessor("levelData")
    WritableLevelData aether$getLevelData();

    @Mutable
    @Accessor("levelData")
    void aether$setLevelData(WritableLevelData levelData);
}