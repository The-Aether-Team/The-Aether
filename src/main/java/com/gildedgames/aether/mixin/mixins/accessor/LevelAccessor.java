package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Level.class)
public interface LevelAccessor {
    @Accessor
    WritableLevelData getLevelData();

    @Mutable
    @Accessor
    void setLevelData(WritableLevelData levelData);
}