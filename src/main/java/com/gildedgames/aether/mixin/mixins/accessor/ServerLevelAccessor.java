package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerLevel.class)
public interface ServerLevelAccessor {
    @Accessor
    ServerLevelData getServerLevelData();

    @Mutable
    @Accessor
    void setServerLevelData(ServerLevelData serverLevelData);
}