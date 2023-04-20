package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerGamePacketListenerImpl.class)
public interface ServerGamePacketListenerImplAccessor {
    @Accessor("aboveGroundTickCount")
    void aether$setAboveGroundTickCount(int aboveGroundTickCount);

    @Accessor("aboveGroundVehicleTickCount")
    void aether$setAboveGroundVehicleTickCount(int aboveGroundVehicleTickCount);
}