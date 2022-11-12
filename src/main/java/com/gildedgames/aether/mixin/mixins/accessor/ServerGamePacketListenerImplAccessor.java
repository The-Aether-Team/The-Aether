package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerGamePacketListenerImpl.class)
public interface ServerGamePacketListenerImplAccessor {
    @Accessor("aboveGroundTickCount")
    void setAboveGroundTickCount(int aboveGroundTickCount);

    @Accessor("aboveGroundVehicleTickCount")
    void setAboveGroundVehicleTickCount(int aboveGroundVehicleTickCount);
}
