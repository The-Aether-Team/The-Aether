package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.server.LanServerPinger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(IntegratedServer.class)
public interface IntegratedServerAccessor {
    @Accessor("publishedPort")
    void aether$setPublishedPort(int publishedPort);

    @Accessor("lanPinger")
    LanServerPinger aether$getLanPinger();

    @Accessor("lanPinger")
    void aether$setLanPinger(LanServerPinger lanPinger);

    @Accessor("uuid")
    UUID aether$getUUID();
}
